package dev.tonimatas.fixermc.sessions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.FixerMC;
import net.lenni0451.commons.httpclient.HttpClient;
import net.raphimc.minecraftauth.MinecraftAuth;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    public static String selectedAccount = "";
    public static Map<String, Account> accounts = new HashMap<>();

    public static void loadAccounts() {
        if (!Files.exists(Constants.ACCOUNTS_JSON)) return;
        try (FileReader reader = new FileReader(Constants.ACCOUNTS_JSON.toFile())) {
            JsonObject jsonFile = JsonParser.parseReader(reader).getAsJsonObject();

            selectedAccount = jsonFile.get("selectedAccount").getAsString();

            for (JsonElement element : jsonFile.get("accounts").getAsJsonArray().asList()) {
                JsonObject accountJson = element.getAsJsonObject();
                
                boolean online = accountJson.get("online").getAsBoolean();
                
                if (online) {
                    StepFullJavaSession.FullJavaSession session = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.fromJson(accountJson.get("session").getAsJsonObject());
                    accounts.put(session.getMcProfile().getName(), new OnlineAccount(session));
                } else {
                    String name = accountJson.get("name").getAsString();
                    accounts.put(name, new OfflineAccount(name));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts.");
        }
    }

    public static void saveAccounts() {
        JsonObject jsonFile = new JsonObject();
        jsonFile.addProperty("selectedAccount", selectedAccount);

        JsonArray accountsJson = new JsonArray();
        for (Account account : accounts.values()) {
            JsonObject accountJson = new JsonObject();

            if (account.isOnline()) {
                JsonObject sessionJson = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.toJson(((OnlineAccount) account).session());

                accountJson.addProperty("online", true);
                accountJson.add("session", sessionJson);
            } else {
                accountJson.addProperty("online", false);
                accountJson.addProperty("name", ((OfflineAccount) account).name());
            }
            
            accountsJson.add(accountJson);
        }


        jsonFile.add("accounts", accountsJson);

        try {
            FileWriter fileWriter = new FileWriter(Constants.ACCOUNTS_JSON.toFile());
            fileWriter.write(FixerMC.GSON.toJson(jsonFile));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts.");
        }
    }
    
    public static StepFullJavaSession.FullJavaSession addAccount() {
        HttpClient httpClient = MinecraftAuth.createHttpClient();
        StepFullJavaSession.FullJavaSession javaSession;
        try {
            javaSession = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.getFromInput(httpClient, new StepMsaDeviceCode.MsaDeviceCodeCallback(msaDeviceCode -> {
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(new URI(msaDeviceCode.getDirectVerificationUri()));
                    } else {
                        System.out.println("Desktop not supported.");
                    }
                } catch (Exception e) {
                    System.out.println("Error opening direct verification URI.");
                }
            }));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("New account added: " + javaSession.getMcProfile().getName());
        
        return javaSession;
    }
}
