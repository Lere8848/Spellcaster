package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;

public class PoisonEffect extends BaseEffect {
    int remainCount = 6; // 存储倒计时 还剩几回合

    public PoisonEffect(int duration, int startTurn, ControlEntity caster) {
        super("Poison", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        int tmpRemain = 6 - (self.getCurrentTurn() - startTurn);
        if (tmpRemain != remainCount) {
            remainCount = tmpRemain;
            log("Note! Being inflicted with Poison!");
            log("Only" + remainCount + " rounds left.");
        }
        // 目标将在 6 回合后死亡
        if (self.getCurrentTurn() - startTurn >= 6) {
            log("The death knell is ringing!");
            log("Get Poison more than 6 turns");
            log("the only choice is DEATH!");
            self.setHealth(0);
            removeEffect(self);
        }
    }
}
