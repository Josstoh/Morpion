package com.josstoh.morpion.vue_controleur.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.josstoh.morpion.vue_controleur.DialogFinDePartie;
import com.josstoh.morpion.vue_controleur.Jeu;
import com.josstoh.morpion.modele.ThreadMorpionMultiEnLigne;
import com.josstoh.morpion.modele.Coup;
import com.josstoh.morpion.modele.Joueur;
import com.josstoh.morpion.modele.JoueurHumain;
import com.josstoh.morpion.modele.Plateau;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by Jocelyn on 03/07/2014.
 */
public class EcranJeuMulti implements Screen, Observer {

    public ThreadMorpionMultiEnLigne morpion;
    public Jeu jeu;
    Label.LabelStyle labelStyle;
    private OrthographicCamera camera;
    public Stage stage;
    private Vector<ImageButton> cellules;
    private String TAG = "Partie multi";
    public int numTour = 1;
    public Skin skin;
    public ArrayList<String> participants;

    int symb;
    // Labels
    Label titre;
    Label vs;
    Label lJoueur1;
    Label lJoueur2;
    Label tour;

    //Sprite
    Sprite imgPlateau;

    // Image
    Image i1, i2, i3, i4, i5, i6, i7, i8, i9;


    public EcranJeuMulti(Jeu monJeu, ArrayList<String> p) {
        participants = p;
        this.jeu = monJeu;
        this.skin = jeu.manager.get("data/uiskin/uiskin.json", Skin.class);
        this.cellules = new Vector<>();
        JoueurHumain joueur1 = Jeu.googleServices.creerJoueur(participants.get(0));
        JoueurHumain joueur2 = Jeu.googleServices.creerJoueur(participants.get(1));

        if(Jeu.googleServices.obtenirMonParticipantId().equals(joueur1.getId()))
            symb = 1;
        else
            symb = 2;

        morpion = new ThreadMorpionMultiEnLigne(joueur1, joueur2);
        morpion.plateau.addObserver(this);
        morpion.start();

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

        labelStyle = new Label.LabelStyle(skin.getFont("font_calibril_48pt_bold"), skin.getColor("Rouge"));

        // Label
        Table grille = new Table(skin);
        grille.setSize(600, 600);
        grille.setBackground("plateau");
        grille.defaults().width(200).height(200);
        i1 = new Image(getDrawableFromGrille(morpion.plateau.grille[0][0]));
        i1.addListener(new CelluleListener());
        grille.add(i1);
        i2 = new Image(getDrawableFromGrille(morpion.plateau.grille[0][1]));
        i2.addListener(new CelluleListener());
        grille.add(i2).uniform();
        i3 = new Image(getDrawableFromGrille(morpion.plateau.grille[0][2]));
        i3.addListener(new CelluleListener());
        grille.add(i3).row();
        i4 = new Image(getDrawableFromGrille(morpion.plateau.grille[1][0]));
        i4.addListener(new CelluleListener());
        grille.add(i4);
        i5 = new Image(getDrawableFromGrille(morpion.plateau.grille[1][1]));
        i5.addListener(new CelluleListener());
        grille.add(i5);
        i6 = new Image(getDrawableFromGrille(morpion.plateau.grille[1][2]));
        i6.addListener(new CelluleListener());
        grille.add(i6).row();
        i7 = new Image(getDrawableFromGrille(morpion.plateau.grille[2][0]));
        i7.addListener(new CelluleListener());
        grille.add(i7);
        i8 = new Image(getDrawableFromGrille(morpion.plateau.grille[2][1]));
        i8.addListener(new CelluleListener());
        grille.add(i8);
        i9 = new Image(getDrawableFromGrille(morpion.plateau.grille[2][2]));
        i9.addListener(new CelluleListener());
        grille.add(i9);
        titre = new Label("Morpion", labelStyle);
        table.add(titre).expandX().pad(30).colspan(3).row();
        tour = new Label("Tour " + numTour, labelStyle);
        table.add(tour).colspan(3).pad(30).row();
        table.add().height(100).row();
        table.add(grille).width(600).height(600).colspan(3).row();
        lJoueur1 = new Label(joueur1.getNom(), labelStyle);
        table.add(lJoueur1);
        vs = new Label("Vs", labelStyle);
        table.add(vs).align(Align.center);
        lJoueur2 = new Label(joueur2.getNom(), labelStyle);
        table.add(lJoueur2).row();
        table.debug();
        grille.debug();


        imgPlateau = new Sprite(skin.getSprite("plateau"));
        imgPlateau.setBounds(60, 400, 600, 600);
        Gdx.app.log(TAG, participants.toString());

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.25f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MAJCellules();
        tour.setText("Votre tour : " + numTour);
        camera.update();
        jeu.batch.setProjectionMatrix(camera.combined);
        jeu.batch.begin();
        jeu.batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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

    }

