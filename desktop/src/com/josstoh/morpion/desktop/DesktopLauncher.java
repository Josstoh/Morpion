package com.josstoh.morpion.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.josstoh.morpion.modele.DesktopGoogleServices;
import com.josstoh.morpion.vue_controleur.Jeu;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 800;
        config.width = 480;
		new LwjglApplication(new Jeu(new DesktopGoogleServices()), config);
	}
}
