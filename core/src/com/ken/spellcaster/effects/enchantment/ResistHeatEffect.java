package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.MonsterDamageSpell;

public class ResistHeatEffect extends BaseEffect {
    public ResistHeatEffect(int duration, int startTurn, ControlEntity caster) {
        super("ResistHeat", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        // 抵挡所有火元素攻击
        if (baseSpell instanceof MonsterDamageSpell && ((MonsterDamageSpell) baseSpell).type == ElementType.FIRE) {
            defendSpellLog(baseSpell);
            baseSpell.invalid();
        }
    }
}
