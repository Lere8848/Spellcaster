package com.ken.spellcaster.spells.summons;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

public class SummonOgreSpell extends BaseSpell {
    public SummonOgreSpell(String gesture, ControlEntity caster) {
        super("Summon Ogre", gesture, SpellType.SUMMON, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (caster instanceof Wizard) {
            ((Wizard) caster).addMonster(new Monster(caster.getManager(), caster + "-Ogre" + ((Wizard) caster).monsterNO, 2, 2, ElementType.NONE, (Wizard) caster));
        }
    }
}
