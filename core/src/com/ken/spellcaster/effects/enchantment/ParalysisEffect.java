package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class ParalysisEffect extends BaseEffect {
    boolean isTake = false; // 用于判断是否收到改效果影响 如果是 则生成相应log

    public ParalysisEffect(int duration, int startTurn, ControlEntity caster) {
        super("Paralysis", duration, startTurn, caster);
    }

    @Override
    public void actionInRange(ControlEntity self) {
        // 在该回合普通怪物不行动
        if (self instanceof Monster) {
            if (((Monster) self).type == ElementType.NONE) {
                self.cancelTurn();
            }
        }
    }

    private String gestureSwitch(String origin) {
        switch (origin) {
            case "C":
                return "F";
            case "S":
                return "D";
            case "W":
                return "P";
            case "c":
                return "f";
            case "s":
                return "d";
            case "w":
                return "p";
        }
        return origin;
    }

    @Override
    public void actionOnTurnBegin(ControlEntity self) {
        if (self instanceof Wizard) {
            String lastLeft = ((Wizard) self).lastLeftGesture;
            String lastRight = ((Wizard) self).lastRightGesture;
            self.getManager().lockChooseLabel(self.getControlWizard(), gestureSwitch(lastLeft), gestureSwitch(lastRight));
            if (!isTake) {
                log("Being inflicted with Paralysis!");
                log("can only make the same gesture as before!");
                log("Following gesture will be switched");
                log("C-->F、S-->D、W-->P");
                isTake = true;
            }
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        // 解锁按键
        self.getManager().unLockChooseLabel();
        isTake = false;
    }
}