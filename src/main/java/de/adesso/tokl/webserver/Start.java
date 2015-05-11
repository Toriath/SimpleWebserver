package de.adesso.tokl.webserver;

/**
 * Created by kloss on 05.05.2015.
 */
final class Start {

    /**
     * private Constructor that does nothing to statisfy checkstyle. >.>
     */
    private Start() {
    }

    /**
     * Main. Starts the program
     * @param args argument list as given by the cmd
     */
    public static void main(String[] args) {
        new ServerLauncher().launch(args);
    }

}
