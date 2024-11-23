package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Profile;
import dev.tonimatas.fixermc.profiles.ProfileManager;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    private final JTextArea textArea;

    public Sidebar() {
        super(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        setPreferredSize(new Dimension(194, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        JButton createProfile = new JButton("Create Profile");

        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(createProfile, gbc);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setLineWrap(true);

        if (ProfileManager.selectedProfile != null) {
            textArea.setText(ProfileManager.profiles.get(ProfileManager.selectedProfile).getText());
        }

        textArea.setVisible(true);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JPanel(), gbc);

        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(textArea, gbc);
    }

    public void resetView(Profile profile) {
        textArea.setText(profile.getText());
    }
}
