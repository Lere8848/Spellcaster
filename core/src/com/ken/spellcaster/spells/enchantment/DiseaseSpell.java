package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.enchantment.DiseaseEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class DiseaseSpell extends BaseSpell {
    public DiseaseSpell(String gesture, ControlEntity caster) {
        super("Disease", gesture, SpellType.ENCHANTMENT, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new DiseaseEffect(0xFFFF, target.getCurrentTurn(), caster));
    }
}
