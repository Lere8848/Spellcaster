package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class InvisibilityEffect extends BaseEffect {
    public InvisibilityEffect(int duration, int startTurn, ControlEntity caster) {
        super("Invisibility", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 只有当对手时有效果
        if (self instanceof Wizard) {
            if (self == self.getManager().cpu) {
                self.getManager().cpu.setGestureHide(true);
            }
        }
    }

    @Override
    public void removeEffect(ControlEntity self) {
        super.removeEffect(self);
        if (self instanceof Wizard) {
            self.getManager().cpu.setGestureHide(false);
        }
    }
}
