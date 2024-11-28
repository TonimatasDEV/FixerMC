package dev.tonimatas.fixermc.gui.profiles;

import javax.swing.*;
import java.awt.*;

public class ProfilesTab extends JPanel {
    public static ProfileSelector profilesView = new ProfileSelector();
    public static Sidebar profileInfo = new Sidebar();

    public ProfilesTab() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(profilesView, BorderLayout.WEST);
        add(profileInfo, BorderLayout.EAST);
    }
}
