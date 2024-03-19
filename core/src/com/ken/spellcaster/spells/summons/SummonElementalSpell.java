package com.ken.spellcaster.spells.summons;

import com.badlogic.gdx.math.MathUtils;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

public class SummonElementalSpell extends BaseSpell {
    public SummonElementalSpell(String gesture, ControlEntity caster) {
        super("Summon Elemental", gesture, SpellType.SUMMON, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (target instanceof Wizard) {
            Monster monster = ((Wizard) target).hasElement();
            boolean isFire = MathUtils.randomBoolean();
            ElementType elementType = isFire ? ElementType.FIRE : ElementType.ICE;
            // 只有一方不存在元素怪物才能召唤成功
            if (monster != null) {
                // 若属性不同则互相 Counter 杀死元素怪物
                if (monster.type != elementType) {
                    monster.setHealth(0);
                } else {
                    // 若属性相同则回满元素的状态
                    monster.setHealth(Integer.MAX_VALUE);
                }
            } else {
                ((Wizard) target).addMonster(new Monster(target.getManager(), target + "-Elemental" + ((Wizard) target).monsterNO + " ( " + (isFire ? "Fire" : "Ice") + " )", 3, 3, elementType, (Wizard) target));
            }
        }
    }
}
