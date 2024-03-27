package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.MonsterDamageSpell;

public class ResistColdEffect extends BaseEffect {
    public ResistColdEffect(int duration, int startTurn, ControlEntity caster) {
        super("ResistCold", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        // 抵挡所有冰元素攻击
        if (baseSpell instanceof MonsterDamageSpell && ((MonsterDamageSpell) baseSpell).type == ElementType.ICE) {
            defendSpellLog(baseSpell);
            baseSpell.invalid();
        }
    }
}
