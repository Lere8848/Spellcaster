package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.enchantment.PermanencyEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class PermanencySpell extends BaseSpell {
    public PermanencySpell(String gesture, ControlEntity caster) {
        super("Permanency", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new PermanencyEffect(3, target.getCurrentTurn() + 1, caster, target));
    }
}
