package com.ken.spellcaster.spells.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.enchantment.DiseaseEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class CureHeavyWoundsSpell extends BaseSpell {
    public CureHeavyWoundsSpell(String gesture, ControlEntity caster) {
        super("Cure Heavy Wounds", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 获得两点假血
        target.setFakeHealth(2);
        // 消除特殊效果 - 疾病
        target.removeEffect(DiseaseEffect.class);
    }
}