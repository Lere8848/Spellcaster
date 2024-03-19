package com.ken.spellcaster.spells.damaging;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.spells.BaseSpell;

public class LightningBoltSpell extends BaseSpell {
    public LightningBoltSpell(String gesture, ControlEntity caster) {
        super("Lightning Bolt", gesture, SpellType.DAMAGING, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 使用了较短的闪电技能后
        if (caster instanceof Wizard && gesture.equals("WDDc")) {
            ((Wizard) caster).useShortLightningBolt = true; //确保该序列只生效一次
        }
        target.attack(5);
    }
}
