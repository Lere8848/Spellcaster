package com.ken.spellcaster;

import com.badlogic.gdx.utils.Array;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

// AI 类
// 用于辅助 CPU 生成决策
public class AIInput {
    static String leftChooseLabel = "Skip";
    static String rightChooseLabel = "Skip";
    static boolean isLockChooseLabel = false;
    static boolean isChooseNoCDFS = false;

    public static class ControlPackage {
        public String leftGesture;
        public String rightGesture;
        public BaseSpell leftSpell;
        public BaseSpell rightSpell;
        public ControlEntity leftTarget;
        public ControlEntity rightTarget;

        // Monster 专用
        public ControlPackage(ControlEntity rightTarget) {
            this.rightTarget = rightTarget;
        }

        // Wizard 专用
        public ControlPackage(String leftGesture, String rightGesture, BaseSpell leftSpell, BaseSpell rightSpell, ControlEntity leftTarget, ControlEntity rightTarget) {
            this.leftGesture = leftGesture;
            this.rightGesture = rightGesture;
            this.leftSpell = leftSpell;
            this.rightSpell = rightSpell;
            this.leftTarget = leftTarget;
            this.rightTarget = rightTarget;
        }
    }

    // 锁定选择栏
    public static void lockChooseLabel(String left, String right) {
        leftChooseLabel = left;
        rightChooseLabel = right;
        isLockChooseLabel = true;
    }

    // 解锁选择栏
    public static void unLockChooseLabel() {
        isLockChooseLabel = false;
    }

    public static void lockChooseNoCDFS() {
        isChooseNoCDFS = true;
        String except = "CDFScdfs";
        if (except.contains(leftChooseLabel)) {
            leftChooseLabel = "Skip";
        }
        if (except.contains(rightChooseLabel)) {
            rightChooseLabel = "Skip";
        }
    }

    public static void unlockChooseNoCDFS() {
        isChooseNoCDFS = false;
    }

    // 设置选择栏 受锁影响
    public static void setLeftChooseLabel(String str) {
        if (!isLockChooseLabel) {
            if (!isChooseNoCDFS) {
                leftChooseLabel = str;
            } else {
                String except = "CDFScdfs";
                if (!except.contains(str)) {
                    leftChooseLabel = str;
                } else {
                    leftChooseLabel = "W";
                }
            }
        }
    }

    public static void setRightChooseLabel(String str) {
        if (!isLockChooseLabel) {
            if (!isChooseNoCDFS) {
                rightChooseLabel = str;
            } else {
                String except = "CDFScdfs";
                if (!except.contains(str)) {
                    rightChooseLabel = str;
                }
            }
        }
    }

    // 给出两字符串首尾重叠部分大小 小写也要算重叠
    public static int overlap(String str1, String str2) {
        int count = 0;
        int minLength = Math.min(str1.length(), str2.length());
        for (int i = 0; i < minLength + 1; i++) {
            String sub1 = str1.substring(str1.length() - i);
            String sub2 = str2.substring(0, i);
            boolean validSub = true;
            for (int j = 0; j < sub1.length(); j++) {
                String chr1 = sub1.substring(j, j + 1);
                String chr2 = sub2.substring(j, j + 1);
                if (!(chr1.equals(chr2) || chr1.toUpperCase().equals(chr2))) {
                    validSub = false;
                    break;
                }
            }
            if (validSub) {
                count = i;
            }
        }
        return count;
    }

    // 从技能列表中挑选手势最长的第一个技能
    public static BaseSpell selectBestSpell(Array<BaseSpell> spells) {
        int maxPoint = -1;
        BaseSpell bestSpell = null;
        for (BaseSpell spell : spells) {
            if (spell.gesture.length() > maxPoint) {
                bestSpell = spell;
                maxPoint = spell.gesture.length();
            }
        }
        return bestSpell;
    }

    // 获得随机的一个技能手势
    public static String genRandomGesture() {
        return SpellMap.getAllGesture().random();
    }

    // 按原先手势和状态分数决策最好的技能手势
    // 手势分数：手势字符个数  这部分可以换成自己规定的分数表  更加合理精确
    // Overlap 数量 = 技能分数 （偏好在较少的回合内便可释放的较长的技能）
    // 在同 Overlap 数量的情况选择较长的手势
    public static String genBestGesture(String originGesture) {
        String bestGesture = null;
        int bestGestureOverlap = 1;
        int bestGestureLength = 0;
        for (String spellGesture : SpellMap.getAllGesture()) {
            int overlap = overlap(originGesture, spellGesture);
            // 只有在还未满足手势的情况下考虑
            if (overlap < spellGesture.length()) {
                if (overlap > bestGestureOverlap || ((overlap == bestGestureOverlap) && (spellGesture.length() > bestGestureLength))) {
                    bestGesture = spellGesture;
                    bestGestureOverlap = overlap;
                    bestGestureLength = spellGesture.length();
                }
            }
        }
        if (bestGesture != null) {
            return bestGesture;
        } else {
            // 对于无 Overlap 的手势组合 随机给出一种手势
            return genRandomGesture();
        }
    }

