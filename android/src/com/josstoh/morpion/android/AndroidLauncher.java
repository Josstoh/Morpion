package com.josstoh.morpion.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.josstoh.morpion.modele.IGoogleServices;
import com.josstoh.morpion.vue_controleur.Jeu;
import com.josstoh.morpion.modele.JoueurHumain;
import com.josstoh.morpion.modele.Plateau;
import com.josstoh.morpion.vue_controleur.screens.EcranJeuMulti;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices,RoomUpdateListener,RealTimeMessageReceivedListener,RoomStatusUpdateListener {

    // message
    public final static int MSG_STARTEARLY = 0;

    private GameHelper _gameHelper;
    private final static int REQUEST_CODE_UNUSED = 9002;

    private Jeu jeu;
    final String TAG = "AndroidLauncher";

    // Est-ce qu'on joue ?
    boolean mPlaying = false;

    // at least 2 players required for our game
    final static int MIN_PLAYERS = 2;

    // Activity RequestCode
    final static int RC_WAITING_ROOM = 10002;
    final static int RC_SELECT_PLAYERS = 10000;
    private static final int RC_ACHIEVEMENTS = 10001;

    String roomId;
    Room room = null;
    private String monPlayerId;
    boolean mWaitingRoomFinishedFromCode = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        log(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        // Création du GameHelper
        _gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        _gameHelper.enableDebugLog(false);

        GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInSucceeded() {
                _gameHelper.getApiClient().connect();
                log(TAG, "GameHelperListener : onSignSucceeded");
                monPlayerId = Games.Players.getCurrentPlayerId(_gameHelper.getApiClient());
                if (_gameHelper.hasInvitation()) {
                    log(TAG, "on a une invitation, on rejoint");
                    gererInvitation();
                }

            }

            @Override
            public void onSignInFailed() {
                log(TAG,"GameHelperListener : onSignFailed");
            }
        };

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        config.useWakelock = true;
        jeu = new Jeu(this, 0);
        initialize(jeu, config);
        _gameHelper.setup(gameHelperListener);
        if (_gameHelper.hasInvitation()) {
            log(TAG, "on a une invitation");
            gererInvitation();
        }

        if(_gameHelper.isSignedIn())
            monPlayerId = Games.Players.getCurrentPlayerId(_gameHelper.getApiClient());


    }

    @Override
    protected void onStart() {
        log(TAG, "call onStart()");
        super.onStart();
        _gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        log(TAG, "call onStop()");
        //Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), null, roomId);
        super.onStop();
        _gameHelper.onStop();

    }

    @Override
    protected void onPause() {
        log(TAG, "call onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        log(TAG, "call onResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        log(TAG, "call onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _gameHelper.onActivityResult(requestCode, resultCode, data);
        log(TAG, "onActivityResult");
        // Invite
        if (requestCode == RC_SELECT_PLAYERS) {
            log(TAG, "onActivityResult : RC_SELECT_PLAYERS");
            if (resultCode != Activity.RESULT_OK) {
                try {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            jeu.setScreen(jeu.accueil);
                        }
                    });
                } catch (Exception e) {
                    Gdx.app.log(TAG, "Log in failed: " + e.getMessage() + ".");
                }
                return;
            }

            // get the invitee list
            Bundle extras = data.getExtras();
            final ArrayList<String> invitees =
                    data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // get auto-match criteria
            Bundle autoMatchCriteria = null;
            int minAutoMatchPlayers =
                    data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoMatchPlayers =
                    data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

            if (minAutoMatchPlayers > 0) {
                autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                        minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            } else {
                autoMatchCriteria = null;
            }

            // create the room and specify a variant if appropriate
            RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
            roomConfigBuilder.addPlayersToInvite(invitees);
            if (autoMatchCriteria != null) {
                roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
            }
            RoomConfig roomConfig = roomConfigBuilder.build();
            Games.RealTimeMultiplayer.create(_gameHelper.getApiClient(), roomConfig);

            // prevent screen from sleeping during handshake
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            return;
        }

        if (requestCode == RC_WAITING_ROOM) {
            log(TAG, "onActivityResult : RC_WAITING_ROOM");
            if (mWaitingRoomFinishedFromCode) return;
            if (resultCode == Activity.RESULT_OK) {
                log(TAG, "onActivityResult : RC_WAITING_ROOM :RESULT_OK");
                log(TAG,"on lance le jeu multi");
                try {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {

                            jeu.setScreen(new EcranJeuMulti(jeu,obtenirJoueurs()));
                        }
                    });
                } catch (Exception e) {
                    Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Waiting room was dismissed with the back button.
                Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), this, roomId);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                try {

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {

                            jeu.setScreen(jeu.accueil);
                        }
                    });
                } catch (Exception e) {
                    Gdx.app.log(TAG, "Log in failed: " + e.getMessage() + ".");
                }
            } else if (resultCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
                // player wants to leave the room.
                Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), this, roomId);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                try {
                    // TODO: remplacer les initialize(jeu) par Gdx.app.postRunnable
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {

                            jeu.setScreen(jeu.accueil);
                        }
                    });
                } catch (Exception e) {
                    Gdx.app.log(TAG, "Log in failed: " + e.getMessage() + ".");
                }
            }
        }
        if (requestCode == RC_ACHIEVEMENTS) {
            log(TAG, "onActivityResult : RC_ACHIEVEMENTS");
            /*try
            {

                Gdx.app.postRunnable(new Runnable()
                {
                    @Override
                    public void run() {

                        jeu.setScreen(new MenuAccueil(jeu));
                    }
                });
            }
            catch (Exception e)
            {
                Gdx.app.log(TAG, "Log in failed: " + e.getMessage() + ".");
            }*/
        }

    }

    // create a RoomConfigBuilder that's appropriate for your implementation
    private RoomConfig.Builder makeBasicRoomConfigBuilder() {
        return RoomConfig.builder(this)
                .setMessageReceivedListener(this)
                .setRoomStatusUpdateListener(this);

    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                //@Override
                public void run() {
                    _gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log(TAG, "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                //@Override
                public void run() {
                    _gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log(TAG, "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame() {
        // Replace the end of the URL with the package of your game
        String str = "https://play.google.com/store/apps/details?id=com.josstoh.morpion.android";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void submitScore(long score) {
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
    public void showScores() {
        /*if (isSignedIn() == true)
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
        else
        {
// Maybe sign in here then redirect to showing scores?
        }*/
    }

    @Override
    public boolean isSignedIn() {
        return _gameHelper.isSignedIn();
    }

    public void inviterJoueur() {
        // launch the player selection screen
        // minimum: 1 other player; maximum: 3 other players
        Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(_gameHelper.getApiClient(), 1, 1);

        startActivityForResult(intent, RC_SELECT_PLAYERS);
    }

    @Override
    public void showAchievements() {
        startActivityForResult(Games.Achievements.getAchievementsIntent(_gameHelper.getApiClient()), RC_ACHIEVEMENTS);
    }

    @Override
    public int envoyerMessageFiable(byte[] message, String destinataire) {
        Gdx.app.log(TAG,"Message fiable : tentative d'envoi");
        return Games.RealTimeMultiplayer.sendReliableMessage(_gameHelper.getApiClient(), null, message, roomId, destinataire);
    }

    @Override
    public int envoyerMessgaeNonFiable(byte[] message, String destinataire) {
        Gdx.app.log(TAG,"Message on fiable : tentative d'envoi");
        return Games.RealTimeMultiplayer.sendUnreliableMessage(_gameHelper.getApiClient(), message, roomId, destinataire);
    }

    @Override
    public String obtenirMonParticipantId() {
        if(room != null)
        {
            for(Participant p : room.getParticipants())
            {
                if(monPlayerId.equals(p.getPlayer().getPlayerId()))
                {
                    return p.getParticipantId();
                }
            }
        }
        return null;
    }

    @Override
    public JoueurHumain creerJoueur(String participantID) {

        if (room != null)
        {
            Participant participant = room.getParticipant(participantID);
            Player player = participant.getPlayer();
            JoueurHumain j = new JoueurHumain();
            if(player != null)
            {
                j.setNom(player.getDisplayName());
                //j.setId(player.get);
            }
            else
            {
                j.setNom(participant.getDisplayName());

            }
            j.setId(participantID);
            return j;
        }
        else
        {
            return Jeu.googleServices.obtenirJoueurLocal();
        }

    }

    @Override
    public JoueurHumain obtenirJoueurLocal() {
        Player p = Games.Players.getCurrentPlayer(_gameHelper.getApiClient());
        JoueurHumain j = new JoueurHumain();
        j.setNom(p.getDisplayName());
        return j;
    }

    @Override
    public void onRoomCreated(int statusCode, Room room) {
        this.room = room;
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // let screen go to sleep
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            // show error message, return to main screen.
            log(TAG, "onRoomCreated() erreur : " + GamesStatusCodes.getStatusString(statusCode));
            try {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {

                        jeu.setScreen(jeu.accueil);
                    }
                });
            } catch (Exception e) {
                Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
            }
            return;
        }
        roomId = room.getRoomId();
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(_gameHelper.getApiClient(), room, Integer.MAX_VALUE);
        startActivityForResult(i, RC_WAITING_ROOM);
    }

    @Override
    public void onJoinedRoom(int statusCode, Room room) {
        this.room = room;
        log(TAG, "onJoinedRoom");
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // let screen go to sleep
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            // show error message, return to main screen.
            log("Activity", GamesStatusCodes.getStatusString(statusCode));
            try {
                // TODO: remplacer les initialize(jeu) par Gdx.app.postRunnable
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {

                        jeu.setScreen(jeu.accueil);
                    }
                });
            } catch (Exception e) {
                Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
            }
            //TODO:gérer quand on commence la partie avant que tout le monde soit co
            Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(_gameHelper.getApiClient(), room, Integer.MAX_VALUE);
            startActivityForResult(i, RC_WAITING_ROOM);
            return;
        }
        roomId = room.getRoomId();
        // get waiting room intent 2=à partir de combien on commence
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(_gameHelper.getApiClient(), room, 2);
        startActivityForResult(i, RC_WAITING_ROOM);
    }

    @Override
    public void onLeftRoom(int statusCode, String s) {
        if(statusCode == GamesStatusCodes.STATUS_OK)
        {
            room = null;
            roomId = null;
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    jeu.setScreen(jeu.accueil);
                }
            });
        }
    }

    @Override
    public void onRoomConnected(int statusCode, Room room) {
        this.room = room;
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // let screen go to sleep
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            // show error message, return to main screen.
            log(TAG, "onRoomConnected() erreur : " + GamesStatusCodes.getStatusString(statusCode));
            try {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {

                        jeu.setScreen(jeu.accueil);
                    }
                });
            } catch (Exception e) {
                Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
            }
        }
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage rtm) {
        log(TAG, "Message reçu");
        byte[] message = rtm.getMessageData();

        Plateau o = null;

        if (message[0] == AndroidLauncher.MSG_STARTEARLY) {
            mWaitingRoomFinishedFromCode = true;
            finishActivity(RC_WAITING_ROOM);
        }
        if(message[1] == 1)
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(message);
            ObjectInput in = null;
            try
            {
                in = new ObjectInputStream(bis);
                 o = (Plateau) in.readObject();
                bis.close();
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception ex)
            {
                    // ignore close exception

            }
            log(TAG,"Message reçu : " + o.toString());
        }
    }

    @Override
    public void onRoomConnecting(Room room) {
        this.room = room;

    }

    @Override
    public void onRoomAutoMatching(Room room) {
        this.room = room;

    }

    @Override
    public void onPeerInvitedToRoom(Room room, List<String> strings) {
        this.room = room;

    }

    @Override
    public void onPeerDeclined(Room room, List<String> strings) {
        this.room = room;
        // peer declined invitation -- see if game should be canceled
        if (!mPlaying && shouldCancelGame(room)) {
            Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), null, room.getRoomId());
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onPeerJoined(Room room, List<String> strings) {
        this.room = room;

    }

    @Override
    public void onPeerLeft(Room room, List<String> strings) {
        this.room = room;
        // peer left -- see if game should be canceled
        if (!mPlaying && shouldCancelGame(room)) {
            Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), null, room.getRoomId());
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onConnectedToRoom(Room room) {
        this.room = room;

    }

    @Override
    public void onDisconnectedFromRoom(Room room) {
        this.room = room;
        Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), this, room.getRoomId());
    }

    @Override
    public void onPeersConnected(Room room, List<String> strings) {
        this.room = room;
        if (mPlaying) {
            // add new player to an ongoing game
        } else if (shouldStartGame(room)) {
            // start game!
        }
    }

    @Override
    public void onPeersDisconnected(Room room, List<String> strings) {
        this.room = room;
        if (mPlaying) {
            // do game-specific handling of this -- remove player's avatar
            // from the screen, etc. If not enough players are left for
            // the game to go on, end the game and leave the room.
        } else if (shouldCancelGame(room)) {
            // cancel the game
            Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), null, room.getRoomId());
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public void onP2PConnected(String s) {

    }

    @Override
    public void onP2PDisconnected(String s) {

    }

    // returns whether there are enough players to start the game
    boolean shouldStartGame(Room room) {
        int connectedPlayers = 0;
        for (Participant p : room.getParticipants()) {
            if (p.isConnectedToRoom()) ++connectedPlayers;
        }
        return connectedPlayers >= MIN_PLAYERS;
    }

    // Returns whether the room is in a state where the game should be canceled.
    boolean shouldCancelGame(Room room) {
        // TODO: Your game-specific cancellation logic here. For example, you might decide to
        // cancel the game if enough people have declined the invitation or left the room.
        // You can check a participant's status with Participant.getStatus().
        // (Also, your UI should have a Cancel button that cancels the game too)
        return false;
    }

    void gererInvitation() {

        RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
        roomConfigBuilder.setInvitationIdToAccept(_gameHelper.getInvitationId());
        Games.RealTimeMultiplayer.join(_gameHelper.getApiClient(), roomConfigBuilder.build());

        // prevent screen from sleeping during handshake
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // go to game screen
        /*try {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {

                    jeu.setScreen(new MenuAccueil(jeu));
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }*/
    }

    ArrayList<String> obtenirJoueurs()
    {
        if(room != null) {
            ArrayList<String> list = new ArrayList<>();

            for (Participant p : room.getParticipants()) {

                Gdx.app.log(TAG,"participant " + p.getParticipantId());
                if(room.getParticipantStatus(p.getParticipantId())  == Participant.STATUS_LEFT) {
                    Gdx.app.log(TAG, p.getParticipantId() + " a quitté la salle");
                } else {
                    list.add(p.getParticipantId());
                }
            }
            Collections.sort(list);
            return list;
        }
        return null;
    }
}