package com.josstoh.morpion;

import com.josstoh.morpion.metier.Plateau;
import com.josstoh.morpion.screens.EcranJeuPartieSolo;

/**
 * Created by Jocelyn on 25/07/2014.
 */
public class PartieSolo {

    public Plateau plateau;

    private boolean enCours;

    PartieSolo(final Jeu jeu)
    {


        System.out.println("création");


        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean j1 = true;
                while(plateau.checkVictoire()!= -1) {
                    if(j1)
                    {
                        System.out.println("Tour J1");
                    }

                    else
                    {
                        System.out.println("Tour J2");
                        plateau.jouerSymbole(jeu.random(0, 2), jeu.random(0, 2), jeu.random(0, 2));
                    }


                    j1 = !j1;
                }

                int resultat = plateau.checkVictoire();
                if (resultat == 0)
                {
                    System.out.println("Match Nul");
                }
                else
                {
                    if(resultat == 1)
                    {
                        System.out.println("Match J1");
                    }
                    else
                    {
                        System.out.println("Match J2");
                    }
                }

            }
        }).start();
    }


}
