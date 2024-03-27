package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;

public class HasteEffect extends BaseEffect {
    int turn = 0; // 三回合内 0 1 2

    public HasteEffect(int duration, int startTurn, ControlEntity caster) {
        super("Haste", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        if (turn != self.getCurrentTurn()) {
            log("Being inflicted with Haste, can attack twice per round.");
            turn = self.getCurrentTurn();
        }
        // 每回合可以攻击两次
        self.currentTurnCount = 2; // 三回合内 0 1 2
    }
}
