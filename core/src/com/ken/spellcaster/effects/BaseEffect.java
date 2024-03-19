package com.ken.spellcaster.effects;

// 基础效果类
// 所有效果均继承自此类

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

// 依靠 type 和 instanceof 进行精细化筛选
public abstract class BaseEffect {
    String name;
    public int duration;
    public int startTurn;
    public ControlEntity caster;
    public boolean isDestroy = false; // 法术效果是否结束

    public BaseEffect(String name, int duration, int startTurn, ControlEntity caster) {
        this.name = name;
        this.duration = duration;
        this.startTurn = startTurn;
        this.caster = caster;
    }

    public final void action(ControlEntity self) {
        if (!isDestroy) {
            if (self.getCurrentTurn() >= startTurn && self.getCurrentTurn() < (startTurn + duration)) {
                actionInRange(self);
            } else if (self.getCurrentTurn() >= (startTurn + duration)) {
                removeEffect(self);
            }
        }
    }

    // 基本效果
    public void actionInRange(ControlEntity self) {
    }

    // 对施加技能的影响
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
    }

    // 在回合开始时的效果 一般用于输入控制
    public void actionOnTurnBegin(ControlEntity self) {
    }

    // 在回合结束时的效果 一般用于恢复正常输入
    public void actionOnTurnEnd(ControlEntity self) {
    }

    // 移除效果时的方法
    public void removeEffect(ControlEntity self) {
        isDestroy = true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseEffect) {
            return name.equals(((BaseEffect) obj).name);
        }
        return false;
    }
}
