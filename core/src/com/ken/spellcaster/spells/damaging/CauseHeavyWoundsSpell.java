package com.ken.spellcaster.spells.damaging;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

public class CauseHeavyWoundsSpell extends BaseSpell {
    public CauseHeavyWoundsSpell(String gesture, ControlEntity caster) {
        super("Cause Heavy Wounds", gesture, SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.attack(3);
    }
}
