package dev.tonimatas.fixermc.gui.profiles;

import javax.swing.*;
import java.awt.*;

public class FixerProfileInfo extends JPanel {
    public FixerProfileInfo() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        setPreferredSize(new Dimension(194, 0));
        add(new JLabel("Profile Info"), BorderLayout.WEST);
    }
}
