package com.ken.spellcaster.spells.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.protection.ShieldEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class ShieldSpell extends BaseSpell {
    public ShieldSpell(String gesture, ControlEntity caster) {
        super("Shield", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new ShieldEffect(1, target.getCurrentTurn(), caster));
    }
}
