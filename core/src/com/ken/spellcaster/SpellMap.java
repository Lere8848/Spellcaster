package com.ken.spellcaster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.spells.BaseSpell.SpellType;
import com.ken.spellcaster.spells.SurrenderSpell;
import com.ken.spellcaster.spells.damaging.*;
import com.ken.spellcaster.spells.enchantment.*;
import com.ken.spellcaster.spells.protection.*;
import com.ken.spellcaster.spells.summons.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 生成技能映射类
public class SpellMap {
    public static Map<String, String> gestureSpellMap;

    static {
        gestureSpellMap = new HashMap<>();
        String[] spellList = Gdx.files.internal("gesture2spell.txt").readString().split("\n");
        for (String s : spellList) {
            String[] i = s.split(":");
            gestureSpellMap.put(i[0], i[1]);
        }
    }

    // 根据类型获得该类型的所有可释放手势字符串
    public static Array<String> getGestureByType(SpellType type) {
        Array<String> array = new Array<>();
        for (String s : gestureSpellMap.keySet()) {
            if (Objects.requireNonNull(findSpell(gestureSpellMap.get(s), "", null)).type == type) {
                array.add(s);
            }
        }
        return array;
    }

    // 获取所有可释放手势字符串
    public static Array<String> getAllGesture() {
        Array<String> array = new Array<>();
        for (String s : gestureSpellMap.keySet()) {
            array.add(s);
        }
        return array;
    }

    // 技能映射 添加技能时在此处登记
    public static BaseSpell findSpell(String name, String gesture, ControlEntity caster) {
        switch (name) {
            case "Shield":
                return new ShieldSpell(gesture, caster);
            case "Remove enchantment":
                return new RemoveEnchantmentSpell(gesture, caster);
            case "Magic mirror":
                return new MagicMirrorSpell(gesture, caster);
            case "Counter-spell":
                return new CounterSpellSpell(gesture, caster);
            case "Dispel magic":
                return new DispelMagicSpell(gesture, caster);
            case "Raise dead":
                return new RaiseDeadSpell(gesture, caster);
            case "Cure light wounds":
                return new CureLightWoundsSpell(gesture, caster);
            case "Cure heavy wounds":
                return new CureHeavyWoundsSpell(gesture, caster);
            case "Summon goblin":
                return new SummonGoblinSpell(gesture, caster);
            case "Summon ogre":
                return new SummonOgreSpell(gesture, caster);
            case "Summon troll":
                return new SummonTrollSpell(gesture, caster);
            case "Summon giant":
                return new SummonGiantSpell(gesture, caster);
            case "Summon elemental":
                return new SummonElementalSpell(gesture, caster);
            case "Missile":
                return new MissileSpell(gesture, caster);
            case "Finger of death":
                return new FingerOfDeathSpell(gesture, caster);
            case "Lightning bolt":
                return new LightningBoltSpell(gesture, caster);
            case "Cause light wounds":
                return new CauseLightWoundsSpell(gesture, caster);
            case "Cause heavy wounds":
                return new CauseHeavyWoundsSpell(gesture, caster);
            case "Fireball":
                return new FireballSpell(gesture, caster);
            case "Fire storm":
                return new FireStormSpell(gesture, caster);
            case "Ice storm":
                return new IceStormSpell(gesture, caster);
            case "Amnesia":
                return new AmnesiaSpell(gesture, caster);
            case "Confusion":
                return new ConfusionSpell(gesture, caster);
            case "Charm person":
                return new CharmPersonSpell(gesture, caster);
            case "Charm monster":
                return new CharmMonsterSpell(gesture, caster);
            case "Paralysis":
                return new ParalysisSpell(gesture, caster);
            case "Fear":
                return new FearSpell(gesture, caster);
            case "Anti-spell":
                return new AntiSpellSpell(gesture, caster);
            case "Protection from evil":
                return new ProtectionFromEvilSpell(gesture, caster);
            case "Resist heat":
                return new ResistHeatSpell(gesture, caster);
            case "Resist cold":
                return new ResistColdSpell(gesture, caster);
            case "Disease":
                return new DiseaseSpell(gesture, caster);
            case "Poison":
                return new PoisonSpell(gesture, caster);
            case "Blindness":
                return new BlindnessSpell(gesture, caster);
            case "Invisibility":
                return new InvisibilitySpell(gesture, caster);
            case "Haste":
                return new HasteSpell(gesture, caster);
            case "Time stop":
                return new TimeStopSpell(gesture, caster);
            case "Delayed effect":
                return new DelayedEffectSpell(gesture, caster);
            case "Permanency":
                return new PermanencySpell(gesture, caster);
            case "Surrender":
                return new SurrenderSpell(gesture, caster);
            default:
                return null;
        }
    }
}
