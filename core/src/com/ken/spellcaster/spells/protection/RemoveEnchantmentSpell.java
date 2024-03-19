package com.ken.spellcaster.spells.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.effects.enchantment.DiseaseEffect;
import com.ken.spellcaster.effects.enchantment.PoisonEffect;
import com.ken.spellcaster.effects.enchantment.ResistColdEffect;
import com.ken.spellcaster.effects.enchantment.ResistHeatEffect;
import com.ken.spellcaster.effects.protection.RemoveEnchantmentEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class RemoveEnchantmentSpell extends BaseSpell {
    public RemoveEnchantmentSpell(String gesture, ControlEntity caster) {
        super("Remove Enchantment", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new RemoveEnchantmentEffect(1, target.getCurrentTurn(), caster));
        // 移除部分效果
        target.removeEffect(ResistHeatEffect.class);
        target.removeEffect(ResistColdEffect.class);
        target.removeEffect(DiseaseEffect.class);
        target.removeEffect(PoisonEffect.class);
        // 受到该法术作用的 Monster 会死亡
        if (target instanceof Monster) {
            target.setHealth(0);
        }
    }
}
