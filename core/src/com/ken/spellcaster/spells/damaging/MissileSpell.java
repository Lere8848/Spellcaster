package com.ken.spellcaster.spells.damaging;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

public class MissileSpell extends BaseSpell {
    public MissileSpell(String gesture, ControlEntity caster) {
        super("Missile", gesture, SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.attack(1);
    }
}
