package com.ken.spellcaster.spells.damaging;

import com.badlogic.gdx.utils.Array;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.MonsterDamageSpell;

public class FireStormSpell extends BaseSpell {
    public FireStormSpell(String gesture, ControlEntity caster) {
        super("Fire Storm", gesture, SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        Array<ControlEntity> entities = caster.getOtherTarget();
        entities.add(caster);
        // AOE 攻击场上所有目标
        for (ControlEntity entity : entities) {
            // 直接杀死冰元素
            if (entity instanceof Monster && ((Monster) entity).type == ElementType.ICE) {
                entity.setHealth(0);
            }
            // 这里借用一下怪物的伤害技能 此技能是整个游戏唯一带属性的攻击技能 用于元素攻击
            entity.addTempSpell(new MonsterDamageSpell(caster, 5, ElementType.FIRE));
        }
    }
}
