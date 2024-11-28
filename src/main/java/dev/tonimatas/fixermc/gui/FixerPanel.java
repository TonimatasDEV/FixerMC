package dev.tonimatas.fixermc.gui;

import dev.tonimatas.fixermc.gui.profiles.ProfilesTab;

import javax.swing.*;

public class FixerPanel extends JTabbedPane {
    public FixerPanel() {
        addTab("Profiles", new ProfilesTab());
        addTab("Complements", new JPanel());
        addTab("Options", new JPanel());
        addTab("Browse", new JPanel());
    }
}
