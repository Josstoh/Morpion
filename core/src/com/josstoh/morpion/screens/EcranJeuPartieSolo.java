package com.josstoh.morpion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.josstoh.morpion.DialogFinDePartie;
import com.josstoh.morpion.Jeu;
import com.josstoh.morpion.metier.Cellule;
import com.josstoh.morpion.metier.Plateau;

import java.util.Vector;

/**
 * Created by Jocelyn on 03/07/2014.
 */
public class EcranJeuPartieSolo implements Screen {

    Jeu jeu;
    Label.LabelStyle labelStyle;
    private OrthographicCamera camera;
    private Stage stage;
    private Plateau plateau;
    private Vector<ImageButton> cellules;
    public boolean J1AJoue = false;
    private int numTour = 1;
    Skin skin;

    // Labels
    Label titre;
    Label vs;
    Label joueur1;
    Label joueur2;
    Label tour;

    //Sprite
    Sprite imgPlateau;

    // Image
    Image i1,i2,i3,i4,i5,i6,i7,i8,i9;


    public EcranJeuPartieSolo(Jeu monJeu)
    {
        this.jeu = monJeu;
        this.skin = jeu.manager.get("data/uiskin/uiskin.json",Skin.class);
        plateau = new Plateau();
        this.cellules = new Vector<ImageButton>();

        //Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, jeu.posX(600), jeu.posY(960));

        // Stage et Table
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        //table.setSize(jeu.posX(600), jeu.posY(960));
        table.setFillParent(true);
        stage.addActor(table);
        table.top();

        labelStyle = new Label.LabelStyle(skin.getFont("font_calibril_48pt_bold"),skin.getColor("Rouge"));

        // Label
        Table grille = new Table(skin);
        grille.setSize(600, 600);
        grille.setBackground("plateau");
        grille.defaults().width(200).height(200);
        i1 = new Image(getDrawableFromGrille(plateau.grille[0][0]));
        i1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[0][0] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(0,0,1);
                    J1AJoue = true;
                }

            }
        });
        grille.add(i1);
        i2 = new Image(getDrawableFromGrille(plateau.grille[0][1]));
        i2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[0][1] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(0,1,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i2).uniform();
        i3 = new Image(getDrawableFromGrille(plateau.grille[0][2]));
        i3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[0][2] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(0,2,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i3).row();
        i4 = new Image(getDrawableFromGrille(plateau.grille[1][0]));
        i4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[1][0] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(1,0,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i4);
        i5 = new Image(getDrawableFromGrille(plateau.grille[1][1]));
        i5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[1][1] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(1,1,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i5);
        i6 = new Image(getDrawableFromGrille(plateau.grille[1][2]));
        i6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[1][2] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(1,2,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i6).row();
        i7 = new Image(getDrawableFromGrille(plateau.grille[2][0]));
        i7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[2][0] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(2,0,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i7);
        i8 = new Image(getDrawableFromGrille(plateau.grille[2][1]));
        i8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[2][1] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(2,1,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i8);
        i9 = new Image(getDrawableFromGrille(plateau.grille[2][2]));
        i9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (plateau.grille[2][2] != 0) {
                    // déjà jouer
                    System.out.println("Déjà jouer !");
                } else {
                    System.out.println("appuyer");
                    plateau.jouerSymbole(2,2,1);
                    J1AJoue = true;
                }
            }
        });
        grille.add(i9);
        titre = new Label("Morpion",labelStyle);
        table.add(titre).expandX().pad(30).colspan(3).row();
        tour = new Label("Tour " + numTour,labelStyle);
        table.add(tour).colspan(3).pad(30).row();
        table.add().height(100).row();
        table.add(grille).width(600).height(600).colspan(3).row();
        joueur1 = new Label("Joueur 1",labelStyle);
        table.add(joueur1);
        vs = new Label("Vs",labelStyle);
        table.add(vs).align(Align.center);
        joueur2 = new Label("Ordinateur",labelStyle);
        table.add(joueur2).row();
        table.debug();
        grille.debug();





        imgPlateau = new Sprite(skin.getSprite("plateau"));
        imgPlateau.setBounds(60,400,600,600);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean j1 = true;
                while(plateau.checkVictoire()== -1) {
                    System.out.println(plateau.checkVictoire());
                    if(j1)
                    {

                        System.out.println("Tour J1");
                        while(!J1AJoue)
                        {
                        }
                        J1AJoue = !J1AJoue;


                    }

                    else
                    {
                        System.out.println("Tour J2");
                        boolean j2Jouer = false;
                        int i,j;
                        do {
                            i = Jeu.random(0, 2);
                            j = Jeu.random(0, 2);
                            if(plateau.grille[i][j] == 0)
                            {
                                plateau.jouerSymbole(i, j,2);
                                j2Jouer = true;
                            }


                        }while(!j2Jouer);
                        numTour++;

                    }


                    j1 = !j1;

                }

                String result;
                int resultat = plateau.checkVictoire();
                if (resultat == 0)
                {
                    result = "Match Nul";
                }
                else
                {
                    if(resultat == 1)
                    {
                        result = "Victoire";
                    }
                    else
                    {
                        result = "Défaite";
                    }

                }
                DialogFinDePartie diag = new DialogFinDePartie(result,skin) {
                    public void result(Object obj) {
                        switch((Integer)obj)
                        {
                            case 0:     jeu.setScreen(new EcranJeuPartieSolo(jeu));
                                        dispose();
                                        break;
                            case 1:     jeu.setScreen(new MenuAccueil(jeu));
                                        dispose();
                                        break;
                        }
                    }
                };
                diag.text("Fin de Partie");
                diag.button("Nouvelle Partie", 0);
                diag.button("Accueil", 1);
                diag.show(stage);


            }
        }).start();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.25f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MAJCellules();
        if(!J1AJoue)
            tour.setText("Votre tour : " +numTour);
        else
            tour.setText("Tour adverse : " + numTour);
        camera.update();
        jeu.batch.setProjectionMatrix(camera.combined);
        jeu.batch.begin();




        //imgPlateau.draw(jeu.batch,1);


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
        stage.dispose();
        plateau = null;

    }

    Drawable getDrawableFromGrille(int i) {
        if(i == 0)
        {
            return skin.getDrawable("0");
        }
        if(i == 1)
        {
            return skin.getDrawable("croix_rouge");

        }
        else
        {
            return skin.getDrawable("rond_bleu");
        }

    }

    private void MAJCellules()
    {
        i1.setDrawable(getDrawableFromGrille(plateau.grille[0][0]));
        i2.setDrawable(getDrawableFromGrille(plateau.grille[0][1]));
        i3.setDrawable(getDrawableFromGrille(plateau.grille[0][2]));
        i4.setDrawable(getDrawableFromGrille(plateau.grille[1][0]));
        i5.setDrawable(getDrawableFromGrille(plateau.grille[1][1]));
        i6.setDrawable(getDrawableFromGrille(plateau.grille[1][2]));
        i7.setDrawable(getDrawableFromGrille(plateau.grille[2][0]));
        i8.setDrawable(getDrawableFromGrille(plateau.grille[2][1]));
        i9.setDrawable(getDrawableFromGrille(plateau.grille[2][2]));
    }
}

