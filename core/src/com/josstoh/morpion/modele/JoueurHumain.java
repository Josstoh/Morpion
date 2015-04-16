package com.josstoh.morpion.modele;

/**
 * Created by Jocelyn on 14/04/2015.
 */
public class JoueurHumain extends Joueur{

    public JoueurHumain() {
    }

    @Override
    public Coup obtenirCoup() {
        return coup;
    }
}
