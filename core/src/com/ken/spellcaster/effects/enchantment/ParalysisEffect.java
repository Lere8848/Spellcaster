package com.ken.spellcaster.effects.enchantment;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Monster.ElementType;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

public class ParalysisEffect extends BaseEffect {
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

    private String cast(String origin) {
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
            self.getManager().lockChooseLabel(self.getControlWizard(), cast(lastLeft), cast(lastRight));
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        // 解锁按键
        self.getManager().unLockChooseLabel();
    }
}