package dev.tonimatas.fixermc;

import dev.tonimatas.fixermc.libraries.LibraryInstaller;

public class Main {
    public static boolean developerMode = false;

    public static void main(String[] args) {
        if (args.length >= 1 && args[0].equals("--developer")) {
            developerMode = true;
        } else {
            LibraryInstaller.init();
        }

        FixerMC.launch();
    }
}
