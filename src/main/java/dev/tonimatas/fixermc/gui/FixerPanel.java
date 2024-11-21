package dev.tonimatas.fixermc.gui;

import dev.tonimatas.fixermc.gui.profiles.FixerProfilesMain;

import javax.swing.*;

public class FixerPanel extends JTabbedPane {
    public FixerPanel() {
        addTab("Profiles", new FixerProfilesMain());
        addTab("Complements", new JPanel());
        addTab("Options", new JPanel());
        addTab("Browse", new JPanel());
    }
}
