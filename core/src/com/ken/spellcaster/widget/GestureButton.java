package com.ken.spellcaster.widget;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ken.spellcaster.MainGame;

public class GestureButton extends TextButton {

    public GestureButton(final String text, Skin skin, final MainGame game, final boolean isLeft) {
        super(text, skin);
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isLeft) {
                    game.setLeftChooseLabel(text);
                    boolean leftIsStab = game.leftChooseLabel.getText().toString().equals("Stab");
                    game.rightStabButton.setVisible(!leftIsStab);
                } else {
                    game.setRightChooseLabel(text);
                    boolean rightIsStab = game.rightChooseLabel.getText().toString().equals("Stab");
                    game.leftStabButton.setVisible(!rightIsStab);
                }
                game.suitLetter();
                game.updateTargetList();
                game.updateSpellList();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}
