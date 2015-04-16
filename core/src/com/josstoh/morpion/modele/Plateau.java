package com.josstoh.morpion.modele;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Jocelyn on 14/07/2014.
 */
public class Plateau extends Observable implements Serializable {
    public int[][] grille;

    public Plateau() {
        grille = new int[3][3];
        System.out.println("grille au début" + grille[0][0]);
    }

    public void jouerCoup(Coup coup) {
        grille[coup.x][coup.y] = coup.symb;
        setChanged();
        notifyObservers(checkVictoire());
    }

    public boolean coupPossible(Coup coup)
    {
        return grille[coup.x][coup.y] == 0;
    }

    /*
    -1 : personne n'a encore gagné
    0 : nul
    1 : Croix WIN
    2 : Rond WIN
     */
    public int checkVictoire() {

        // Variables
        int win;
        int it = 0;
        int it2 = 0;
        boolean rempli = true;


        // Test si quelqu'un a gagné
        while (it < 3){
            int symbole = grille[it][0];
            if(symbole != 0)
            {
                if(symbole == grille[it][1] && symbole == grille[it][2])
                {
                    win = symbole;
                    return win;
                }
            }
            it++;
        }
        it = 0;
        // Test lignes verticales
        while (it < 3){
            int symbole = grille[0][it];
            if(symbole != 0)
            {
                if(symbole == grille[1][it] && symbole == grille[2][it])
                {
                    win = symbole;
                    return win;
                }
            }
            it++;
        }
        int symbole = grille[0][0];
        if(symbole != 0 && symbole == grille[1][1] && symbole == grille[2][2])
        {
            win = symbole;
            return win;
        }

        symbole = grille[0][2];
        if(symbole != 0 && symbole == grille[1][1] && symbole == grille[2][0])
        {
            win = symbole;
            return win;
        }
        // Test si résultat rempli
        it = 0;
        while (it < 3)
        {
            while (it2 < 3) {
                if (grille[it][it2] == 0)
                    rempli = false;
                it2++;
            }
            it++;
            it2 = 0;
        }

        // Résultat rempli
        if(rempli) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        String s = "";
        for(int[] i : grille)
        {
            for(int j : i)
            {
                s+=j;
            }
            s+="\n";
        }
        return s;
    }
}
