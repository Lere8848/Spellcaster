package com.ken.spellcaster.spells;

import com.ken.spellcaster.entity.ControlEntity;

// 特殊技能 - 投降 将surrender视为一个spell
public class SurrenderSpell extends BaseSpell {
    public SurrenderSpell(String gesture, ControlEntity caster) {
        super("Surrender (Fail)", gesture, SpellType.Non, caster, false);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 生命置 0 游戏结束
        caster.setHealth(0);
    }
}
