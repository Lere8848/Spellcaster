package com.ken.spellcaster;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.ken.spellcaster.AIInput.ControlPackage;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;

// 回合管理器
// 回合管理器应当承担收集每回合操作，分配控制器，判断游戏胜负的责任
public class TurnManager {
    public MainGame game;
    public Wizard player, cpu;
    public int currentTurn = 1;
    public int lastTurn = 1;
    public ControlEntity currentEntity = null;
    private boolean isPlayerInput = true;
    private final ChangeListener listener;
    private boolean isWizardEnd = false;
    // 游戏结束变量
    public boolean isFinish = false;

    public TurnManager(MainGame game, ChangeListener listener) {
        this.game = game;
        this.listener = listener;
        isFinish = false;
        this.player = new Wizard(this, "You");
        this.cpu = new Wizard(this, "CPU");
        listener.onGetLog("---The Turn Manager Begin---");
        prepareTurn();
        currentEntity = player;
    }

    // 重开游戏
    public void restart() {
        isFinish = false;
        currentTurn = 1;
        lastTurn = 1;
        player.setHealth(14);
        cpu.setHealth(14);
        player.clearMonster();
        cpu.clearMonster();
        player.clearEffect();
        cpu.clearEffect();
        player.clearSpell();
        cpu.clearSpell();
        player.clearGesture();
        cpu.clearGesture();
        player.useShortLightningBolt = false;
        cpu.useShortLightningBolt = false;
        player.monsterNO = 1;
        cpu.monsterNO = 1;
        game.logLabel.setText("");
    }

    public void prepareTurn() {
        isWizardEnd = false;
        player.startAction();
        for (Monster monster : player.getMonsters()) {
            monster.startAction();
        }
        cpu.startAction();
        for (Monster monster : cpu.getMonsters()) {
            monster.startAction();
        }
    }

    // 搜索下一个待控制的可控制实体
    // 顺序：玩家 - CPU - 玩家monster - CPU monster
    // 若将 CPU 与玩家宠物顺序更换则无法提前判别技能 会不支持 Amnesia 等技能流程
    // Return: 是否完成全部可控制实体选择
    public boolean selectNextControl() {
        if (player.hasTurn()) {
            selectCurrentEntity(player);
            setPlayerInput(currentEntity.getControlWizard() == player);
            return false;
        }
        if (cpu.hasTurn()) {
            selectCurrentEntity(cpu);
            setPlayerInput(currentEntity.getControlWizard() == player);
            return false;
        }
        // 由于只有法师会释放特殊技能 在法师释放完后便应用技能效果 以便特殊技能正确的影响到怪物
        // 如果怪物也会释放特殊技能 便不能这样假设
        if (!isWizardEnd) {
            applyAllSpell();
            isWizardEnd = true;
        }
        for (Monster monster : player.getMonsters()) {
            if (monster.hasTurn()) {
                selectCurrentEntity(monster);
                setPlayerInput(currentEntity.getControlWizard() == player);
                return false;
            }
        }
        for (Monster monster : cpu.getMonsters()) {
            if (monster.hasTurn()) {
                selectCurrentEntity(monster);
                setPlayerInput(currentEntity.getControlWizard() == player);
                return false;
            }
        }
        return true;
    }

    public void setPlayerInput(boolean playerInput) {
        isPlayerInput = playerInput;
    }

    public void selectCurrentEntity(ControlEntity currentEntity) {
        if (this.currentEntity != currentEntity || lastTurn != currentTurn) {
            // 执行回合开始效果
            // 用于限制操作等特殊技能发挥效果
            if (currentEntity != null && !isFinish) {
                log(currentEntity + " Turn...");
                currentEntity.beginEffect();
            }
            // 回合开始更新目标列表
            game.updateTargetList();
            this.currentEntity = currentEntity;
            lastTurn = currentTurn;
        }
    }

