package com.ken.spellcaster;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ken.spellcaster.TurnManager.ChangeListener;
import com.ken.spellcaster.entity.ControlEntity;
import com.ken.spellcaster.entity.Monster;
import com.ken.spellcaster.entity.Wizard;
import com.ken.spellcaster.entity.Wizard.DataChangeListener;
import com.ken.spellcaster.effects.BaseEffect;
import com.ken.spellcaster.spells.BaseSpell;
import com.ken.spellcaster.widget.GestureButton;

public class MainGame extends ApplicationAdapter {
    Stage stage;
    Skin skin;
    public Label leftHealthLabel, rightHealthLabel;
    public Label leftGestureLabel, rightGestureLabel;
    public List<Monster> leftMonsterList, rightMonsterList;
    public Label leftEffectLabel, rightEffectLabel;
    public Label leftChooseLabel, rightChooseLabel;
    public List<BaseSpell> leftSpellList, rightSpellList;
    public List<ControlEntity> leftTargetList, rightTargetList;
    public Label logLabel;
    public ScrollPane logPane, leftGesturePane, rightGesturePane;
    public GestureButton leftStabButton, rightStabButton;
    public TextButton submitButton;
    public TurnManager turnManager;
    private boolean isLockChooseLabel = false;
    private boolean isChooseNoCDFS = false;

