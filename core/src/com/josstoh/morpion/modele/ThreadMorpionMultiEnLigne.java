package com.josstoh.morpion.modele;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ByteArray;
import com.josstoh.morpion.modele.Coup;
import com.josstoh.morpion.vue_controleur.Jeu;
import com.josstoh.morpion.modele.Joueur;
import com.josstoh.morpion.modele.Plateau;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

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
        int reponse;
        while(plateau.checkVictoire()== -1)
        {
            // si joueur local
            if(joueurs[0].getId() == monId)
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
                ByteArray m = new ByteArray();
                m.add((byte)1);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out;
                byte[] p = new byte[0];
                try
                {
                    out = new ObjectOutputStream(bos);
                    out.writeObject(plateau);
                    p = bos.toByteArray();
                    if (out != null) {
                        out.close();
                    }
                    bos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.addAll(p, 0, p.length);
                Gdx.app.log(TAG,"envoie d'un message fiable");
                reponse = Jeu.googleServices.envoyerMessageFiable(m.shrink(), joueurs[1].getId());
                Gdx.app.log(TAG,"message envoyé : " + (reponse != -1));
            }
            // si joueur distant
            else
            {

            }
        }



    }


}


