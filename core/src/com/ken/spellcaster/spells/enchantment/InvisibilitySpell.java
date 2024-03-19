package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.effects.enchantment.InvisibilityEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class InvisibilitySpell extends BaseSpell {
    public InvisibilitySpell(String gesture, ControlEntity caster) {
        super("Invisibility", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 直接杀死怪物
        if (target instanceof Monster) {
            target.setHealth(0);
            target.cancelTurn();
        }
        target.addEffect(new InvisibilityEffect(3, target.getCurrentTurn() + 1, caster));
    }
}
