package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.ProfileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileView extends JPanel {
    public final String profileName;
    private final JButton playKill;
    
    public ProfileView(String profile) {
        super(new BorderLayout());
        this.profileName = profile;
        
        setBackground(Color.BLACK);
        setEnabled(true);
        setOpaque(true);
        setFocusable(true);
        
        playKill = new JButton("Play");
        playKill.setVisible(false);
        playKill.setBackground(Color.GREEN.darker().darker());
        add(playKill, BorderLayout.SOUTH);

        playKill.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                playKill.setVisible(false);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ProfileManager.setSelectedProfile(ProfileView.this);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ProfileView.this.playKill.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!playKill.getBounds().contains(e.getPoint())) {
                    ProfileView.this.playKill.setVisible(false);
                }
            }
        });

        JTextArea profileName = new JTextArea(this.profileName);
        profileName.setEditable(false);
        profileName.setFocusable(false);
        profileName.setBackground(null);
        profileName.setWrapStyleWord(true);
        profileName.setLineWrap(true);
        add(profileName, BorderLayout.NORTH);
    }
}
