package dev.tonimatas.fixermc.profiles;

import dev.tonimatas.fixermc.gui.profiles.MainTab;
import dev.tonimatas.fixermc.gui.profiles.ProfileView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ProfileManager {
    public static Profile selectedProfile = null;
    public static Map<String, Profile> profiles = new HashMap<>();
    public static Map<String, ProfileView> profilesViews = new HashMap<>();
    
    public static void setSelectedProfile(ProfileView profileView) {
        profileView.setBorder(BorderFactory.createLineBorder(Color.RED));

        Profile profile = profiles.get(profileView.profileName);
        selectedProfile = profile;
        MainTab.profileInfo.resetView(profile);
        
        for (ProfileView otherProfileView : ProfileManager.profilesViews.values()) {
            if (!otherProfileView.equals(profileView)) {
                otherProfileView.setBorder(null);
            }
        }
    }
}
