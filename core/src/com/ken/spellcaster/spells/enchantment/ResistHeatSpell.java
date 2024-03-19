package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.effects.enchantment.ResistHeatEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class ResistHeatSpell extends BaseSpell {
    public ResistHeatSpell(String gesture, ControlEntity caster) {
        super("Resist Heat", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 对火元素释放会摧毁火元素
        if (target instanceof Monster && ((Monster) target).type == ElementType.FIRE) {
            target.setHealth(0);
        }
        // 该效果无限长 直到移除效果
        target.addEffect(new ResistHeatEffect(0xFFFF, target.getCurrentTurn(), caster));
    }
}
