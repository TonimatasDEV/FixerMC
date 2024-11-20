package dev.tonimatas.fixermc;

import com.formdev.flatlaf.FlatDarkLaf;
import dev.tonimatas.fixermc.gui.FixerMenuBar;
import dev.tonimatas.fixermc.gui.FixerPanel;

import javax.swing.*;
import java.awt.*;

public class FixerMC extends JFrame {
    public static void launch() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        
        JFrame frame = new FixerMC();
        frame.add(new FixerPanel(), BorderLayout.CENTER);
    }
    
    public FixerMC() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        setJMenuBar(new FixerMenuBar());
        setVisible(true);
    }
}
