package tic;

import engine.support.FXApplication;
import engine.support.FXFrontEnd;

import java.io.FileNotFoundException;

/**
 * Here is your main class. You should not have to edit this
 * class at all unless you want to change your window size
 * or turn off the debugging information.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FXFrontEnd app = new App("2D Game Engines - J. Alex Mina");
        FXApplication application = new FXApplication();
        application.begin(app);
    }
}
