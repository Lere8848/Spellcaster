package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.enchantment.CharmPersonEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class CharmPersonSpell extends BaseSpell {
    public CharmPersonSpell(String gesture, ControlEntity caster) {
        super("Charm Person", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (enchantmentNotCounter(target, CharmPersonSpell.class)) {
            if (target instanceof Wizard) {
                target.addEffect(new CharmPersonEffect(1, target.getCurrentTurn() + 1, caster));
            }
        }
    }
}