package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class InvisibilityEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log

    public InvisibilityEffect(int duration, int startTurn, ControlEntity caster) {
        super("Invisibility", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 只有当对手时有效果
        if (self instanceof Wizard) {
            if (self == self.getManager().AI) {
                self.getManager().AI.setGestureHide(true);
                if (!isTake) {
                    //log("                                       "); 最大长度
                    log("Being inflicted with Invisibility");
                    log("can NOT see gestures made by opponent");
                    isTake = true;
                }
            }
        }
    }

    @Override
    public void removeEffect(ControlEntity self) {
        super.removeEffect(self);
        if (self instanceof Wizard) {
            self.getManager().AI.setGestureHide(false);
            isTake = false;
        }
    }
}
