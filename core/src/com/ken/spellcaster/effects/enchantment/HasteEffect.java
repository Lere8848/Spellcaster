package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;

public class HasteEffect extends BaseEffect {
    public HasteEffect(int duration, int startTurn, ControlEntity caster) {
        super("Haste", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 每回合可以攻击两次
        self.currentTurnCount = 2;
    }
}
