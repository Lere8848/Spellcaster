package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.effects.enchantment.CharmMonsterEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class CharmMonsterSpell extends BaseSpell {
    public CharmMonsterSpell(String gesture, ControlEntity caster) {
        super("Charm Monster", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (enchantmentNotCounter(target, CharmMonsterSpell.class)) {
            if (target instanceof Monster) {
                target.addEffect(new CharmMonsterEffect(1, target.getCurrentTurn(), caster));
            }
        }
    }
}