package com.ken.spellcaster.spells;

import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.enchantment.*;

// 基础技能类 所有技能均继承自此类
public abstract class BaseSpell {
    String name;
    public String gesture;
    public SpellType type;
    boolean isValid = true;
    public ControlEntity caster;
    // 标识该技能对目标是否有利 用于辅助 AI 判定
    public boolean isGoodForTarget;
    // 镜子反射次数 防止无限反射堆溢出
    public int mirrorCount = 1;

    public BaseSpell(String name, String gesture, SpellType type, ControlEntity caster, boolean isGoodForTarget) {
        this.name = name;
        this.gesture = gesture;
        this.type = type;
        this.caster = caster;
        this.isGoodForTarget = isGoodForTarget;
    }

    // 使该技能失效 无法发挥作用
    public void invalid() {
        isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.equals(name);
        }
        if (obj instanceof BaseSpell) {
            return ((BaseSpell) obj).name.equals(name);
        }
        return false;
    }

    public void action(ControlEntity target) {
        // 一个技能只能生效一次 action 会使技能失效
        invalid();
    }

    // 判断是否抵消特殊技能
    // 不能用迭代器循环 否则会因迭代器套迭代器出现错误
    public boolean enchantmentNotCounter(ControlEntity target, Class<? extends BaseSpell> spellClass) {
        boolean isCounter = false;
        for (int i = 0; i < target.currentReceiveSpells.size; i++) {
            BaseSpell spell = target.currentReceiveSpells.get(i);
            if ((spell instanceof AmnesiaSpell || spell instanceof CharmMonsterSpell || spell instanceof CharmPersonSpell || spell instanceof ConfusionSpell || spell instanceof ParalysisSpell || spell instanceof FearSpell) && !spell.getClass().isAssignableFrom(spellClass)) {
                isCounter = true;
                spell.invalid();
            }
        }
        return !isCounter;
    }

    public enum SpellType {
        PROTECTION,
        SUMMON,
        DAMAGING,
        ENCHANTMENT,
        Non
    }

}
