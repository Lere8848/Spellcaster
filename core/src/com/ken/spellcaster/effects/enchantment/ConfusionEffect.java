package com.ken.spellcaster.effects.enchantment;

import com.badlogic.gdx.math.MathUtils;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;

// 随机手势效果
public class ConfusionEffect extends BaseEffect {
    public ConfusionEffect(int duration, int startTurn, ControlEntity caster) {
        super("Confusion", duration, startTurn, caster);
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
            // 锁定按键并覆写随机手势
            String[] range = {"C", "D", "F", "P", "S", "W"};
            String left = range[MathUtils.random(1, 6) - 1];
            String right = range[MathUtils.random(1, 6) - 1];
            self.getManager().lockChooseLabel(self.getControlWizard(), left, right);
        }
    }

    @Override
    public void actionOnTurnEnd(ControlEntity self) {
        // 解锁按键
        self.getManager().unLockChooseLabel();
    }
}