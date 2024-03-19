package com.ken.spellcaster;

import com.badlogic.gdx.utils.Array;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.damaging.LightningBoltSpell;
import com.ken.spellcaster.spells.protection.ShieldSpell;

// 手势类
public class Gesture {
    // 大写 - 单手手势  小写 - 双手手势
    private String gestureStack = "";
    private final ControlEntity caster;
    public boolean isHide = false;

    public Gesture(ControlEntity caster) {
        this.caster = caster;
    }

    public void pushGesture(String gesture) {
        if (gesture.length() == 1 && "SFWPDsfwpdc".contains(gesture)) {
            gestureStack += gesture;
        } else {
            clearGesture();
        }
    }

    public void clearGesture() {
        gestureStack = "";
    }

    public Array<BaseSpell> genSpell(String gesture) {
        Array<BaseSpell> array = new Array<>();
        if (gesture.length() == 1) {
            for (String s : SpellMap.gestureSpellMap.keySet()) {
                boolean isValidSpell = true;
                int length = s.length();
                if (gestureStack.concat(gesture).length() >= length) {
                    String subGesture = gestureStack.concat(gesture).substring(gestureStack.concat(gesture).length() - length);
                    for (int i = 0; i < length; i++) {
                        String chr1 = subGesture.substring(i, i + 1);
                        String chr2 = s.substring(i, i + 1);
                        if (!(chr1.equals(chr2) || chr1.toUpperCase().equals(chr2))) {
                            isValidSpell = false;
                            break;
                        }
                    }
                } else {
                    isValidSpell = false;
                }
                if (isValidSpell) {
                    // 只有实现了的技能会被添加
                    BaseSpell spell = SpellMap.findSpell(SpellMap.gestureSpellMap.get(s), s, caster);
                    if (spell != null) {
                        // 不添加已经使用过的较短的LightningBolt
                        if (!(spell instanceof LightningBoltSpell && spell.gesture.equals("WDDc") && ((Wizard) caster).useShortLightningBolt)) {
                            // 禁止双手盾牌 && 必须投降
                            if (!(gesture.equals("p") && spell instanceof ShieldSpell)) {
                                array.add(spell);
                            }
                        }
                    }
                }
            }
        }
        return array;
    }

    public String str() {
        return gestureStack;
    }

    public String replaceWithAsterisk(String input) {
        // 使用 replaceAll 方法将每个字符替换为 *
        String replaced = input.replaceAll("\\.", "*");
        // 使用 replace 方法将 * 替换为空格并在字符之间添加空格
        return replaced.replace("*", "* ");
    }

    @Override
    public String toString() {
        if (isHide) {
            return replaceWithAsterisk(gestureStack);
        } else {
            return gestureStack.replaceAll("", " ");
        }
    }
}
