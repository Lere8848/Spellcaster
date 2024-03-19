package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.BaseEffect;

public class DiseaseEffect extends BaseEffect {
    public DiseaseEffect(int duration, int startTurn, ControlEntity caster) {
        super("Disease", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 目标将在 6 回合后死亡
        if (self.getCurrentTurn() - startTurn >= 6) {
            self.setHealth(0);
            removeEffect(self);
        }
    }
}
