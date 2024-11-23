package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Loader;
import dev.tonimatas.fixermc.profiles.Profile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FixerProfileSearch extends JScrollPane {
    private static final List<FixerProfile> profiles = new ArrayList<>();
    private final static int squareSize = 160;
    private final static int squareSpacing = 15;
    private final static int columns = 4;
    
    public FixerProfileSearch() {
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        setPreferredSize(new Dimension(750, 0));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUnitIncrement(7);
        
        int totalSquares = 15 + 1;
        int rows = (int) (Math.ceil((double) totalSquares / columns));
        
        if (rows < 5) {
            rows = 5;
        }
        
        int contentHeight = rows * (squareSize + squareSpacing) + 6;
        
        JPanel contentPanel  = new JPanel(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        contentPanel.setLayout(new GridLayout(rows, columns, 15, 15));
        contentPanel.setPreferredSize(new Dimension(500, contentHeight));

        int voidSquares = rows * columns - totalSquares;
        
        for (int i = 0; i < totalSquares - 1; i++) {
            FixerProfile profile = new FixerProfile(new Profile("ATM", "1.19.2", Loader.FORGE, "45.10.23", "An ATM modpack"));
            profiles.add(profile);
            contentPanel.add(profile);
        }

        JButton createProfile = new JButton("+");
        createProfile.setFont(createProfile.getFont().deriveFont(20f));
        contentPanel.add(createProfile);

        for (int i = 0; i < voidSquares; i++) {
            JPanel profile = new JPanel();
            contentPanel.add(profile);
        }

        setViewportView(contentPanel);
    }
    
    public static void setSelectedProfile(FixerProfile profile) {
        profile.setBorder(BorderFactory.createLineBorder(Color.RED));

        FixerProfileInfo.textArea.removeAll();
        FixerProfileInfo.textArea.setText(profile.profile.getText());
        
        for (FixerProfile profile1 : profiles) {
            if (!profile1.equals(profile)) {
                profile1.setBorder(null);
            }
        }
    }
}
