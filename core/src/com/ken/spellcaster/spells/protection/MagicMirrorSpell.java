package com.ken.spellcaster.spells.protection;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.effects.protection.MagicMirrorEffect;
import com.ken.spellcaster.spells.BaseSpell;

public class MagicMirrorSpell extends BaseSpell {
    public MagicMirrorSpell(String gesture, ControlEntity caster) {
        super("Magic Mirror", gesture, SpellType.PROTECTION, caster, true);
    }

    @Override
    public void action(ControlEntity target) {
        super.action(target);
        target.addEffect(new MagicMirrorEffect(1, target.getCurrentTurn(), caster));
    }
}