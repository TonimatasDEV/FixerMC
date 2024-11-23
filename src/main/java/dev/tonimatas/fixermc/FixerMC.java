package dev.tonimatas.fixermc;

import com.formdev.flatlaf.FlatDarkLaf;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.tonimatas.fixermc.gui.FixerMenuBar;
import dev.tonimatas.fixermc.gui.FixerPanel;
import dev.tonimatas.fixermc.sessions.AccountManager;

import javax.swing.*;
import java.awt.*;

public class FixerMC extends JFrame {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void launch() {
        AccountManager.loadAccounts();

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        
        new FixerMC();
    }
    
    public FixerMC() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        setJMenuBar(new FixerMenuBar());
        setContentPane(new FixerPanel());
        setVisible(true);
    }
}
