package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;

public class DiseaseEffect extends BaseEffect {
    int remainCount = 6; // 存储倒计时 还剩几回合

    public DiseaseEffect(int duration, int startTurn, ControlEntity caster) {
        super("Disease", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        int tmpRemain = 6 - (self.getCurrentTurn() - startTurn);
        if (tmpRemain != remainCount) {
            remainCount = tmpRemain;
            log("Note! Being inflicted with Disease! Only " + remainCount + " rounds remaining until death.");
        }
        // 目标将在 6 回合后死亡
        if (self.getCurrentTurn() - startTurn >= 6) {
            log("The death knell is ringing! Being inflicted with Disease more than 6 turns, the only choice is DEATH!");
            self.setHealth(0);
            removeEffect(self);
        }
    }
}
