package com.ken.spellcaster.spells.damaging;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

public class CauseLightWoundsSpell extends BaseSpell {
    public CauseLightWoundsSpell(String gesture, ControlEntity caster) {
        super("Cause Light Wounds", gesture, SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.attack(2);
    }
}
