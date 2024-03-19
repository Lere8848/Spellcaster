package com.ken.spellcaster.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.ken.spellcaster.Gesture;
import com.ken.spellcaster.TurnManager;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.BaseSpell.SpellType;
import com.ken.spellcaster.spells.StabSpell;

// 法师类
public class Wizard extends ControlEntity {
    public final Gesture leftGesture;
    public final Gesture rightGesture;
    public DataChangeListener listener = null;
    private final Array<Monster> monsters; // 用的是LibGDX的Array来存储当前Wizard控制的monster，更厉害
    public int monsterNO = 1; // test
    public String lastLeftGesture;
    public String lastRightGesture;

    // 标识是否已经使用了较短的闪电手势 （一局只能使用一次）
    public boolean useShortLightningBolt;

    public Wizard(TurnManager manager, String name) {
        super(manager, name, 14); // 设置Wizard的初始生命值
        leftGesture = new Gesture(this);
        rightGesture = new Gesture(this);
        setOriginControlWizard(this);
        setControlWizard(this);
        monsters = new Array<>();
        useShortLightningBolt = false;
    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        if (listener != null) {
            listener.onHealthChange(health);
        }
    }

    public Array<BaseSpell> genLeftSpell(String gesture) {
        return leftGesture.genSpell(gesture);
    }

    public Array<BaseSpell> genRightSpell(String gesture) {
        return rightGesture.genSpell(gesture);
    }

    // 判断该法师是否拥有元素怪物 若有 则返回第一只元素怪物
    // 用于元素怪物 Counter
    public Monster hasElement() {
        for (Monster monster : monsters) {
            if (monster.type != ElementType.NONE) {
                return monster;
            }
        }
        return null;
    }

    @Override
    public void startAction() {
        super.startAction();
        updateMonster();
    }

    @Override
    public void addEffect(BaseEffect effect) {
        super.addEffect(effect);
        if (listener != null) {
            listener.onEffectChange(effects);
        }
    }

    public void setGestureHide(boolean isHide) {
        leftGesture.isHide = isHide;
        rightGesture.isHide = isHide;
        listener.onGestureChange(leftGesture, rightGesture);
    }

    @Override
    public void updateEffect() {
        super.updateEffect();
        if (listener != null) {
            listener.onEffectChange(effects);
        }
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
        monsterNO++;
        if (listener != null) {
            listener.onMonsterChange(monsters);
        }
    }

    public void updateMonster() {
        ArrayIterator<Monster> iterable = monsters.iterator();
        while (iterable.hasNext()) {
            if (iterable.next().isDead()) {
                iterable.remove();
            }
        }
        if (listener != null) {
            listener.onMonsterChange(monsters);
        }
    }

    public void clearGesture() {
        leftGesture.clearGesture();
        rightGesture.clearGesture();
        listener.onGestureChange(this.leftGesture, this.rightGesture);
    }

    public void clearMonster() {
        monsters.clear();
        if (listener != null) {
            listener.onMonsterChange(monsters);
        }
    }

    public void clearEffect() {
        effects.clear();
        permanencyEffects.clear();
        if (listener != null) {
            listener.onEffectChange(effects);
        }
    }
    public void clearSpell() {
        currentReceiveSpells.clear();
        currentTempSpells.clear();
    }

    public void setListener(DataChangeListener listener) {
        this.listener = listener;
    }

    public Array<Monster> getMonsters() {
        return monsters;
    }

    @Override
    public void control(String leftGesture, String rightGesture, BaseSpell leftSpell, BaseSpell rightSpell, ControlEntity leftTarget, ControlEntity rightTarget) {
        super.control(leftGesture, rightGesture, leftSpell, rightSpell, leftTarget, rightTarget);
        boolean leftSpellIsTwo = false;
        boolean rightSpellIsTwo = false;
        if (leftSpell != null) {
            // 最后一位为小写字母的手势是双手手势
            leftSpellIsTwo = Character.isLowerCase(leftSpell.gesture.charAt(leftSpell.gesture.length() - 1));
        }
        if (rightSpell != null) {
            rightSpellIsTwo = Character.isLowerCase(rightSpell.gesture.charAt(rightSpell.gesture.length() - 1));
        }
        lastLeftGesture = leftGesture;
        lastRightGesture = rightGesture;
        this.leftGesture.pushGesture(leftGesture);
        this.rightGesture.pushGesture(rightGesture);
        // 有双手技能优先双手技能
        // 同为双手技能优先左手
        // 否则才会两只手分别执行单手技能
        if (leftSpellIsTwo) {
            controlHalf(leftGesture, leftSpell, leftTarget);
        } else if (rightSpellIsTwo) {
            controlHalf(rightGesture, rightSpell, rightTarget);
        } else {
            controlHalf(leftGesture, leftSpell, leftTarget);
            controlHalf(rightGesture, rightSpell, rightTarget);
        }
        listener.onGestureChange(this.leftGesture, this.rightGesture);
    }

    private void controlHalf(String gesture, BaseSpell spell, ControlEntity target) {
        if (spell != null) {
            manager.log(name + ": " + spell + " to " + target);
        }
        if (gesture.equals("Stab")) {
            manager.log(name + ": " + "Stab to " + target);
            target.addSpell(new StabSpell(this));
        }
        // 绿色通道 如果是召唤类技能则提前生效 不走审批流程
        if (spell != null && spell.type == SpellType.SUMMON) {
            spell.action(this);
        }
        // 否则加入审批列表
        if (target != null) {
            target.addSpell(spell);
        }
    }

    public interface DataChangeListener {
        public void onHealthChange(int health);

        public void onGestureChange(Gesture left, Gesture right);

        public void onMonsterChange(Array<Monster> monsters);

        public void onEffectChange(Array<BaseEffect> effects);
    }

    /*
    在编程中，特别是在设计事件监听器和回调接口时，方法名前加上“on”前缀是一种常见的命名约定。这种约定有几个原因和优势：

    清晰的意图表达：使用“on”前缀明确表示该方法是作为某个事件发生时的响应而执行的。这使得代码的阅读者可以轻松地理解方法的目的和触发时机。

    增强代码可读性：当看到一个以“on”开头的方法时，开发者可以立即知道这是一个事件处理方法，而不是普通的逻辑方法。这种命名方式为代码的结构和逻辑提供了额外的上下文信息，有助于提高代码的整体可读性
     */
}



