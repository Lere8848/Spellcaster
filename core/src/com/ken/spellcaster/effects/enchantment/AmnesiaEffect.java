package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

// 手势重复效果
public class AmnesiaEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log

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
            // 此处修改log 添加
            if (!isTake) {
                log("Being inflicted with Amnesia! next turn can only make the same gestures as before!.");
                isTake = true;
            }
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        // 解锁按键
        self.getManager().unLockChooseLabel();
        isTake = true;
    }
}
