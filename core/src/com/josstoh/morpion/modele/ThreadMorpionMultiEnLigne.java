package com.josstoh.morpion.modele;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ByteArray;
import com.josstoh.morpion.modele.Coup;
import com.josstoh.morpion.vue_controleur.Jeu;
import com.josstoh.morpion.modele.Joueur;
import com.josstoh.morpion.modele.Plateau;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Jocelyn on 07/04/2015.
 */
public class ThreadMorpionMultiEnLigne extends Thread{
    public Plateau plateau;
    public Joueur[] joueurs;
    private String monId = Jeu.googleServices.obtenirMonParticipantId();

    private String TAG = "Morpion";

    public ThreadMorpionMultiEnLigne(Joueur joueur1, Joueur joueur2)
    {
        Gdx.app.log(TAG,"mon participant id : " + monId);
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

    public void majGrille(int[][] g)
    {
        plateau.setGrille(g);
    }

    @Override
    public void run() {

        Coup coup;
        int reponse;
        while(plateau.checkVictoire()== -1)
        {
            // si joueur local
            if(monId.equals(joueurs[0].getId()))
            {
                try {
                    Gdx.app.log(TAG, "Coup joueur local");
                    synchronized (this){
                        wait();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Gdx.app.log(TAG,"getCoup()");
                coup = joueurs[0].obtenirCoup();
                if(coup != null)
                {
                    if(plateau.coupPossible(coup))
                    {
                        Gdx.app.log(TAG, "coup possible");
                        plateau.jouerCoup(coup);
                        Gdx.app.log(TAG,plateau.toString());
                        Gdx.app.log(TAG, "coup joué, joueur suivant");
                    }

                }
                Gdx.app.log(TAG, "envoie d'un message fiable");
                reponse = Jeu.googleServices.envoyerMessageFiable(Jeu.toMessage(Jeu.MSG_PLATEAU, plateau,TAG), joueurs[1].getId());
                Gdx.app.log(TAG,"message envoyé de " + joueurs[0].getId() + " à " + joueurs[1].getId() + " : " + (reponse != -1) + " reponse : " + reponse);
                joueurSuivant();
                Gdx.app.log(TAG,"Tour adversaire");
            }
            // si joueur distant
            else
            {
                try
                {
                    Gdx.app.log(TAG,"Tour adverse");
                    synchronized (this) {
                        wait();
                    }

                }
                catch (InterruptedException e)
                {
                        e.printStackTrace();
                }
                Gdx.app.log(TAG,"l'adversaire a joué !");
                joueurSuivant();
            }

        }
    }
}





