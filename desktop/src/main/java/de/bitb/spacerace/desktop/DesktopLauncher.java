package de.bitb.spacerace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.bitb.spacerace.core.MainGame;

public class
DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        //If I want to test windowed
        boolean fullscreen = false;
        if (!fullscreen) {
            config.width *= 0.9f;
            config.height *= 0.9f;
        }
        config.fullscreen = fullscreen;
        config.resizable = true;
        config.samples = 4;
        config.vSyncEnabled = true;

        new LwjglApplication(new MainGame("DESKTOP", null), config);
    }
}
