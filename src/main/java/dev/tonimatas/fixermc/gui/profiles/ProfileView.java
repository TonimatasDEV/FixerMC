package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Profile;
import dev.tonimatas.fixermc.profiles.ProfileManager;
import dev.tonimatas.fixermc.profiles.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProfileView extends JPanel {
    public final String profileName;
    public final JButton actionButton;
    public State state = State.NORMAL;
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

        actionButton = new JButton();
        changeState(State.NORMAL);

        actionButton.setFont(actionButton.getFont().deriveFont(Font.BOLD).deriveFont(16f));
        gbc.gridy = 1;
        gbc.weighty = 0.08;
        add(actionButton, gbc);


        actionButton.addActionListener(a -> {
            if (state == State.NORMAL) {
                launch();
                return;
            }

            if (state == State.RUNNING && process != null) {
                process.destroy();
                changeState(State.NORMAL);
            }
        });

        actionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (ProfileView.this.state == State.NORMAL) {
                    actionButton.setVisible(false);
                }
            }
        });

        profileNameArea.addMouseListener(mouseActionListener(this));
        addMouseListener(mouseActionListener(this));
    }

    private static MouseListener mouseActionListener(ProfileView profileView) {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ProfileManager.setSelectedProfile(profileView);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                profileView.actionButton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!profileView.actionButton.getBounds().contains(e.getPoint()) && profileView.state == State.NORMAL) {
                    profileView.actionButton.setVisible(false);
                }
            }
        };
    }

    public void changeState(State state) {
        actionButton.setText(state.title);
        actionButton.setBackground(state.color);
        this.state = state;

        if (state.alwaysVisible) actionButton.setVisible(true);
        
        if (state == State.NORMAL) {
            Point mousePos = getMousePosition();

            if (mousePos != null) {
                actionButton.setVisible(actionButton.getBounds().contains(mousePos));
            }

            actionButton.setVisible(false);
        }
    }

    public void launch() {
        changeState(State.LOADING);

        new Thread(() -> {
            process = getProfile().launch();
            if (state != State.RUNNING && process != null) {
                changeState(State.RUNNING);

                try {
                    process.waitFor();
                    changeState(State.NORMAL);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private Profile getProfile() {
        return ProfileManager.profiles.get(profileName);
    }
}
