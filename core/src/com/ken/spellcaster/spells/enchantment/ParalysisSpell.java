package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.enchantment.ParalysisEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class ParalysisSpell extends BaseSpell {
    public ParalysisSpell(String gesture, ControlEntity caster) {
        super("Paralysis", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (enchantmentNotCounter(target, ParalysisSpell.class)) {
            if (target instanceof Wizard) {
                target.addEffect(new ParalysisEffect(1, target.getCurrentTurn() + 1, caster));
            }
            if (target instanceof Monster) {
                target.addEffect(new ParalysisEffect(1, target.getCurrentTurn(), caster));
                target.cancelTurn();
            }
        }
    }
}
