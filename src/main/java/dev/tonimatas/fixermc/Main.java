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
        
        /*
        FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(new VanillaVersion.VanillaVersionBuilder().withName("1.21.3").build())
                .build();
        
        try {
            updater.update(Constants.PROFILES_FOLDER.resolve("1.21.3"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            NoFramework noFramework = new NoFramework(Constants.PROFILES_FOLDER.resolve("1.21.3"), AccountManager.accounts.get(AccountManager.selectedAccount).getAuthInfos(), getProfileGameFolder("1.21.3"));

            noFramework.launch("1.21.3", "", NoFramework.ModLoader.VANILLA);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    /*
    public static GameFolder getProfileGameFolder(String profileName) {
        Path profileFolder = Constants.PROFILES_FOLDER.resolve(profileName);
        return new GameFolder(profileFolder.resolve("assets").toString(),
                profileFolder.resolve("libraries").toString(),
                profileFolder.resolve("natives").toString(),
                profileFolder.resolve("client.jar").toString());
    }*/
}
