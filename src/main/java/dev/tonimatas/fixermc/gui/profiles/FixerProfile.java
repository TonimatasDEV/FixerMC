package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FixerProfile extends JPanel {
    public final Profile profile;
    private final JButton playKill;
    
    public FixerProfile(Profile profile) {
        super(new BorderLayout());
        this.profile = profile;
        
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
                FixerProfileSearch.setSelectedProfile(FixerProfile.this);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                FixerProfile.this.playKill.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!playKill.getBounds().contains(e.getPoint())) {
                    FixerProfile.this.playKill.setVisible(false);
                }
            }
        });
        
        

        JTextArea profileName = new JTextArea("ATM To The Sky - 9");
        profileName.setEditable(false);
        profileName.setFocusable(false);
        profileName.setBackground(null);
        profileName.setWrapStyleWord(true);
        profileName.setLineWrap(true);
        add(profileName, BorderLayout.NORTH);
    }
}
