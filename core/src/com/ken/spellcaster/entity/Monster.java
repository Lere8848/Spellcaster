package com.ken.spellcaster.entity;

import com.ken.spellcaster.TurnManager;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.MonsterDamageSpell;

// 怪物类
public class Monster extends ControlEntity {
    private final int attack;
    public final ElementType type;

    public Monster(TurnManager manager, String name, int maxHealth, int attack, ElementType type, Wizard controlWizard) {
        super(manager, name, maxHealth);
        this.attack = attack;
        this.type = type;
        setOriginControlWizard(controlWizard);
        setControlWizard(controlWizard);
    }

    @Override
    public void control(String leftGesture, String rightGesture, BaseSpell leftSpell, BaseSpell rightSpell, ControlEntity leftTarget, ControlEntity rightTarget) {
        super.control(leftGesture, rightGesture, leftSpell, rightSpell, leftTarget, rightTarget);
        // 普通怪物攻击指定目标
        if (rightTarget != null && this.type == ElementType.NONE) {
            manager.log(name + ": " + "Attack to " + rightTarget);
            rightTarget.addSpell(new MonsterDamageSpell(this, attack, type));
        }
        // 元素怪物 AOE 攻击除自己外的所有目标
        if (this.type != ElementType.NONE) {
            for (ControlEntity entity : manager.getOtherTarget(this)) {
                manager.log(name + ": " + "Attack to " + entity);
                entity.addSpell(new MonsterDamageSpell(this, attack, type));
            }
        }
    }

    public enum ElementType {
        FIRE,
        ICE,
        NONE
    }

}
