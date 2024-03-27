package com.ken.spellcaster.effects.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.damaging.FingerOfDeathSpell;
import com.ken.spellcaster.spells.protection.DispelMagicSpell;
import com.ken.spellcaster.spells.protection.RaiseDeadSpell;

public class CounterSpellEffect extends BaseEffect {
    public CounterSpellEffect(int duration, int startTurn, ControlEntity caster) {
        super("CounterSpell", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        if (!(baseSpell instanceof DispelMagicSpell) && !(baseSpell instanceof RaiseDeadSpell) && !(baseSpell instanceof FingerOfDeathSpell)) {
            defendSpellLog(baseSpell);
            baseSpell.invalid();
        }
    }
}
