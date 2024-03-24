package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

// 失明
public class BlindnessEffect extends BaseEffect {
    public BlindnessEffect(int duration, int startTurn, ControlEntity caster) {
        super("Blindness", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 只有当玩家时有效果
        if (self instanceof Wizard) {
            if (self == self.getManager().player) {
                self.getManager().player.setGestureHide(true);
                self.getManager().cpu.setGestureHide(true);
            }
        }
    }

    @Override
    public void removeEffect(ControlEntity self) {
        super.removeEffect(self);
        if (self instanceof Wizard) {
            self.getManager().player.setGestureHide(false);
            self.getManager().cpu.setGestureHide(false);
        }
    }
}
