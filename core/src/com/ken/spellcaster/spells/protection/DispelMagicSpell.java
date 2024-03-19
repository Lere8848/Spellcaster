package com.ken.spellcaster.spells.protection;

import com.badlogic.gdx.utils.Array;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.effects.enchantment.DiseaseEffect;
import com.ken.spellcaster.effects.enchantment.PoisonEffect;
import com.ken.spellcaster.effects.enchantment.ResistColdEffect;
import com.ken.spellcaster.effects.enchantment.ResistHeatEffect;
import com.ken.spellcaster.effects.protection.DispelMagicEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class DispelMagicSpell extends BaseSpell {
    public DispelMagicSpell(String gesture, ControlEntity caster) {
        super("Dispel Magic", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 对所有可选择实体造成影响
        Array<ControlEntity> entities = target.getOtherTarget();
        entities.add(target);
        for (ControlEntity entity : entities) {
            entity.addEffect(new DispelMagicEffect(1, target.getCurrentTurn(), caster));
            // 移除部分效果
            entity.removeEffect(ResistHeatEffect.class);
            entity.removeEffect(ResistColdEffect.class);
            entity.removeEffect(DiseaseEffect.class);
            entity.removeEffect(PoisonEffect.class);
            // 受到该法术作用的 Monster 会全部死亡 （清空场上所有 Monster）
            if (entity instanceof Monster) {
                entity.setHealth(0);
            }
        }
    }
}