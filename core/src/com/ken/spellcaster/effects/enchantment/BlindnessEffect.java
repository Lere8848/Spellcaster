package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

// 失明
public class BlindnessEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log

    public BlindnessEffect(int duration, int startTurn, ControlEntity caster) {
        super("Blindness", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 只有当玩家时有效果
        if (self instanceof Wizard) {
            if (self == self.getManager().player) {
                self.getManager().player.setGestureHide(true);
                self.getManager().AI.setGestureHide(true);
                if (!isTake) {
                    //log("                                       "); 最大长度
                    log("Being inflicted with Blindness!");
                    log("canNOT see gestures made by your opponent");
                    isTake = true;
                }
            }
        }
    }

    @Override
    public void removeEffect(ControlEntity self) {
        super.removeEffect(self);
        if (self instanceof Wizard) {
            self.getManager().player.setGestureHide(false);
            self.getManager().AI.setGestureHide(false);
            isTake = true;
        }
    }
}
