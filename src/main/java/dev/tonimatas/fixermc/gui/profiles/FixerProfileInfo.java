package dev.tonimatas.fixermc.gui.profiles;

import javax.swing.*;
import java.awt.*;

public class FixerProfileInfo extends JPanel {
    public static JTextArea textArea;

    public FixerProfileInfo() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        setPreferredSize(new Dimension(194, 0));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        add(textArea, BorderLayout.CENTER);
    }
}
