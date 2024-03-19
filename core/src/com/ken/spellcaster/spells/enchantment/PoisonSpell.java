package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.enchantment.PoisonEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class PoisonSpell extends BaseSpell {
    public PoisonSpell(String gesture, ControlEntity caster) {
        super("Poison", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new PoisonEffect(0xFFFF, target.getCurrentTurn(), caster));
    }
}
