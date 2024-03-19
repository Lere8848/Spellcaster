package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class DelayedEffectEffect extends BaseEffect {
    BaseSpell tmpSpell;

    public DelayedEffectEffect(int duration, int startTurn, ControlEntity caster) {
        super("DelayedEffect", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        // 储存技能
        tmpSpell = baseSpell;
    }
}
