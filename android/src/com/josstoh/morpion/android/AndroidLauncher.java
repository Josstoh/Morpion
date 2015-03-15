package com.josstoh.morpion.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.josstoh.morpion.IGoogleServices;
import com.josstoh.morpion.Jeu;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices{
    private GameHelper _gameHelper;
    private final static int REQUEST_CODE_UNUSED = 9002;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        System.out.println("call onCreate()");
        super.onCreate(savedInstanceState);
        //System.out.println("bundle vide ?" + savedInstanceState.isEmpty());

        // Create the GameHelper.
        _gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        _gameHelper.enableDebugLog(false);

        GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
        {
            @Override
            public void onSignInSucceeded()
            {
            }

            @Override
            public void onSignInFailed()
            {
            }
        };

        /* ---------------------A retirer ---------------*/
        _gameHelper.setMaxAutoSignInAttempts(0);
        _gameHelper.setup(gameHelperListener);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        config.useWakelock = true;
        initialize(new Jeu(this), config);

    }
    @Override
    protected void onStart()
    {
        System.out.println("call onStart()");
        super.onStart();
        _gameHelper.onStart(this);
    }

    @Override
    protected void onStop()
    {
        System.out.println("call onStop()");
        super.onStop();
        _gameHelper.onStop();
    }

    @Override
    protected void onPause() {
        System.out.println("call onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        System.out.println("call onResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        System.out.println("call onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        _gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn()
    {
        try
        {
            runOnUiThread(new Runnable()
            {
                //@Override
                public void run()
                {
                    _gameHelper.beginUserInitiatedSignIn();
                }
            });
        }
        catch (Exception e)
        {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut()
    {
        try
        {
            runOnUiThread(new Runnable()
            {
                //@Override
                public void run()
                {
                    _gameHelper.signOut();
                }
            });
        }
        catch (Exception e)
        {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame()
    {
// Replace the end of the URL with the package of your game
        String str ="https://play.google.com/store/apps/details?id=org.fortheloss.plunderperil";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void submitScore(long score)
    {
        /*if (isSignedIn() == true)
        {
            Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
        }
        else
        {
// Maybe sign in here then redirect to submitting score?
        }*/
    }

    @Override
    public void showScores()
    {
        /*if (isSignedIn() == true)
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
        else
        {
// Maybe sign in here then redirect to showing scores?
        }*/
    }

    @Override
    public boolean isSignedIn()
    {
        return _gameHelper.isSignedIn();
    }

}