    Drawable getDrawableFromGrille(int i) {
        if (i == 0) {
            return skin.getDrawable("0");
        }
        if (i == 1) {
            return skin.getDrawable("croix_rouge");

        } else {
            return skin.getDrawable("rond_bleu");
        }

    }

    private void MAJCellules() {
        i1.setDrawable(getDrawableFromGrille(morpion.plateau.grille[0][0]));
        i2.setDrawable(getDrawableFromGrille(morpion.plateau.grille[0][1]));
        i3.setDrawable(getDrawableFromGrille(morpion.plateau.grille[0][2]));
        i4.setDrawable(getDrawableFromGrille(morpion.plateau.grille[1][0]));
        i5.setDrawable(getDrawableFromGrille(morpion.plateau.grille[1][1]));
        i6.setDrawable(getDrawableFromGrille(morpion.plateau.grille[1][2]));
        i7.setDrawable(getDrawableFromGrille(morpion.plateau.grille[2][0]));
        i8.setDrawable(getDrawableFromGrille(morpion.plateau.grille[2][1]));
        i9.setDrawable(getDrawableFromGrille(morpion.plateau.grille[2][2]));
    }

    @Override
    public void update(Observable o, Object arg) {

        Gdx.app.log(TAG, "update");
        MAJCellules();
        String result;
        if(numTour%2 == 1)
        {
            tour.setText("Tour de " + lJoueur1.getText());
        }
        else
        {
            tour.setText("Tour de " + lJoueur2.getText());
        }
        int resultat = (int) arg;
        if (arg != -1) {
            if (resultat == 0) {
                result = "Match Nul";
            } else {
                if (resultat == 1) {
                    result = "Victoire";
                } else {
                    result = "Défaite";
                }

            }
            DialogFinDePartie diag = new DialogFinDePartie(result, skin) {
                public void result(Object obj) {
                    switch ((Integer) obj) {
                        case 0:
                            jeu.setScreen(new EcranJeuPartieSolo(jeu));
                            dispose();
                            break;
                        case 1:
                            Gdx.app.log(TAG, "cas 1");

                            jeu.setScreen(jeu.accueil);
                            Gdx.input.setInputProcessor(jeu.accueil.stage);

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

    }

    private class CelluleListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.log(TAG, "touché !");
            Image i = (Image) event.getTarget();
            Joueur j = morpion.obtenirJoueurCourant();
            Coup c = null;
            if (j instanceof JoueurHumain) {
                Gdx.app.log(TAG, "hoy");
                if (i.equals(i1)) {
                    c = new Coup(0, 0, symb);

                } else if (i.equals(i2)) {
                    c = new Coup(0, 1, symb);

                } else if (i.equals(i3)) {
                    c = new Coup(0, 2, symb);

                } else if (i.equals(i4)) {
                    c = new Coup(1, 0, symb);

                } else if (i.equals(i5)) {
                    c = new Coup(1, 1, symb);

                } else if (i.equals(i6)) {
                    c = new Coup(1, 2, symb);

                } else if (i.equals(i7)) {
                    c = new Coup(2, 0, symb);

                } else if (i.equals(i8)) {
                    c = new Coup(2, 1, symb);

                } else if (i.equals(i9)) {
                    c = new Coup(2, 2, symb);
                }
                if (morpion.plateau.coupPossible(c)) {
                    j.setCoup(c);
                    synchronized (morpion) {

                        morpion.notify();
                    }
                } else {
                    Gdx.app.log(TAG, "Déjà joué !");
                }

            }
        }
    }
}
