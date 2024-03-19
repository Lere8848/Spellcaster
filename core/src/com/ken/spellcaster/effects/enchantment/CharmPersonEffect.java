package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class CharmPersonEffect extends BaseEffect {
    public CharmPersonEffect(int duration, int startTurn, ControlEntity caster) {
        super("Charm", duration, startTurn, caster);
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Wizard) {
            self.setControlWizard((Wizard) caster);
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        if (self instanceof Wizard) {
            self.setControlWizard((Wizard) self);
        }
    }
}