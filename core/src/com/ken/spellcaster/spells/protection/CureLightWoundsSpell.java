package com.ken.spellcaster.spells.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

public class CureLightWoundsSpell extends BaseSpell {
    public CureLightWoundsSpell(String gesture, ControlEntity caster) {
        super("Cure Light Wounds", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 获得一点假血
        target.setFakeHealth(1);
    }
}