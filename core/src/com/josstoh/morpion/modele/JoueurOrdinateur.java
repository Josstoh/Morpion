package com.josstoh.morpion.modele;

import com.josstoh.morpion.vue_controleur.Jeu;

/**
 * Created by Jocelyn on 14/04/2015.
 */
public class JoueurOrdinateur extends Joueur{

    public JoueurOrdinateur() {
        nom = "Ordinateur";
    }

    @Override
    public Coup obtenirCoup()
    {
       return new Coup(Jeu.random(0, 2),Jeu.random(0, 2),2);
    }
}
