package com.josstoh.morpion.modele;

/**
 * Created by Jocelyn on 07/04/2015.
 */
public abstract class  Joueur {

    protected String nom;
    protected String id;
    protected int statut;
    protected Coup coup;

    public abstract Coup obtenirCoup();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coup getCoup() {
        return coup;
    }

    public void setCoup(Coup coup) {
        this.coup = coup;
    }
}
