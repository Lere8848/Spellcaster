package com.ken.spellcaster.spells.summons;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

public class SummonGoblinSpell extends BaseSpell {
    public SummonGoblinSpell(String gesture, ControlEntity caster) {
        super("Summon Goblin", gesture, SpellType.SUMMON, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (caster instanceof Wizard) {
            Monster goblin = new Monster(caster.getManager(), caster + "-Goblin" + ((Wizard) caster).monsterNO, 1, 1, ElementType.NONE, (Wizard) caster);
            ((Wizard) caster).addMonster(goblin);
        }
    }
}