    static String[] array = {"P", "D", "W", "S", "F", "C", "Stab", "Skip"};
    static Array<String> gesture = new Array<>(array);
    static String[] arrayNoCDFS = {"P", "W", "Stab", "Skip"};
    static Array<String> gestureNoCDFS = new Array<>(array);

    // CPU 决策出的目标手势 尽量向该手势保持 可变动
    static String leftTargetGesture = "";
    static String rightTargetGesture = "";
    // 上次左手重叠部分 (即步骤分值)
    // 用于判别手势是否受到意外影响 产生变化
    static int lastLeftOverlap = 0;
    // 上次右手重叠部分 (即步骤分值)
    static int lastRightOverlap = 0;


    public static ControlPackage searchControl(ControlEntity entity, TurnManager manager) {
        if (entity instanceof Wizard) {
            setLeftChooseLabel("Skip");
            setRightChooseLabel("Skip");

            Wizard wizard = (Wizard) entity;
            String leftGesture = wizard.leftGesture.str();
            String rightGesture = wizard.rightGesture.str();
            int leftOverlap = overlap(leftGesture, leftTargetGesture);
            int rightOverlap = overlap(rightGesture, rightTargetGesture);

            // 一步步朝手势逼近 若发现某一次需逼近步数又变高了 说明出现意外情况 重新确定目标手势
            if (leftTargetGesture.length() <= leftOverlap || lastLeftOverlap > leftOverlap) {
                leftTargetGesture = genBestGesture(leftGesture);
            }
            if (rightTargetGesture.length() <= rightOverlap || lastRightOverlap > rightOverlap) {
                rightTargetGesture = genBestGesture(rightGesture);
            }
            leftOverlap = overlap(leftGesture, leftTargetGesture);
            rightOverlap = overlap(rightGesture, rightTargetGesture);
            lastLeftOverlap = leftOverlap;
            lastRightOverlap = rightOverlap;
            // 按最优路线向目标手势逼近

            String leftChoose = leftTargetGesture.substring(leftOverlap, leftOverlap + 1);
            String rightChoose = rightTargetGesture.substring(rightOverlap, rightOverlap + 1);
            if (leftOverlap < leftTargetGesture.length()) {
                setLeftChooseLabel(leftChoose);
            }
            if (rightOverlap < rightTargetGesture.length()) {
                setRightChooseLabel(rightChoose);
            }
            // 如果做出的选择有双手手势 则另一只手要遵循该手势 即使破坏阵型
            if (Character.isLowerCase(leftChoose.charAt(0))) {
                setRightChooseLabel(leftChoose);
            }
            if (Character.isLowerCase(rightChoose.charAt(0))) {
                setLeftChooseLabel(rightChoose);
            }

            // 受到技能效果影响而必须做出的改变 决策部分必须在这行上面
            if (leftChooseLabel.length() == 1 && leftChooseLabel.equals(rightChooseLabel)) {
                leftChooseLabel = leftChooseLabel.toLowerCase();
                rightChooseLabel = rightChooseLabel.toLowerCase();
            }
            if (leftChooseLabel.length() == 1 && !leftChooseLabel.equals(rightChooseLabel)) {
                leftChooseLabel = leftChooseLabel.toUpperCase();
                rightChooseLabel = rightChooseLabel.toUpperCase();
            }
            // 绝不允许决策出双 P 造成投降
            if (leftChooseLabel.equals("p")) {
                // 若出现则左让右 让右手满足
                leftChooseLabel = "Skip";
                rightChooseLabel = "P";
            }
            BaseSpell leftSpell = selectBestSpell(wizard.genLeftSpell(leftChooseLabel));
            BaseSpell rightSpell = selectBestSpell(wizard.genRightSpell(rightChooseLabel));
            // 判定双手技能
            boolean leftSpellIsTwo = false;
            boolean rightSpellIsTwo = false;
            // 好效果给己方  坏效果给对方
            ControlEntity leftTarget = null;
            if (leftSpell != null) {
                leftTarget = leftSpell.isGoodForTarget ? manager.getAITarget().random() : manager.getPlayerTarget().random();
                // 最后一位为小写字母的手势是双手手势
                leftSpellIsTwo = Character.isLowerCase(leftSpell.gesture.charAt(leftSpell.gesture.length() - 1));
            }
            ControlEntity rightTarget = null;
            if (rightSpell != null) {
                rightTarget = rightSpell.isGoodForTarget ? manager.getAITarget().random() : manager.getPlayerTarget().random();
                rightSpellIsTwo = Character.isLowerCase(rightSpell.gesture.charAt(rightSpell.gesture.length() - 1));
            }
            // 有双手技能优先双手技能
            // 同为双手技能优先左手
            // 否则才会两只手分别执行单手技能
            if (leftSpellIsTwo) {
                rightSpell = null;
            } else if (rightSpellIsTwo) {
                leftSpell = null;
            }
            return new ControlPackage(leftChooseLabel, rightChooseLabel, leftSpell, rightSpell, leftTarget, rightTarget);
        }
        // CPU 的怪物随机选取玩家一方的目标攻击
        if (entity instanceof Monster) {
            return new ControlPackage(manager.getPlayerTarget().random());
        }
        return null;
    }
}
