package com.gorga.frontend.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gorga.frontend.Main;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("GorgaSimatupang_OOP_FE");
        config.setWindowedMode(1280, 720);
        config.useVsync(true);
        new Lwjgl3Application(new Main(), config);
    }
}
