package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Profile;
import dev.tonimatas.fixermc.profiles.ProfileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProfileView extends JPanel {
    public final String profileName;
    public final JButton playKill;
    public boolean isRunning = false;
    private Process process;

    public ProfileView(String profileName) {
        super(new GridBagLayout());
        this.profileName = profileName;

        setBackground(Color.BLACK);
        setFocusable(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JTextArea profileNameArea = new JTextArea(this.profileName);
        profileNameArea.setEditable(false);
        profileNameArea.setFocusable(false);
        profileNameArea.setForeground(Color.WHITE);
        profileNameArea.setBackground(Color.BLACK);
        profileNameArea.setWrapStyleWord(true);
        profileNameArea.setLineWrap(true);
        add(profileNameArea, gbc);

        if (ProfileManager.profiles.get(profileName).downloaded) {
            playKill = new JButton("Play");
            playKill.setVisible(false);
            playKill.setBackground(Color.GREEN.darker().darker());
        } else {
            new Thread(() -> ProfileManager.profiles.get(profileName).update()).start();
            playKill = new JButton("Downloading...");
            playKill.setVisible(true);
            playKill.setBackground(Color.BLUE.brighter());
        }

        playKill.setFont(playKill.getFont().deriveFont(Font.BOLD).deriveFont(16f));
        gbc.gridy = 1;
        gbc.weighty = 0.08;
        add(playKill, gbc);

        profileNameArea.addMouseListener(mouseActionListener(this));

        playKill.addActionListener(a -> {
            if (playKill.getText().equals("Downloading...")) return;

            if (playKill.getText().equals("Kill") && process != null) {
                process.destroy();
                isRunning = false;
                playKill.setText("Play");
                playKill.setBackground(Color.GREEN.darker().darker());
                return;
            }

            launch();
        });

        playKill.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (ProfileView.this.playKill.getText().equals("Play")) {
                    playKill.setVisible(false);
                }
            }
        });

        addMouseListener(mouseActionListener(this));
    }
    
    public void launch() {
        Profile updatedProfile = ProfileManager.profiles.get(profileName);

        playKill.setText("Loading...");
        playKill.setBackground(Color.ORANGE.darker());

        new Thread(() -> {
            process = updatedProfile.launch();
            if (!isRunning && process != null) {
                playKill.setText("Kill");
                playKill.setBackground(Color.RED.darker());
                isRunning = true;

                try {
                    process.waitFor();
                    playKill.setText("Play");
                    playKill.setBackground(Color.GREEN.darker().darker());
                    playKill.setVisible(false);
                    isRunning = false;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private static MouseListener mouseActionListener(ProfileView profileView) {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ProfileManager.setSelectedProfile(profileView);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                profileView.playKill.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!profileView.playKill.getBounds().contains(e.getPoint()) && profileView.playKill.getText().equals("Play")) {
                    profileView.playKill.setVisible(false);
                }
            }
        };
    }
}
