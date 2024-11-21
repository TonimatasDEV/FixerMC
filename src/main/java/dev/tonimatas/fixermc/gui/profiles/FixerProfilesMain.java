package dev.tonimatas.fixermc.gui.profiles;

import javax.swing.*;
import java.awt.*;

public class FixerProfilesMain extends JPanel {
    public FixerProfilesMain() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(new FixerProfileSearch(), BorderLayout.WEST);
        add(new FixerProfileInfo(), BorderLayout.EAST);
    }
}