    public boolean control(boolean isPlayer) {
        selectNextControl();
        if (isPlayer == isPlayerInput && !isFinish) {
            if (isPlayer) {
                currentEntity.control(game.leftChooseLabel.getText().toString(),
                        game.rightChooseLabel.getText().toString(),
                        game.leftSpellList.getSelected(),
                        game.rightSpellList.getSelected(),
                        game.leftTargetList.getSelected(),
                        game.rightTargetList.getSelected());
            } else {
                ControlPackage pack = AIInput.searchControl(currentEntity, this);
                // AI 输入的位置
                currentEntity.control(pack.leftGesture, pack.rightGesture, pack.leftSpell, pack.rightSpell, pack.leftTarget, pack.rightTarget);
            }
            // 回合结束之处
            if (this.currentEntity != null) {
                this.currentEntity.endEffect();
            }
            boolean isEndTurn = selectNextControl();
            if (isEndTurn) {
                applyAllSpell();
                isEndTurn = selectNextControl();
                // 触发本回合就需运行的效果
                for (ControlEntity entity : getAllTarget()) {
                    entity.applyEffect();
                }
                // 触发效果后可能有技能添加 继续应用
                applyAllSpell();
                if (isEndTurn) {
                    nextTurn();
                }
            }
        }
        return isPlayerInput;
    }

    // 应用所有技能
    public void applyAllSpell() {
        // 对所有实体应用技能
        player.applySpell();
        for (Monster monster : player.getMonsters()) {
            monster.applySpell();
        }
        cpu.applySpell();
        for (Monster monster : cpu.getMonsters()) {
            monster.applySpell();
        }
    }

    public void nextTurn() {
        currentTurn++;
        prepareTurn();
        if (player.getHealth() <= 0 && cpu.getHealth() > 0) {
            isFinish = true;
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    restart();
                }
            }, 6);
            log("Game Over. CPU Win. 6 second after restart.");
        } else if (player.getHealth() > 0 && cpu.getHealth() <= 0) {
            isFinish = true;
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    restart();
                }
            }, 6);
            log("Game Over. You Win. 6 second after restart.");
        } else if (player.getHealth() <= 0 && cpu.getHealth() <= 0) {
            isFinish = true;
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    restart();
                }
            }, 6);
            log("Game Over. No One Win. 6 second after restart.");
        } else {
            selectNextControl();
        }
    }

    public void log(String log) {
        listener.onGetLog("Turn" + currentTurn + " : " + log);
    }

    public void lockChooseLabel(Wizard self, String left, String right) {
        if (self == this.player) {
            game.lockChooseLabel(left, right);
        } else {
            AIInput.lockChooseLabel(left, right);
        }
    }

    // 解锁选择栏
    public void unLockChooseLabel() {
        game.unLockChooseLabel();
        AIInput.unLockChooseLabel();
    }

    public void lockChooseNoCDFS(Wizard self) {
        if (self == this.player) {
            game.lockChooseNoCDFS();
        } else {
            AIInput.lockChooseNoCDFS();
        }
    }

    public void unlockChooseNoCDFS() {
        game.unlockChooseNoCDFS();
        AIInput.unlockChooseNoCDFS();
    }

    public Array<ControlEntity> getAllTarget() {
        Array<ControlEntity> array = new Array<>();
        array.add(player, cpu);
        array.addAll(player.getMonsters());
        array.addAll(cpu.getMonsters());
        return array;
    }

    public Array<ControlEntity> getOtherTarget(ControlEntity entity) {
        Array<ControlEntity> array = getAllTarget();
        ArrayIterator<ControlEntity> iterable = array.iterator();
        while (iterable.hasNext()) {
            if (iterable.next() == entity) {
                iterable.remove();
            }
        }
        return array;
    }

    public Array<ControlEntity> getPlayerTarget() {
        Array<ControlEntity> array = new Array<>();
        array.add(player);
        array.addAll(player.getMonsters());
        return array;
    }

    public Array<ControlEntity> getCPUTarget() {
        Array<ControlEntity> array = new Array<>();
        array.add(cpu);
        array.addAll(cpu.getMonsters());
        return array;
    }

    public Wizard getPlayer() {
        return player;
    }

    public Wizard getCpu() {
        return cpu;
    }

    public interface ChangeListener {
        void onGetLog(String log);
    }
}

