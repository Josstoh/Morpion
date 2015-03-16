package com.josstoh.morpion;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.josstoh.morpion.screens.Chargement;
import com.josstoh.morpion.screens.MenuAccueil;
import android.util.Log;
import java.util.Random;



public class Jeu extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;

    public float largeur;
    public float hauteur;
    private PartieSolo partie;

    float largeurRepere = 600;
    float hauteurRepere = 960;
    private String TAG = "Jeu";
    public AssetManager manager;

    public static IGoogleServices googleServices;

    public Jeu(IGoogleServices googleServices)
    {
        super();
        Jeu.googleServices = googleServices;
        manager = new AssetManager();
        manager.load("data/uiskin/uiskin.json",Skin.class);
        manager.load("data/symboles.pack",TextureAtlas.class);
    }

    @Override
    public void create () {

        // Initialisations
        batch = new SpriteBatch();
        // Taille
        hauteur = Gdx.graphics.getHeight();
        largeur = Gdx.graphics.getWidth();
        System.out.println(largeur + " " + hauteur);

        setScreen(new Chargement(this));

    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose()
    {
        Log.i(TAG, "dispose batch et font");
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

    public void creerPartieSolo()
    {
        partie = new PartieSolo(this);
    }

    public static int random(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }


}
