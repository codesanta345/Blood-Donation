package application;

/**
 * Start the app from here (Run As > Java Application in Eclipse, or the green
 * Run button in IntelliJ / VS Code).
 *
 * This class deliberately does not extend javafx.application.Application, which
 * avoids the "JavaFX runtime components are missing" error when JavaFX is on
 * the classpath.
 */
public class Launcher {
    public static void main(String[] args) {
        Login.Main.main(args);
    }
}
