package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.effects.protection.ShieldEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.enchantment.*;

public class PermanencyEffect extends BaseEffect {
    ControlEntity target;

    public PermanencyEffect(int duration, int startTurn, ControlEntity caster, ControlEntity target) {
        super("Permanency", duration, startTurn, caster);
        this.target = target;
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        // 支持永久化的所有法术效果
        if (baseSpell instanceof AmnesiaSpell) {
            target.addPermanencyEffect(new AmnesiaEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof ConfusionSpell) {
            target.addPermanencyEffect(new ConfusionEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof CharmPersonSpell) {
            target.addPermanencyEffect(new CharmPersonEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof CharmMonsterSpell) {
            target.addPermanencyEffect(new CharmMonsterEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof ParalysisSpell) {
            target.addPermanencyEffect(new ParalysisEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof FearSpell) {
            target.addPermanencyEffect(new FearEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof ProtectionFromEvilSpell) {
            target.addPermanencyEffect(new ShieldEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof ResistHeatSpell) {
            target.addPermanencyEffect(new ResistHeatEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof ResistColdSpell) {
            target.addPermanencyEffect(new ResistColdEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof BlindnessSpell) {
            target.addPermanencyEffect(new BlindnessEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof InvisibilitySpell) {
            target.addPermanencyEffect(new InvisibilityEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
        if (baseSpell instanceof HasteSpell) {
            target.addPermanencyEffect(new HasteEffect(0xFFFF, caster.currentTurnCount, caster));
            removeEffect(caster);
        }
    }
}
