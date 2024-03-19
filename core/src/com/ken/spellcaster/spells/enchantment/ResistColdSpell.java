package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.effects.enchantment.ResistColdEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class ResistColdSpell extends BaseSpell {
    public ResistColdSpell(String gesture, ControlEntity caster) {
        super("Resist Cold", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 对冰元素释放会摧毁冰元素
        if (target instanceof Monster && ((Monster) target).type == ElementType.ICE) {
            target.setHealth(0);
        }
        // 该效果无限长 直到移除效果
        target.addEffect(new ResistColdEffect(0xFFFF, target.getCurrentTurn(), caster));
    }
}
