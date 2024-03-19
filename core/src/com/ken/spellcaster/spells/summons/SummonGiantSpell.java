package com.ken.spellcaster.spells.summons;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

public class SummonGiantSpell extends BaseSpell {
    public SummonGiantSpell(String gesture, ControlEntity caster) {
        super("Summon Giant", gesture, SpellType.SUMMON, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (caster instanceof Wizard) {
            ((Wizard) caster).addMonster(new Monster(caster.getManager(), caster + "-Giant" + ((Wizard) caster).monsterNO, 4, 4, ElementType.NONE, (Wizard) caster));
        }
    }
}
