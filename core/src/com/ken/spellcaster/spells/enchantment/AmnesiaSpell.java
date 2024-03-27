package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.enchantment.AmnesiaEffect;
import com.ken.spellcaster.spells.BaseSpell;

// 手势重复法术
public class AmnesiaSpell extends BaseSpell {
    public AmnesiaSpell(String gesture, ControlEntity caster) {
        super("Amnesia", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (enchantmentNotCounter(target, AmnesiaSpell.class)) {
            if (target instanceof Wizard) {
                target.addEffect(new AmnesiaEffect(1, target.getCurrentTurn() + 1, caster));
            }
            if (target instanceof Monster) {
                target.addEffect(new AmnesiaEffect(1, target.getCurrentTurn(), caster));
                target.cancelTurn();
            }
        }
    }
}
