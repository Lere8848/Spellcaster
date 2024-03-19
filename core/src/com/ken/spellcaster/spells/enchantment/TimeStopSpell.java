package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;

public class TimeStopSpell extends BaseSpell {
    public TimeStopSpell(String gesture, ControlEntity caster) {
        super("Time Stop", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 立刻获得额外的回合
        target.currentTurnCount++;
    }
}
