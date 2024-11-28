package dev.tonimatas.fixermc.profiles;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.tonimatas.fixermc.Constants;
import dev.tonimatas.fixermc.FixerMC;
import dev.tonimatas.fixermc.gui.profiles.ProfileView;
import dev.tonimatas.fixermc.gui.profiles.ProfilesTab;

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
        ProfilesTab.profileInfo.resetView(profile);
        ProfilesTab.profileInfo.deleteButton.setVisible(true);
        ProfilesTab.profileInfo.textArea.setVisible(true);

        for (ProfileView otherProfileView : ProfileManager.profilesViews.values()) {
            if (!otherProfileView.equals(profileView)) {
                otherProfileView.setBorder(null);
            }
        }
    }

    public static void removeProfile(String profile) {
        profiles.get(profile).delete();

        profilesViews.remove(profile);
        profiles.remove(profile);

        ProfilesTab.profileInfo.textArea.setVisible(false);
        ProfilesTab.profileInfo.deleteButton.setVisible(false);
        ProfilesTab.profilesView.resetView();
        ProfileManager.save();
    }

    private static void addProfile(Profile profile) {
        profiles.put(profile.name, profile);
        profilesViews.put(profile.name, new ProfileView(profile.name));

        if (!profile.downloaded) profile.update();
    }

    public static void createProfile(String name, String version, Loader loader, String loaderVersion) {
        Profile profile = new Profile(name, version, loader, loaderVersion);

        profiles.put(profile.name, profile);
        profilesViews.put(profile.name, new ProfileView(profile.name));
        ProfilesTab.profilesView.resetView();
        ProfileManager.save();

        profile.update();
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
                addProfile(FixerMC.GSON.fromJson(element, Profile.class));
            }
        } catch (IOException e) {
            System.out.println("Error loading profiles.");
        }
    }
}
