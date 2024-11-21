package dev.tonimatas.fixermc;

import fr.theshark34.openlauncherlib.minecraft.GameFolder;

import java.nio.file.Path;

public class Main {
    

    public static void main(String[] args) {
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
    
    public static GameFolder getProfileGameFolder(String profileName) {
        Path profileFolder = Constants.PROFILES_FOLDER.resolve(profileName);
        return new GameFolder(profileFolder.resolve("assets").toString(),
                profileFolder.resolve("libraries").toString(),
                profileFolder.resolve("natives").toString(),
                profileFolder.resolve("client.jar").toString());
    }
}
