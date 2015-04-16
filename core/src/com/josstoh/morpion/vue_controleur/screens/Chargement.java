package com.josstoh.morpion.vue_controleur.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.josstoh.morpion.vue_controleur.Jeu;

/**
 * Created by Jocelyn on 14/03/2015.
 */
public class Chargement implements Screen {

    private Jeu jeu;
    private Stage stage;
    private Table table;
    public Chargement(Jeu jeu) {
        this.jeu = jeu;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setSize(jeu.posX(600), jeu.posY(960));
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();
        //jeu.batch.setProjectionMatrix(camera.combined);
        jeu.batch.begin();
        jeu.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(jeu.manager.update()) {
            jeu.manager.get("data/uiskin/uiskin.json",Skin.class).addRegions(jeu.manager.get("data/symboles.pack",TextureAtlas.class));
            jeu.accueil = new MenuAccueil(jeu);
            jeu.setScreen(jeu.accueil);
        }

        // display loading information
        float progress = jeu.manager.getProgress();
        System.out.println(progress);
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
