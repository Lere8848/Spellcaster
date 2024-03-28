package com.ken.spellcaster.spells.summons;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

public class SummonTrollSpell extends BaseSpell {
    public SummonTrollSpell(String gesture, ControlEntity caster) {
        super("Summon Troll", gesture, SpellType.SUMMON, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (caster instanceof Wizard) {
            Monster troll = new Monster(caster.getManager(), caster + "-Troll" + ((Wizard) caster).monsterNO, 3, 3, ElementType.NONE, (Wizard) caster);
            ((Wizard) caster).addMonster(troll);
        }
    }
}
