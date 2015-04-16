package com.josstoh.morpion.vue_controleur;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.josstoh.morpion.modele.IGoogleServices;
import com.josstoh.morpion.vue_controleur.screens.Chargement;
import com.josstoh.morpion.vue_controleur.screens.MenuAccueil;

import java.util.Random;



public class Jeu extends Game {

    // Screen
    public MenuAccueil accueil;
    public Chargement chargement;

    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;

    public float largeur;
    public float hauteur;

    float largeurRepere = 600;
    float hauteurRepere = 960;
    private String TAG = "Jeu";
    public AssetManager manager;

    public static IGoogleServices googleServices;

    public Jeu(IGoogleServices googleServices,int screen)
    {
        super();
        Jeu.googleServices = googleServices;
        switch(screen)
        {
            default:    manager = new AssetManager();
                        manager.load("data/uiskin/uiskin.json",Skin.class);
                        manager.load("data/symboles.pack",TextureAtlas.class);
                        break;

        }

    }

    @Override
    public void create () {

        Gdx.app.log(TAG,"create");
        // Initialisations
        batch = new SpriteBatch();
        // Taille
        hauteur = Gdx.graphics.getHeight();
        largeur = Gdx.graphics.getWidth();
        Gdx.app.log(TAG,"résolution écran : " + largeur + " " + hauteur);
        chargement = new Chargement(this);
        setScreen(chargement);

    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose()
    {
        Gdx.app.log(TAG, "dispose batch et font");
        batch.dispose();
        manager.dispose();
    }


    public float posX(float width)
    {
        return width*largeur/largeurRepere;
    }

    public float posY(float height)
    {
        return height*hauteur/hauteurRepere;
    }

    public static int random(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }


}
