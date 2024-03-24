package com.ken.spellcaster.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.ken.spellcaster.TurnManager;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.BaseSpell.SpellType;

// 可控制实体类
// 所有可操作对象均继承此类
public abstract class ControlEntity {
    // 每回合开始要计算该回合 ControlEntity 回合数
    public int currentTurnCount = 1;
    int health;
    // 假血 用于挡伤害及回血
    int fakeHealth = 0;
    final int maxHealth;
    String name;
    TurnManager manager;
    // 该实体实际控制者
    private Wizard controlWizard;
    // 初始控制者
    private Wizard originControlWizard;
    Array<BaseEffect> effects; // 存储受到的Effect
    Array<BaseEffect> permanencyEffects;
    // 每回合接收到的技能 由该实体自行根据状态分类并应用
    public Array<BaseSpell> currentReceiveSpells;

    // 技能运行中新增的技能 需要校验
    public Array<BaseSpell> currentTempSpells;

    // 回合开始使用一次状态
    public ControlEntity(TurnManager manager, String name, int maxHealth) {
        this.manager = manager;
        this.name = name;
        this.maxHealth = maxHealth;
        setHealth(maxHealth);
        effects = new Array<>();
        permanencyEffects = new Array<>();
        currentReceiveSpells = new Array<>();
        currentTempSpells = new Array<>();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    public void setFakeHealth(int health) {
        this.fakeHealth = health;
    }

    public void attack(int health) {
        setHealth(getHealth() - health);
        int lostHealth = maxHealth - getHealth();
        setHealth(getHealth() + fakeHealth);
        if (lostHealth <= fakeHealth) {
            fakeHealth -= lostHealth;
        } else {
            fakeHealth = 0;
        }
    }

    // 每回合开始初始化状态的方法
    public void startAction() {
        currentTurnCount = 1;
        setFakeHealth(0);
        applyEffect();
        currentReceiveSpells.clear();
        currentTempSpells.clear();
        // 每回合开始先添加永久化的状态
        for (BaseEffect effect : permanencyEffects) {
            addEffect(effect);
        }
    }

    public void applyEffect() {
        for (BaseEffect effect : effects) {
            effect.action(this);
        }
        updateEffect();
    }

    public void removeEffect(Class<? extends BaseEffect> effectClass) {
        for (BaseEffect effect : effects) {
            if (effect.getClass().isAssignableFrom(effectClass)) {
                effect.removeEffect(this);
            }
        }
    }

    public void removeAllEffect() {
        for (BaseEffect effect : effects) {
            effect.removeEffect(this);
        }
    }

    public void beginEffect() {
        for (BaseEffect effect : effects) {
            effect.actionOnTurnBegin(this);
        }
        updateEffect();
    }

    public void endEffect() {
        for (BaseEffect effect : effects) {
            effect.actionOnTurnEnd(this);
        }
        updateEffect();
    }

    public void addSpell(BaseSpell spell) {
        if (spell != null) {
            currentReceiveSpells.add(spell);
        }
    }

    public void addTempSpell(BaseSpell spell) {
        if (spell != null) {
            currentTempSpells.add(spell);
        }
    }

    // 计算效果的影响并应用技能
    public void applySpell() {
        // 先根据初始状态进行遴选 （筛去部分不可同时存在的状态）
        for (BaseEffect effect : effects) {
            for (BaseSpell spell : currentReceiveSpells) {
                effect.action2Spell(spell.caster, spell);
            }
        }
        // 先运行非攻击性技能 获得状态和加成与宠物等
        for (BaseSpell spell : currentReceiveSpells) {
            if (spell.isValid() && spell.type != SpellType.DAMAGING) {
                spell.action(this);
            }
        }
        // 根据加成后的状态进行遴选
        for (BaseEffect effect : effects) {
            for (BaseSpell spell : currentReceiveSpells) {
                effect.action2Spell(spell.caster, spell);
            }
        }
        // 再运行攻击性技能 进行伤害
        for (BaseSpell spell : currentReceiveSpells) {
            if (spell.isValid() && spell.type == SpellType.DAMAGING) {
                spell.action(this);
            }
        }
        // 攻击性技能内部可能产生新的攻击性技能 如 FireStorm ，若产生 则 应用效果并再次执行攻击
        for (BaseEffect effect : effects) {
            for (BaseSpell spell : currentTempSpells) {
                effect.action2Spell(spell.caster, spell);
            }
        }
        // 再运行攻击性技能 进行伤害
        for (BaseSpell spell : currentTempSpells) {
            if (spell.isValid() && spell.type == SpellType.DAMAGING) {
                spell.action(this);
                // log(); // 想办法在此处 每次释放技能时，能够生成正确的log
                // 例如： PROTECTION spell + is protected + caster + from + spell.action
            }
        }
    }

    // 判断该可控制实体是否有剩余回合的方法
    public boolean hasTurn() {
        return currentTurnCount > 0;
    }

    // 使可控制实体本回合跳过控制
    public void cancelTurn() {
        currentTurnCount = 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void addEffect(BaseEffect effect) {
        effects.removeValue(effect, false);
        effects.add(effect);
    }

    public void addPermanencyEffect(BaseEffect effect) {
        permanencyEffects.removeValue(effect, false);
        permanencyEffects.add(effect);
    }

    public void updateEffect() {
        ArrayIterator<BaseEffect> iterable = effects.iterator();
        while (iterable.hasNext()) {
            if (iterable.next().isDestroy) {
                iterable.remove();
            }
        }
    }

    // 用于 AOE 攻击 选取除自己外的所有可控制实体
    public Array<ControlEntity> getOtherTarget() {
        return manager.getOtherTarget(this);
    }

    // 根据部分反转控制的状态决定获取到的实际控制者
    public Wizard getControlWizard() {
        return controlWizard;
    }

    public void setControlWizard(Wizard controlWizard) {
        this.controlWizard = controlWizard;
    }

    public void setOriginControlWizard(Wizard controlWizard) {
        this.originControlWizard = controlWizard;
    }

    // 最初始控制者 永远不应改变
    public Wizard getOriginControlWizard() {
        return originControlWizard;
    }

    public TurnManager getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getCurrentTurn() {
        return manager.currentTurn;
    }

    // 每次控制都会使可操作回合数减一
    public void control(String leftGesture, String rightGesture, BaseSpell leftSpell, BaseSpell rightSpell, ControlEntity leftTarget, ControlEntity rightTarget) {
        currentTurnCount--;
    }
}
