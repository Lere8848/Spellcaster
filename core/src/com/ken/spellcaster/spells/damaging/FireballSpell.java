package com.ken.spellcaster.spells.damaging;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.MonsterDamageSpell;

public class FireballSpell extends BaseSpell {
    public FireballSpell(String gesture, ControlEntity caster) {
        super("Fireball", gesture, SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (target instanceof Monster) {
            // 火球会直接杀死冰元素
            if (((Monster) target).type == ElementType.ICE) {
                target.setHealth(0);
                target.cancelTurn();
            }
            // 受到火球影响的火元素当回合不会攻击
            if (((Monster) target).type == ElementType.FIRE) {
                target.cancelTurn();
            }
            if (((Monster) target).type == ElementType.NONE) {
                // 这里借用一下怪物的伤害技能 此技能是整个游戏唯一带属性的攻击技能 用于元素攻击
                target.addTempSpell(new MonsterDamageSpell(caster, 5, ElementType.FIRE));
            }
        }
        if (target instanceof Wizard) {
            // 阻止目标释放的冰风暴法术
            for (int i = 0; i < target.currentReceiveSpells.size; i++) {
                BaseSpell spell = target.currentReceiveSpells.get(i);
                if ((spell instanceof IceStormSpell)) {
                    spell.invalid();
                }
            }
            // 这里借用一下怪物的伤害技能 此技能是整个游戏唯一带属性的攻击技能 用于元素攻击
            target.addTempSpell(new MonsterDamageSpell(caster, 5, ElementType.FIRE));
        }
    }
}
