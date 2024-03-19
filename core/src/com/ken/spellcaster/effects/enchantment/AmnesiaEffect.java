package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

// 手势重复效果
public class AmnesiaEffect extends BaseEffect {
    public AmnesiaEffect(int duration, int startTurn, ControlEntity caster) {
        super("Amnesia", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 在该回合内攻击所有人
        if (self instanceof Monster) {
            for (ControlEntity entity : self.getOtherTarget()) {
                self.control(null, null, null, null, null, entity);
            }
        }
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Wizard) {
            // 锁定按键 (根据实际操控者决定锁定 AI 还是按钮)
            self.getManager().lockChooseLabel(self.getControlWizard(), ((Wizard) self).lastLeftGesture, ((Wizard) self).lastRightGesture);
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        // 解锁按键
        self.getManager().unLockChooseLabel();
    }
}
