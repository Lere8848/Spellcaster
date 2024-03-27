package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class CharmPersonEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log

    public CharmPersonEffect(int duration, int startTurn, ControlEntity caster) {
        super("Charm", duration, startTurn, caster);
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Wizard) {
            self.setControlWizard((Wizard) caster);
            if (!isTake) {
                log(String.format("Being inflicted with CharmPerson! the gesture for the next turn will be determined by %s.", caster));
                isTake = true;
            }
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        if (self instanceof Wizard) {
            self.setControlWizard((Wizard) self);
        }
        isTake = false;
    }
}