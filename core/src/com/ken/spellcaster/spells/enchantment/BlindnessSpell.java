package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.effects.enchantment.BlindnessEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class BlindnessSpell extends BaseSpell {
    public BlindnessSpell(String gesture, ControlEntity caster) {
        super("Blindness", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 直接杀死怪物
        if (target instanceof Monster) {
            target.setHealth(0);
            target.cancelTurn();
        }
        target.addEffect(new BlindnessEffect(3, target.getCurrentTurn() + 1, caster));
    }
}
