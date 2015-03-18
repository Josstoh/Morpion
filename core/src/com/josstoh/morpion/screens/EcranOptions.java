package com.josstoh.morpion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.josstoh.morpion.Jeu;

/**
 * Created by Jocelyn on 16/03/2015.
 */
public class EcranOptions implements Screen{

    Jeu jeu;
    OrthographicCamera camera;
    Stage stage;
    Skin skin;
    // Bouton
    TextButton gs,retour;


    public EcranOptions(final Jeu monJeu)
    {
        this.jeu = monJeu;
        this.skin = jeu.manager.get("data/uiskin/uiskin.json",Skin.class);

        //Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, jeu.posX(600), jeu.posY(960));

        // Stage et Table
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setSize(jeu.posX(600), jeu.posY(960));
        table.setFillParent(true);
        stage.addActor(table);
        gs = new TextButton("Déconnecté",skin);
        if(Jeu.googleServices.isSignedIn())
            gs.setText("Connecté");
        gs.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if(!Jeu.googleServices.isSignedIn()){
                    Jeu.googleServices.signIn();
                    gs.setText("Connecté");
                }

            }
        });
        table.add(gs).row();
        retour = new TextButton("Retour",skin);
        retour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                jeu.setScreen(new MenuAccueil(monJeu));
                dispose();
            }
        });
        table.add(retour);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        jeu.batch.setProjectionMatrix(camera.combined);
        jeu.batch.begin();
        jeu.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,width,height);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        System.out.println("MenuAccueil : dispose");
        stage.dispose();
    }

}

