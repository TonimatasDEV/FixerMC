package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Profile;
import dev.tonimatas.fixermc.profiles.ProfileManager;

import javax.swing.*;
import java.awt.*;

public class ProfileSelector extends JScrollPane {
    private final static int squareSize = 160;
    private final static int squareSpacing = 15;
    private final static int columns = 4;

    public ProfileSelector() {
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        setPreferredSize(new Dimension(750, 0));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUnitIncrement(7);
        resetView();
    }

    public void resetView() {
        for (ProfileView view : ProfileManager.profilesViews.values()) {
            if (ProfileManager.profiles.get(view.profileName) == null) {
                remove(view);
            }
        }

        int profilesCount = ProfileManager.profiles.size();
        int rows = (int) (Math.ceil((double) profilesCount / columns));

        if (rows < 3) {
            rows = 3;
        }

        int contentHeight = rows * (squareSize + squareSpacing) + 6;

        JPanel contentPanel = new JPanel(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        contentPanel.setLayout(new GridLayout(rows, columns, 15, 15));
        contentPanel.setPreferredSize(new Dimension(500, contentHeight));
        
        
        for (Profile profile : ProfileManager.profiles.values()) {
            if (ProfileManager.profilesViews.get(profile.name) != null) continue;

            ProfileView profileView = new ProfileView(profile.name);
            ProfileManager.profilesViews.put(profile.name, profileView);
        }

        for (ProfileView view : ProfileManager.profilesViews.values()) {
            contentPanel.add(view);
        }

        int voidSquares = rows * columns - profilesCount;
        for (int i = 0; i < voidSquares; i++) {
            JPanel profile = new JPanel();
            contentPanel.add(profile);
        }

        setViewportView(contentPanel);
    }
}
