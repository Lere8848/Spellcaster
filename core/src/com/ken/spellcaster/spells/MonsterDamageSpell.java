package com.ken.spellcaster.spells;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.spells.BaseSpell;

// 怪物攻击释放专用技能 将monster释放攻击视为一个spell
public class MonsterDamageSpell extends BaseSpell {
    int attack;
    public ElementType type;

    public MonsterDamageSpell(ControlEntity caster, int attack, ElementType type) {
        super("Monster Attack", "", SpellType.DAMAGING, caster, false);
        this.attack = attack;
        this.type = type;
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        if (target instanceof Monster) {
            if (((Monster) target).type != ElementType.NONE) {
                // 若攻击对象为相反元素
                // 则直接杀死怪物
                if (((Monster) target).type != type) {
                    target.setHealth(0);
                }
                // 取消目标该回合行动权
                target.cancelTurn();
            }
        }
        target.attack(attack);
    }
}
