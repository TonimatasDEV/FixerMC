package dev.tonimatas.fixermc.gui.profiles;

import javax.swing.*;
import java.awt.*;

public class FixerProfile extends JPanel {
    public FixerProfile() {
        setLayout(new BorderLayout());
        JButton playKill = new JButton("Play");
        add(playKill, BorderLayout.SOUTH);

        JTextArea profileName = new JTextArea("ATM To The Sky - 9");
        profileName.setEditable(false);
        profileName.setFocusable(false);
        profileName.setBackground(null);
        profileName.setWrapStyleWord(true);
        profileName.setLineWrap(true);
        add(profileName, BorderLayout.NORTH);
    }
}
