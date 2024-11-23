package dev.tonimatas.fixermc.gui.profiles;

import dev.tonimatas.fixermc.profiles.Loader;
import dev.tonimatas.fixermc.profiles.Profile;
import dev.tonimatas.fixermc.profiles.ProfileManager;
import dev.tonimatas.fixermc.profiles.downloader.FixerDownloader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel {
    private final JTextArea textArea;

    public Sidebar() {
        super(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        setPreferredSize(new Dimension(194, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        JButton createProfile = new JButton("Create Profile");

        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(createProfile, gbc);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        if (!ProfileManager.selectedProfile.isBlank()) {
            textArea.setText(ProfileManager.profiles.get(ProfileManager.selectedProfile).getText());
        }

        textArea.setVisible(true);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JPanel(), gbc);

        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(textArea, gbc);

        createProfile.addActionListener(createProfileAction());
    }

    private static ActionListener createProfileAction() {
        return a -> {
            JPanel options = new JPanel();
            options.setLayout(new GridLayout(3, 2, 5, 5));
            options.setVisible(true);

            JTextField name = new JTextField();
            options.add(new JLabel("Name: "));
            options.add(name);


            JComboBox<Loader> modLoader = new JComboBox<>(new Loader[]{Loader.VANILLA});
            options.add(new JLabel("Loader: "));
            options.add(modLoader);

            JComboBox<String> minecraftVersion = new JComboBox<>(FixerDownloader.getMinecraftVersions().toArray(new String[0]));
            minecraftVersion.setEditable(false);
            options.add(new JLabel("MC Version: "));
            options.add(minecraftVersion);

            int option = JOptionPane.showOptionDialog(null, options, "Create Profile", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Cancel", "Ok"}, "Ok");

            if (option == 1) {
                if (name.getText().isEmpty()) {
                    JOptionPane.showConfirmDialog(null, "You can't create a profile without name.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (ProfileManager.profiles.get(name.getText()) != null) {
                    JOptionPane.showConfirmDialog(null, "This name is used in another profile.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Profile profile = new Profile(name.getText(), (String) minecraftVersion.getSelectedItem(), (Loader) modLoader.getSelectedItem(), "");
                ProfileManager.profiles.put(profile.name, profile);
                MainTab.profilesView.resetView();
                ProfileManager.save();
            }
        };
    }

    public void resetView(Profile profile) {
        textArea.setText(profile.getText());
    }
}
