package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class FearEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log

    public FearEffect(int duration, int startTurn, ControlEntity caster) {
        super("Fear", duration, startTurn, caster);
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Wizard) {
            // 锁定 CDFS 按键 (根据实际操控者决定锁定 AI 还是按钮)
            self.getManager().lockChooseNoCDFS((Wizard) self);
            if (!isTake) {
                log("Being inflicted with Fear, can NOT use C/D/F/S gestures next turn.");
                isTake = true;
            }
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        // 解锁按键
        self.getManager().unlockChooseNoCDFS();
        isTake = false;
    }
}
