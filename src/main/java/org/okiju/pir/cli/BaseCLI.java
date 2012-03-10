package org.okiju.pir.cli;

public class BaseCLI {

    public static void checkArgs(String nameApp, String[] args) {
        if (args.length < 1) {
            System.out.println("Use: " + nameApp + " configPath");
            System.exit(1);
        }
    }
}
