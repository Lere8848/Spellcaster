package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class DelayedEffectEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log
    BaseSpell tmpSpell;

    public DelayedEffectEffect(int duration, int startTurn, ControlEntity caster) {
        super("DelayedEffect", duration, startTurn, caster);

    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        // 储存技能
        tmpSpell = baseSpell;
        if (!isTake) {
            //log("                                       "); 最大长度
            log("Thanks to DelayedEffect!");
            log(String.format("%s spell stored for next turn!.", baseSpell));
            isTake = true;
        }
    }
}
