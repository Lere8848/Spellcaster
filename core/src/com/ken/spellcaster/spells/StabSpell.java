package com.ken.spellcaster.spells;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

// 专为匕首设计的技能 将stab视为一个spell
public class StabSpell extends BaseSpell {
    public StabSpell(ControlEntity caster) {
        super("Stab", "Wang Yukai", SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.attack(1);
    }
}