    @Override
    public void create() {
        stage = new Stage(new FitViewport(1280, 720));
        skin = new Skin(Gdx.files.internal("skin/plain-james-ui.json"));
        leftHealthLabel = new Label("Your Health: 15", skin);
        rightHealthLabel = new Label("AI Health: 15", skin);
        leftGestureLabel = new Label("Left hand: \nRight hand: ", skin);
        rightGestureLabel = new Label("Left hand: \nRight hand: ", skin);
        leftMonsterList = new List<>(skin);
        rightMonsterList = new List<>(skin);
        leftEffectLabel = new Label("Your Effects: ", skin);
        rightEffectLabel = new Label("AI Effects: ", skin);
        leftChooseLabel = new Label("Skip", skin);
        rightChooseLabel = new Label("Skip", skin);
        leftSpellList = new List<>(skin);
        leftTargetList = new List<>(skin);
        rightSpellList = new List<>(skin);
        rightTargetList = new List<>(skin);
        logLabel = new Label("", skin);
        logLabel.setFontScale(0.8f);
        logPane = scrollPaneY(logLabel);
        leftGesturePane = scrollPaneX(leftGestureLabel);
        rightGesturePane = scrollPaneX(rightGestureLabel);
        logLabel.setAlignment(Align.topLeft);
        leftStabButton = new GestureButton("Stab", skin, this, true);
        rightStabButton = new GestureButton("Stab", skin, this, false);
        submitButton = new TextButton("Next", skin);

        turnManager = new TurnManager(this, new ChangeListener() {
            @Override
            public void onGetLog(String log) {
                logLabel.setText(logLabel.getText().append(log).append("\n"));
                logLabel.invalidate();
                logPane.invalidate();
                logPane.scrollTo(0, -1, 0, 0);
            }
        });

        submitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean playerInput = turnManager.control(true);
                while (!playerInput) {
                    playerInput = turnManager.control(false);
                }
                updateSpellList();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        turnManager.getPlayer().setListener(new DataChangeListener() {
            @Override
            public void onHealthChange(int health) {
                leftHealthLabel.setText("Your Health: " + health);
            }

            @Override
            public void onGestureChange(Gesture left, Gesture right) {
                leftGestureLabel.setText(String.format("Left hand: %s\nRight hand: %s", left, right));
                leftGesturePane.scrollTo(Integer.MAX_VALUE, 0, 0, 0);
            }

            @Override
            public void onMonsterChange(Array<Monster> monsters) {
                leftMonsterList.setItems(monsters);
            }

            @Override
            public void onEffectChange(Array<BaseEffect> effects) {
                StringBuilder label = new StringBuilder("Your Effects: ");
                for (BaseEffect effect : effects) {
                    label.append(effect).append(" ");
                }
                leftEffectLabel.setText(label);
            }
        });

        turnManager.getAI().setListener(new DataChangeListener() {
            @Override
            public void onHealthChange(int health) {
                rightHealthLabel.setText("AI Health: " + health);
            }

            @Override
            public void onGestureChange(Gesture left, Gesture right) {
                rightGestureLabel.setText(String.format("Left hand: %s\nRight hand: %s", left, right));
                rightGesturePane.scrollTo(Integer.MAX_VALUE, 0, 0, 0);
            }

            @Override
            public void onMonsterChange(Array<Monster> monsters) {
                rightMonsterList.setItems(monsters);
            }

            @Override
            public void onEffectChange(Array<BaseEffect> effects) {
                StringBuilder label = new StringBuilder("AI Effects: ");
                for (BaseEffect effect : effects) {
                    label.append(effect).append(" ");
                }
                rightEffectLabel.setText(label);
            }
        });
        Table rootTable = new Table(skin);
        rootTable.setFillParent(true);
        // rootTable.setDebug(true);
        rootTable.add(leftHealthLabel).width(1200 / 2f).left().pad(8);
        rootTable.add(rightHealthLabel).width(1200 / 2f).left().pad(8);
        rootTable.row();
        rootTable.add("Your Gesture Stack: ").expandX().left().pad(8);
        rootTable.add("AI Gesture Stack: ").expandX().left().pad(8);
        rootTable.row();
        rootTable.add(leftGesturePane).fill().minHeight(60).left().pad(8);
        rootTable.add(rightGesturePane).fill().minHeight(60).left().pad(8);
        rootTable.row();
        rootTable.add("Your Monsters: ").expandX().left().pad(8);
        rootTable.add("AI Monsters: ").expandX().left().pad(8);
        rootTable.row();

        rootTable.add(scrollPaneY(leftMonsterList)).expandX().minWidth(400).maxHeight(80).left().pad(8);
        rootTable.add(scrollPaneY(rightMonsterList)).expandX().minWidth(400).maxHeight(80).left().pad(8);
        rootTable.row();
        rootTable.add(leftEffectLabel).width(1200 / 2f).left().pad(8);
        rootTable.add(rightEffectLabel).width(1200 / 2f).left().pad(8);
        rootTable.row();

        Table bottomTable = new Table(skin);
        // bottomTable.setDebug(true);
        rootTable.add(bottomTable).colspan(2).pad(8).center();

        Table bottomLeftTable = new Table(skin);
        bottomLeftTable.add("Left Spell");
        bottomLeftTable.row();
        bottomLeftTable.add(scrollPaneY(leftSpellList)).minWidth(200).maxHeight(160);
        bottomLeftTable.row();
        bottomLeftTable.add("Left Target");
        bottomLeftTable.row();
        bottomLeftTable.add(scrollPaneY(leftTargetList)).minWidth(200).maxHeight(160);
        bottomTable.add(bottomLeftTable).top();

        Table leftHandTable = new Table(skin);
        leftHandTable.add(leftChooseLabel).padBottom(16);
        leftHandTable.row();
        Table leftButtonTable = new Table(skin);
        leftButtonTable.add(new GestureButton("S", skin, this, true)).expandX().pad(2);
        leftButtonTable.add(new GestureButton("F", skin, this, true)).expandX().pad(2);
        leftButtonTable.row();
        leftButtonTable.add(new GestureButton("W", skin, this, true)).expandX().pad(2);
        leftButtonTable.add(new GestureButton("P", skin, this, true)).expandX().pad(2);
        leftButtonTable.row();
        leftButtonTable.add(new GestureButton("D", skin, this, true)).expandX().pad(2);
        leftButtonTable.add(new GestureButton("C", skin, this, true)).expandX().pad(2);
        leftButtonTable.row();
        leftButtonTable.add(leftStabButton).colspan(2).expandX().pad(2);
        leftButtonTable.row();
        leftButtonTable.add(new GestureButton("Skip", skin, this, true)).colspan(2).pad(2);
        leftHandTable.add(leftButtonTable);

        bottomTable.add(leftHandTable).pad(8).top();

        Table bottomMiddleTable = new Table(skin);
        // bottomMiddleTable.setDebug(true);
        bottomTable.add(bottomMiddleTable).minWidth(400).pad(8).top();
        bottomMiddleTable.add("Log").expand().pad(8).top();
        bottomMiddleTable.row();
        bottomMiddleTable.add(logPane).minWidth(540).height(200).pad(8).top();
        bottomMiddleTable.row();
        bottomMiddleTable.add(submitButton).minWidth(400).pad(8);

        Table rightHandTable = new Table(skin);
        rightHandTable.add(rightChooseLabel).padBottom(16);
        rightHandTable.row();
        Table rightButtonTable = new Table(skin);
        rightButtonTable.add(new GestureButton("S", skin, this, false)).expandX().pad(2);
        rightButtonTable.add(new GestureButton("F", skin, this, false)).expandX().pad(2);
        rightButtonTable.row();
        rightButtonTable.add(new GestureButton("W", skin, this, false)).expandX().pad(2);
        rightButtonTable.add(new GestureButton("P", skin, this, false)).expandX().pad(2);
        rightButtonTable.row();
        rightButtonTable.add(new GestureButton("D", skin, this, false)).expandX().pad(2);
        rightButtonTable.add(new GestureButton("C", skin, this, false)).expandX().pad(2);
        rightButtonTable.row();
        rightButtonTable.add(rightStabButton).colspan(2).expandX().pad(2);
        rightButtonTable.row();
        rightButtonTable.add(new GestureButton("Skip", skin, this, false)).colspan(2).pad(2);
        rightHandTable.add(rightButtonTable);

        bottomTable.add(rightHandTable).pad(8).top();

        Table bottomRightTable = new Table(skin);
        bottomRightTable.add("Right Spell");
        bottomRightTable.row();
        bottomRightTable.add(scrollPaneY(rightSpellList)).minWidth(200).maxHeight(160);
        bottomRightTable.row();
        bottomRightTable.add("Right Target");
        bottomRightTable.row();
        bottomRightTable.add(scrollPaneY(rightTargetList)).minWidth(200).maxHeight(160);
        bottomTable.add(bottomRightTable).top();

        stage.addActor(rootTable);
        Gdx.input.setInputProcessor(stage);
    }

