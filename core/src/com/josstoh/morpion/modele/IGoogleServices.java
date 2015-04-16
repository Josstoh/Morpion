package com.josstoh.morpion.modele;

import com.josstoh.morpion.modele.Joueur;
import com.josstoh.morpion.modele.JoueurHumain;

import java.util.ArrayList;

/**
 * Created by Jocelyn on 04/03/2015.
 */
public interface IGoogleServices
{
    void signIn();
    void signOut();
    void rateGame();
    void submitScore(long score);
    void showScores();
    boolean isSignedIn();
    void inviterJoueur();
    void showAchievements();
    int envoyerMessageFiable(byte[] message, String destinataire);
    int envoyerMessgaeNonFiable(byte[] message, String destinataire);
    String obtenirMonParticipantId();
    JoueurHumain creerJoueur(String participantID);
    JoueurHumain obtenirJoueurLocal();


}
