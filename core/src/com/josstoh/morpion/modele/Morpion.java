package com.josstoh.morpion.modele;

import com.badlogic.gdx.Gdx;

/**
 * Created by Jocelyn on 14/04/2015.
 */
public class Morpion extends Thread{
    public Plateau plateau;
    public Joueur[] joueurs;

    private String TAG = "Morpion";

    public Morpion(Joueur joueur1, Joueur joueur2)
    {
        joueurs = new Joueur[2];
        joueurs[0] = joueur1;
        joueurs[1] = joueur2;
        plateau = new Plateau();

    }

    Joueur joueurSuivant()
    {
        Joueur tmp = joueurs[0];
        joueurs[0] = joueurs[1];
        joueurs[1] = tmp;
        return joueurs[0];
    }

    public Joueur obtenirJoueurCourant()
    {
        return joueurs[0];
    }

    @Override
    public void run() {
        Coup coup;
        while(plateau.checkVictoire()== -1)
        {
            if(joueurs[0] instanceof JoueurHumain)
            {
                try {
                    Gdx.app.log(TAG, "hey");
                    synchronized (this){
                        wait();
                    }
                    Gdx.app.log(TAG,"ho");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Gdx.app.log(TAG,"getCoup()");
            coup = joueurs[0].obtenirCoup();
            if(coup != null)
            {
                if(plateau.coupPossible(coup))
                {
                    Gdx.app.log(TAG,"coup possible");
                    plateau.jouerCoup(coup);
                    joueurSuivant();
                    Gdx.app.log(TAG,plateau.toString());
                    Gdx.app.log(TAG, "coup joué, joueur suivant");
                }

            }
        }



    }
}
