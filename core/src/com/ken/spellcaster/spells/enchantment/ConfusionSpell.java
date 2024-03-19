package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.enchantment.ConfusionEffect;
import com.ken.spellcaster.spells.BaseSpell;

// 目标手势随机决定技能
public class ConfusionSpell extends BaseSpell {
    public ConfusionSpell(String gesture, ControlEntity caster) {
        super("Confusion", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (enchantmentNotCounter(target, ConfusionSpell.class)) {
            if (target instanceof Wizard) {
                target.addEffect(new ConfusionEffect(1, target.getCurrentTurn() + 1, caster));
            }
            if (target instanceof Monster) {
                target.addEffect(new ConfusionEffect(1, target.getCurrentTurn(), caster));
                target.cancelTurn();
            }
        }
    }
}