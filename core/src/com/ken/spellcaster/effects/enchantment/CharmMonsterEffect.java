package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class CharmMonsterEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log

    public CharmMonsterEffect(int duration, int startTurn, ControlEntity caster) {
        super("Charm", duration, startTurn, caster);
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Monster) {
            self.setControlWizard((Wizard) caster);
            if (!isTake) {
                log(String.format("Due to CharmMonster, control transferred to %s. Amazing!", caster));
                isTake = true;
            }
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        isTake = false;
    }
}