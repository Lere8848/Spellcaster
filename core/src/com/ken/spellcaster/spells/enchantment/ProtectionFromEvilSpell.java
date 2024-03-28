package com.ken.spellcaster.spells.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.protection.ShieldEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class ProtectionFromEvilSpell extends BaseSpell {
    public ProtectionFromEvilSpell(String gesture, ControlEntity caster) {
        super("Protection from Evil", gesture, SpellType.ENCHANTMENT, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        // 这一回合和接下来的三回合 共四回合护盾效果
        target.addEffect(new ShieldEffect(4, target.getCurrentTurn(), caster));
        //log("                                       "); 最大长度
        log("Buffed by ProtectionFromEvil spell,");
        log("Have Shield for next 4 turns");
    }
}

