package com.josstoh.morpion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private Vector<Cellule> cellules;
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


    public EcranJeuPartieSolo(Jeu monJeu)
    {
        this.jeu = monJeu;
        this.skin = jeu.manager.get("data/uiskin/uiskin.json",Skin.class);
        plateau = new Plateau();
        this.cellules = new Vector<Cellule>();

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
        grille.setSize(500,500);
        grille.setBackground("plateau");
        grille.add().row();
        grille.add().row();
        grille.add();
        titre = new Label("Morpion",labelStyle);
        table.add(titre).expandX().pad(30).colspan(3).row();
        //titre.setPosition(350,1200,Align.center);
        tour = new Label("Tour " + numTour,labelStyle);
        table.add(tour).colspan(3).pad(30).row();
        table.add(grille).colspan(3).row();
        //tour.setPosition(250,1100);
        joueur1 = new Label("Joueur 1",labelStyle);
        table.add(joueur1);
        //joueur1.setPosition(100,200);
        vs = new Label("Vs",labelStyle);
        table.add(vs).align(Align.center);
        //vs.setPosition(300,350,Align.cnter);

        joueur2 = new Label("Ordinateur",labelStyle);
        table.add(joueur2).row();
        //joueur2.setPosition(500,200);
        table.debug();
        int i = 0, j;
        while(i<3)
        {
            j = 0;
            while(j<3)
            {

                Cellule cellule = new Cellule(getDrawableFromGrille(plateau.grille[i][j]), i, j);
                int j2 = 0;
                switch (j)
                {
                    case 0:j2 = 2;
                        break;
                    case 1: j2 = 1;
                        break;
                    case 2: j2 = 0;
                        break;

                }
                cellule.setBounds(60 + i * 600 / 3, 400 + j2 * 600 / 3, 200, 200);
                System.out.println("i"+ i +" j" +j+ " x " + (60+i*600/3) + " y " + (400+j2*600/3));

                cellule.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(event.getTarget());
                        Actor a = event.getTarget();
                        Cellule c = null;
                        int caseEn = 0;

                        switch (caseEn) {
                            case 0:
                                if (a == cellules.get(0).getImage()) {
                                    c = cellules.get(0);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 1:
                                if (a == cellules.get(1).getImage()) {
                                    c = cellules.get(1);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 2:
                                if (a == cellules.get(2).getImage()) {
                                    c = cellules.get(2);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 3:
                                if (a == cellules.get(3).getImage()) {
                                    c = cellules.get(3);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 4:
                                if (a == cellules.get(4).getImage()) {
                                    c = cellules.get(4);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 5:
                                if (a == cellules.get(5).getImage()) {
                                    c = cellules.get(5);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 6:
                                if (a == cellules.get(6).getImage()) {
                                    c = cellules.get(6);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 7:
                                if (a == cellules.get(7).getImage()) {
                                    c = cellules.get(7);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                            case 8:
                                if (a == cellules.get(8).getImage()) {
                                    c = cellules.get(8);
                                    if (plateau.grille[c.getposX()][c.getposY()] != 0) {
                                        // déjà jouer
                                        System.out.println("Déjà jouer !");
                                    } else {
                                        plateau.jouerSymbole(c.getposX(), c.getposY(), 1);
                                        J1AJoue = true;
                                    }
                                    break;
                                }
                        }
                        if (c != null) {
                            System.out.println("click, x:" + c.getposX() + " y:" + c.getposY());
                        }
                    }
                });
                cellules.add(cellule);

                j++;
            }
            i++;
        }

        for(Cellule c : cellules)
        {
            stage.addActor(c.image);

        }
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
        for(Cellule c : cellules)
        {
            c.draw(jeu.batch);

        }

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
        int it =0;
        for(int[] i : plateau.grille){
            for(int j : i)
            {
                Cellule tmp;
                tmp = cellules.get(it);
                tmp.setDrawable(getDrawableFromGrille(j));
                cellules.set(it,tmp);
                it++;
            }
        }
    }
}

