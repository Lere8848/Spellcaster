package com.ken.spellcaster.effects.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.StabSpell;
import com.ken.spellcaster.spells.protection.CureHeavyWoundsSpell;
import com.ken.spellcaster.spells.protection.CureLightWoundsSpell;

public class DispelMagicEffect extends BaseEffect {
    public DispelMagicEffect(int duration, int startTurn, ControlEntity caster) {
        super("DispelMagic", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        // 只有匕首和回血被允许 其他技能全部消除
        if (!(baseSpell instanceof StabSpell) && !(baseSpell instanceof CureLightWoundsSpell) && !(baseSpell instanceof CureHeavyWoundsSpell)) {
            baseSpell.invalid();
        }
    }
}
