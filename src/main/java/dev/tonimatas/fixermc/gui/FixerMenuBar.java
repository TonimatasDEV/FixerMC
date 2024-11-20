package dev.tonimatas.fixermc.gui;

import dev.tonimatas.fixermc.util.FixerUtils;

import javax.swing.*;

public class FixerMenuBar extends JMenuBar {
    public FixerMenuBar() {
        JMenu loginMenu = new JMenu("Login");
        JMenuItem openItem = new JMenuItem("TonimatasDEV");
        openItem.setIcon(FixerUtils.getImageIcon("myskin"));
        loginMenu.add(openItem);

        add(Box.createHorizontalGlue());
        add(loginMenu);
    }
}