    // 锁定选择栏
    public void lockChooseLabel(String left, String right) {
        leftChooseLabel.setText(left);
        rightChooseLabel.setText(right);
        suitLetter();
        isLockChooseLabel = true;
    }

    // 解锁选择栏
    public void unLockChooseLabel() {
        isLockChooseLabel = false;
    }

    public void lockChooseNoCDFS() {
        isChooseNoCDFS = true;
        String except = "CDFScdfs";
        if (except.contains(leftChooseLabel.getText().toString())) {
            leftChooseLabel.setText("Skip");
        }
        if (except.contains(rightChooseLabel.getText().toString())) {
            rightChooseLabel.setText("Skip");
        }
    }

    public void unlockChooseNoCDFS() {
        isChooseNoCDFS = false;
    }

    // 设置选择栏 受锁影响
    public void setLeftChooseLabel(String str) {
        if (!isLockChooseLabel) {
            if (!isChooseNoCDFS) {
                leftChooseLabel.setText(str);
            } else {
                String except = "CDFScdfs";
                if (!except.contains(str)) {
                    leftChooseLabel.setText(str);
                }
            }
        }
    }

    public void setRightChooseLabel(String str) {
        if (!isLockChooseLabel) {
            if (!isChooseNoCDFS) {
                rightChooseLabel.setText(str);
            } else {
                String except = "CDFScdfs";
                if (!except.contains(str)) {
                    rightChooseLabel.setText(str);
                }
            }
        }
    }

    // 让选择栏符合规范
    public void suitLetter() {
        if (leftChooseLabel.getText().length > 1 && rightChooseLabel.getText().length == 1) {
            setRightChooseLabel(rightChooseLabel.getText().toString().toUpperCase());
        }
        if (leftChooseLabel.getText().length == 1 && rightChooseLabel.getText().length > 1) {
            setLeftChooseLabel(leftChooseLabel.getText().toString().toUpperCase());
        }
        if (leftChooseLabel.getText().length == 1 && rightChooseLabel.getText().length == 1) {
            if (leftChooseLabel.getText().toString().equalsIgnoreCase(rightChooseLabel.getText().toString())) {
                setLeftChooseLabel(leftChooseLabel.getText().toString().toLowerCase());
                setRightChooseLabel(rightChooseLabel.getText().toString().toLowerCase());
            } else {
                setLeftChooseLabel(leftChooseLabel.getText().toString().toUpperCase());
                setRightChooseLabel(rightChooseLabel.getText().toString().toUpperCase());
            }
        }
    }

    // 更新目标列表 排除 Stab
    public void updateTargetList() {
        boolean leftIsStab = leftChooseLabel.getText().toString().equals("Stab");
        if (leftIsStab) {
            leftTargetList.setItems(turnManager.getOtherTarget(turnManager.player));
        } else {
            leftTargetList.setItems(turnManager.getAllTarget());
        }
        boolean rightIsStab = rightChooseLabel.getText().toString().equals("Stab");
        if (rightIsStab) {
            rightTargetList.setItems(turnManager.getOtherTarget(turnManager.player));
        } else {
            rightTargetList.setItems(turnManager.getAllTarget());
        }
    }

    public void updateSpellList() {
        // 每次必须创建新的 Spell 对象 复用会导致 Spell 无法生效
        // 此处不可优化
        ControlEntity entity = turnManager.currentEntity;
        leftSpellList.setItems();
        rightSpellList.setItems();
        if (entity instanceof Wizard) {
            leftSpellList.setItems(((Wizard) entity).genLeftSpell(leftChooseLabel.getText().toString()));
            rightSpellList.setItems(((Wizard) entity).genRightSpell(rightChooseLabel.getText().toString()));
        }
    }

    private ScrollPane scrollPaneX(Widget widget) {
        ScrollPane pane = new ScrollPane(widget, skin);
        pane.setScrollingDisabled(false, true);
        return pane;
    }

    private ScrollPane scrollPaneY(Widget widget) {
        ScrollPane pane = new ScrollPane(widget, skin);
        pane.setScrollingDisabled(true, false);
        return pane;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
