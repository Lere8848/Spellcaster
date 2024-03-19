package com.ken.spellcaster.spells.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.protection.CounterSpellEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class CounterSpellSpell extends BaseSpell {
    public CounterSpellSpell(String gesture, ControlEntity caster) {
        super("Counter-Spell", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new CounterSpellEffect(1, target.getCurrentTurn(), caster));
    }
}