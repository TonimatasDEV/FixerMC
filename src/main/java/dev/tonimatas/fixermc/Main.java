package dev.tonimatas.fixermc;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.theshark34.openlauncherlib.minecraft.*;
import net.lenni0451.commons.httpclient.HttpClient;
import net.raphimc.minecraftauth.MinecraftAuth;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

import java.nio.file.Path;

public class Main {
    

    public static void main(String[] args) {
        FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(new VanillaVersion.VanillaVersionBuilder().withName("1.21.3").build())
                .build();
        
        try {
            updater.update(Constants.PROFILES_FOLDER.resolve("1.21.3"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        HttpClient httpClient = MinecraftAuth.createHttpClient();
        StepFullJavaSession.FullJavaSession javaSession;
        try {
            javaSession = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.getFromInput(httpClient, new StepMsaDeviceCode.MsaDeviceCodeCallback(msaDeviceCode -> {
                // Method to generate a verification URL and a code for the user to enter on that page
                System.out.println("Go to " + msaDeviceCode.getVerificationUri());
                System.out.println("Enter code " + msaDeviceCode.getUserCode());
    
                // There is also a method to generate a direct URL without needing the user to enter a code
                System.out.println("Go to " + msaDeviceCode.getDirectVerificationUri());
            }));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        System.out.println("Username: " + javaSession.getMcProfile().getName());
        
        AuthInfos authInfos = new AuthInfos(javaSession.getMcProfile().getName(), javaSession.getMcProfile().getMcToken().getAccessToken(), javaSession.getMcProfile().getId().toString());

        try {
            NoFramework noFramework = new NoFramework(Constants.PROFILES_FOLDER.resolve("1.21.3"), authInfos, getProfileGameFolder("1.21.3"));

            noFramework.launch("1.21.3", "", NoFramework.ModLoader.VANILLA);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static GameFolder getProfileGameFolder(String profileName) {
        Path profileFolder = Constants.PROFILES_FOLDER.resolve(profileName);
        return new GameFolder(profileFolder.resolve("assets").toString(),
                profileFolder.resolve("libraries").toString(),
                profileFolder.resolve("natives").toString(),
                profileFolder.resolve("client.jar").toString());
    }
}
