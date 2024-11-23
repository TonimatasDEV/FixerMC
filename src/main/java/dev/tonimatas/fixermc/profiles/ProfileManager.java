package dev.tonimatas.fixermc.profiles;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.FixerMC;
import dev.tonimatas.fixermc.gui.profiles.MainTab;
import dev.tonimatas.fixermc.gui.profiles.ProfileView;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ProfileManager {
    public static String selectedProfile = "";
    public static Map<String, Profile> profiles = new HashMap<>();
    public static Map<String, ProfileView> profilesViews = new HashMap<>();

    public static void setSelectedProfile(ProfileView profileView) {
        profileView.setBorder(BorderFactory.createLineBorder(Color.RED));

        Profile profile = profiles.get(profileView.profileName);
        selectedProfile = profile.name;
        MainTab.profileInfo.resetView(profile);

        for (ProfileView otherProfileView : ProfileManager.profilesViews.values()) {
            if (!otherProfileView.equals(profileView)) {
                otherProfileView.setBorder(null);
            }
        }
    }

    public static void save() {
        JsonObject profilesJson = new JsonObject();

        profilesJson.addProperty("selectedProfile", selectedProfile);

        JsonArray profilesArray = new JsonArray();
        for (Profile profile : profiles.values()) {
            profilesArray.add(FixerMC.GSON.toJsonTree(profile));
        }

        profilesJson.add("profiles", profilesArray);

        try {
            FileWriter fileWriter = new FileWriter(Constants.PROFILES_JSON.toFile());
            fileWriter.write(FixerMC.GSON.toJson(profilesJson));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving profiles.");
        }
    }

    public static void load() {
        if (!Files.exists(Constants.PROFILES_JSON)) return;
        try (FileReader reader = new FileReader(Constants.PROFILES_JSON.toFile())) {
            JsonObject jsonFile = JsonParser.parseReader(reader).getAsJsonObject();

            selectedProfile = jsonFile.get("selectedProfile") == null ? "" : jsonFile.get("selectedProfile").getAsString();

            for (JsonElement element : jsonFile.get("profiles").getAsJsonArray().asList()) {
                Profile profile = FixerMC.GSON.fromJson(element, Profile.class);

                profiles.put(profile.name, profile);
            }
        } catch (IOException e) {
            System.out.println("Error loading profiles.");
        }
    }
}
