package com.ken.spellcaster.effects.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.BaseSpell.SpellType;

public class RemoveEnchantmentEffect extends BaseEffect {
    public RemoveEnchantmentEffect(int duration, int startTurn, ControlEntity caster) {
        super("RemoveEnchantment", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        // 移除所有召唤或附魔技能
        if (baseSpell.type == SpellType.ENCHANTMENT || baseSpell.type == SpellType.SUMMON) {
            defendSpellLog(baseSpell);
            baseSpell.invalid();
        }
    }
}
