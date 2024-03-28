package com.ken.spellcaster.spells.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.enchantment.DiseaseEffect;
import com.ken.spellcaster.effects.enchantment.PoisonEffect;
import com.ken.spellcaster.effects.enchantment.ResistColdEffect;
import com.ken.spellcaster.effects.enchantment.ResistHeatEffect;
import com.ken.spellcaster.effects.protection.RemoveEnchantmentEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class RaiseDeadSpell extends BaseSpell {
    public RaiseDeadSpell(String gesture, ControlEntity caster) {
        super("Raise Dead", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new RemoveEnchantmentEffect(1, target.getCurrentTurn(), caster));
        // 清除不利的特殊效果
        target.removeEffect(ResistHeatEffect.class);
        target.removeEffect(ResistColdEffect.class);
        target.removeEffect(DiseaseEffect.class);
        target.removeEffect(PoisonEffect.class);
        // 回满 ，setHealth 会自动到血量最大值
        target.setHealth(Integer.MAX_VALUE);
        log("Buffed by RaiseDead Spell");
        log("Gain full health!");
        log("All bad effects removed!");
    }
}
