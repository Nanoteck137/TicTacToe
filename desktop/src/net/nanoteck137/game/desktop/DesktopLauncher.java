package net.nanoteck137.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.nanoteck137.game.Program;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 800;
		config.title = "Hello World";
		config.resizable = false;
		config.samples = 4;
		new LwjglApplication(new Program(), config);
	}
}
