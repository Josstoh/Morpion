package com.josstoh.morpion;

/**
 * Created by Jocelyn on 04/03/2015.
 */
public interface IGoogleServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void submitScore(long score);
    public void showScores();
    public boolean isSignedIn();
    public void inviterJoueur();
}
