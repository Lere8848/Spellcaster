package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.enchantment.DelayedEffectEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class DelayedEffectSpell extends BaseSpell {
    public DelayedEffectSpell(String gesture, ControlEntity caster) {
        super("Delayed Effect", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new DelayedEffectEffect(3, target.getCurrentTurn() + 1, caster));
    }
}
