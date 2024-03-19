package com.ken.spellcaster.effects.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.damaging.MissileSpell;
import com.ken.spellcaster.spells.StabSpell;

// 护盾效果
public class ShieldEffect extends BaseEffect {
    public ShieldEffect(int duration, int startTurn, ControlEntity caster) {
        super("Shield", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        if (caster instanceof Monster || baseSpell instanceof MissileSpell || baseSpell instanceof StabSpell) {
            baseSpell.invalid();
        }
    }
}
