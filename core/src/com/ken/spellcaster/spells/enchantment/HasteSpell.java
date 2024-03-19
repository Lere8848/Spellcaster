package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.enchantment.HasteEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class HasteSpell extends BaseSpell {
    public HasteSpell(String gesture, ControlEntity caster) {
        super("Haste", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new HasteEffect(3, target.getCurrentTurn() + 1, caster));
    }
}
