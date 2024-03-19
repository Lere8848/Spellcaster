package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class FearEffect extends BaseEffect {
    public FearEffect(int duration, int startTurn, ControlEntity caster) {
        super("Fear", duration, startTurn, caster);
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Wizard) {
            // 锁定 CDFS 按键 (根据实际操控者决定锁定 AI 还是按钮)
            self.getManager().lockChooseNoCDFS((Wizard) self);
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        // 解锁按键
        self.getManager().unlockChooseNoCDFS();
    }
}
