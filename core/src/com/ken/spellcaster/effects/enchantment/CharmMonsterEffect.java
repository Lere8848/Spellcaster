package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class CharmMonsterEffect extends BaseEffect {
    public CharmMonsterEffect(int duration, int startTurn, ControlEntity caster) {
        super("Charm", duration, startTurn, caster);
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Monster) {
            self.setControlWizard((Wizard) caster);
        }
    }
}