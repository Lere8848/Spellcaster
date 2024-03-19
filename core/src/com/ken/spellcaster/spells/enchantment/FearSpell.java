package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.enchantment.FearEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class FearSpell extends BaseSpell {
    public FearSpell(String gesture, ControlEntity caster) {
        super("Fear", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (enchantmentNotCounter(target, FearSpell.class)) {
            if (target instanceof Wizard) {
                target.addEffect(new FearEffect(1, target.getCurrentTurn() + 1, caster));
            }
        }
    }
}
