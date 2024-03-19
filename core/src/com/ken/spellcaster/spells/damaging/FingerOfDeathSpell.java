package com.ken.spellcaster.spells.damaging;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

public class FingerOfDeathSpell extends BaseSpell {
    public FingerOfDeathSpell(String gesture, ControlEntity caster) {
        super("Finger of Death", gesture, SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 造成极大伤害 一击必杀
        target.attack(0xFFFF);
    }
}
