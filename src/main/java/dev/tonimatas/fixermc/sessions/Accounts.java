package dev.tonimatas.fixermc.sessions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.tonimatas.fixermc.Constants;
import net.raphimc.minecraftauth.MinecraftAuth;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Accounts {
    public static String selectedAccount = "";
    public static Map<String, Account> accounts = new HashMap<>();

    public static void loadAccounts() {
        // TODO: Logic
    }

    public static void saveAccounts() {
        JsonObject jsonFile = new JsonObject();
        jsonFile.addProperty("selectedAccount", selectedAccount);

        JsonArray accounts = new JsonArray();
        for (Account account : Accounts.accounts.values()) {
            JsonObject accountJson = new JsonObject();

            if (account.isOnline()) {
                JsonObject sessionJson = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.toJson(((OnlineAccount) account).session());

                accountJson.addProperty("online", true);
                accountJson.add("session", sessionJson);
            } else {
                accountJson.addProperty("online", false);
                accountJson.addProperty("name", ((OfflineAccount) account).name());
            }
            
            accounts.add(accountJson);
        }


        jsonFile.add("accounts", accounts);

        try {
            FileWriter fileWriter = new FileWriter(Constants.ACCOUNTS_JSON.toFile());
            fileWriter.write(Constants.GSON.toJson(jsonFile));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts.");
        }
    }
}
