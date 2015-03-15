package com.josstoh.morpion;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Jocelyn on 08/03/2015.
 */
public class DialogFinDePartie extends Dialog {

    Skin skin;
    public DialogFinDePartie (String title,Skin skin) {

        super(title,skin,"dialog");
        this.skin = skin;
        initialize();
    }

    private void initialize() {
        padTop(60); // set padding on top of the dialog title
        getButtonTable().defaults().height(60); // set buttons height
        setModal(true);
        setMovable(false);
        setResizable(false);
    }

    @Override
    public DialogFinDePartie text(String text) {

        super.text(new Label(text, new Label.LabelStyle(skin.getFont("font_calibril_20pt"),skin.getColor("Blanc"))));
        return this;
    }

    /*
     * Adds a text button to the button table.
     * @param listener the input listener that will be attached to the button.
     *
    /*public DialogFinDePartie button(String buttonText, InputListener listener) {
        TextButton button = new TextButton(buttonText, skin);
        button.addListener(listener);
        button(button);
        return this;
    }*/

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 600f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 300f;
    }

    @Override
    protected void result(Object object) {
        super.result(object);
    }
}
