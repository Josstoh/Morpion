package com.josstoh.morpion.screens;

/**
 * Created by Jocelyn on 13/07/2014.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.josstoh.morpion.Jeu;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * Created by Jocelyn on 03/07/2014.
 */
public class MenuAccueil implements Screen {

    Jeu jeu;
    OrthographicCamera camera;
    Stage stage;
    TextButton bNouvellePartie;
    TextButton bOptions;
    TextButton bQuitter;
    TextureAtlas buttonAtlas;
    Image img;
    Skin skin;

    public MenuAccueil(final Jeu monJeu)
    {
        System.out.println(Gdx.app.getType());

        this.jeu = monJeu;
        this.skin = jeu.manager.get("data/uiskin/uiskin.json",Skin.class);

        int largeur = Gdx.graphics.getWidth();
        int hauteur = Gdx.graphics.getHeight();

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


        LabelStyle styleTitre = new LabelStyle(skin.getFont("font_calibril_48pt_bold"),skin.getColor("Blanc"));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("data/accueil.pack"));
        skin.addRegions(buttonAtlas);
        img = new Image(skin.getDrawable("icone"));
        //img.addAction(Actions.sequence(Actions.delay(2), Actions.parallel(Actions.fadeIn(1), Actions.moveBy(100, 100, 2))));
        //img.addAction(Actions.moveBy(-100, -100,2));
        table.add(img).fill().pad(30).row();
        table.pad(30);
        table.row();
        Label titre = new Label("Morpion",styleTitre);
        table.add(titre).height(100).pad(30).row();

        bNouvellePartie = new TextButton("Nouvelle Partie",skin);
        table.add(bNouvellePartie).width(500).height(100).pad(30).row();
        bOptions = new TextButton("Options",skin);
        table.add(bOptions).width(500).height(100).pad(30).row();
        bQuitter = new TextButton("Quitter",skin);
        table.add(bQuitter).width(500).height(100).pad(30).row();


        bNouvellePartie.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                jeu.setScreen(new EcranJeuPartieSolo(monJeu));
                //dispose();
            }
        });
        bOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Jeu.googleServices.signIn();
            }
        });

        bQuitter.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
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
        //buttonAtlas.dispose();
    }
}

