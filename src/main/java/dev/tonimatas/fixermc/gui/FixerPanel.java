package dev.tonimatas.fixermc.gui;

import dev.tonimatas.fixermc.gui.profiles.MainTab;

import javax.swing.*;

public class FixerPanel extends JTabbedPane {
    public FixerPanel() {
        addTab("Profiles", new MainTab());
        addTab("Complements", new JPanel());
        addTab("Options", new JPanel());
        addTab("Browse", new JPanel());
    }
}
