package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

public class AntiSpellSpell extends BaseSpell {
    public AntiSpellSpell(String gesture, ControlEntity caster) {
        super("Anti-Spell", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (target instanceof Wizard) {
            ((Wizard) target).clearGesture();
        }
    }
}
