package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Profile;
import dev.tonimatas.fixermc.profiles.ProfileManager;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    private JTextArea textArea;

    public Sidebar() {
        super(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        setPreferredSize(new Dimension(194, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        if (ProfileManager.selectedProfile != null) {
            textArea.setText(ProfileManager.selectedProfile.getText());
        }
        textArea.setVisible(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        add(textArea, gbc);
    }
    
    public void resetView(Profile profile) {
        textArea.setText(profile.getText());
    }
}
