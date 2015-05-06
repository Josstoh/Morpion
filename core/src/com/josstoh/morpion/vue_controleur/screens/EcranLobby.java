package com.josstoh.morpion.vue_controleur.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.josstoh.morpion.modele.Joueur;
import com.josstoh.morpion.vue_controleur.Jeu;

import java.util.ArrayList;

/**
 * Created by Jocelyn on 06/05/2015.
 */
public class EcranLobby implements Screen {

    private Jeu jeu;
    private Stage stage;
    private Table table;
    private Skin skin;
    private ArrayList<String> participants;

    // Labels
    Label lPretJ1;
    Label lPretJ2;

    // Boutons
    TextButton bPret;
    TextButton bQuitter;

    public EcranLobby(Jeu jeu, Joueur j1, Joueur j2) {
        Label.LabelStyle ls48Rouge = new Label.LabelStyle(skin.getFont("font_calibril_48pt_bold"), skin.getColor("Rouge"));
        this.jeu = jeu;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = jeu.manager.get("data/uiskin/uiskin.json", Skin.class);
        table = new Table();
        table.setSize(jeu.posX(600), jeu.posY(960));
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Salle d'attente",ls48Rouge)).row();
        table.add(new Label(j1.getNom(), ls48Rouge));
        table.add(new Label(j2.getNom(), ls48Rouge)).row();
        table.add();
        table.add().row();
        table.add();
        table.add().row();
        lPretJ1 = new Label("Pas prêt",ls48Rouge);
        table.add(lPretJ1);
        lPretJ2 = new Label("Pas prêt",ls48Rouge);
        table.add(lPretJ2).row();
        bQuitter = new TextButton("Quitter", skin);
        bQuitter.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Jeu.googleServices.quitterPartieMulti();
            }
        });
        bPret.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        table.add(bQuitter);
        table.add(bPret);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        jeu.batch.begin();
        jeu.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

