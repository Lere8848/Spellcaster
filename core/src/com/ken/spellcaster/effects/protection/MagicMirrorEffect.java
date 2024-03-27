package com.ken.spellcaster.effects.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.BaseSpell.SpellType;
import com.ken.spellcaster.spells.StabSpell;

public class MagicMirrorEffect extends BaseEffect {
    public MagicMirrorEffect(int duration, int startTurn, ControlEntity caster) {
        super("MagicMirror", duration, startTurn, caster);
    }

    @Override
    public void action2Spell(ControlEntity caster, BaseSpell baseSpell) {
        if (caster instanceof Wizard && baseSpell.type == SpellType.DAMAGING && !(baseSpell instanceof StabSpell)) {
            defendSpellLog(baseSpell); // 添加log
            baseSpell.invalid();
            // 防止无限反射导致堆溢出
            // 如果反射次数小于 0 则技能消失 拥有镜子的实体都没有受到伤害
            if (baseSpell.mirrorCount > 0) {
                baseSpell.mirrorCount--;
                try {
                    BaseSpell spell = baseSpell.getClass().getConstructor(String.class, ControlEntity.class).newInstance(baseSpell.gesture, this.caster);
                    spell.mirrorCount = baseSpell.mirrorCount;
                    caster.addSpell(spell);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
