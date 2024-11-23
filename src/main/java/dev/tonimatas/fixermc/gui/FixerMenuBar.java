package dev.tonimatas.fixermc.gui;

import dev.tonimatas.fixermc.sessions.Account;
import dev.tonimatas.fixermc.sessions.AccountManager;
import dev.tonimatas.fixermc.sessions.OfflineAccount;
import dev.tonimatas.fixermc.sessions.OnlineAccount;
import dev.tonimatas.fixermc.util.FixerUtils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FixerMenuBar extends JMenuBar {
    public static JMenu loginMenu;

    public FixerMenuBar() {
        loginMenu = new JMenu();

        setLoginTitle();

        add(Box.createHorizontalGlue());

        resetAccountsView();

        add(loginMenu);
    }

    private static void resetAccountsView() {
        loginMenu.removeAll();

        for (String username : AccountManager.accounts.keySet()) {
            JMenuItem accountItem = new JMenuItem(username, FixerUtils.getImageIcon("steve"));
            loginMenu.add(accountItem);

            accountItem.addActionListener(e -> {
                AccountManager.selectedAccount = accountItem.getText();
                setLoginTitle();
            });
        }

        JMenuItem addAccount = new JMenuItem("Add...");
        addAccount.addActionListener(addAccountAction());
        loginMenu.add(addAccount);

        JMenuItem removeAccount = new JMenuItem("Remove...");
        removeAccount.addActionListener(removeAccountAction());
        loginMenu.add(removeAccount);

        setLoginTitle();
    }

    private static void setLoginTitle() {
        if (AccountManager.selectedAccount.isEmpty()) {
            loginMenu.setText("Login");
        } else {
            loginMenu.setIcon(FixerUtils.getImageIcon("steve"));
            loginMenu.setText(AccountManager.selectedAccount);
        }
    }

    private static ActionListener addAccountAction() {
        return e -> {
            int option = JOptionPane.showOptionDialog(null, "Select one account type.", "Add account", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Premium", "No-Premium", "Cancel"}, null);

            Account account = null;
            switch (option) {
                case 0 -> account = new OnlineAccount(AccountManager.addAccount());

                case 1 -> {
                    String username = JOptionPane.showInputDialog(null, "Enter username: ");
                    account = new OfflineAccount(username);
                }
            }

            if (account == null) return;

            String username = account.getAuthInfos().getUsername();

            AccountManager.accounts.put(username, account);
            AccountManager.selectedAccount = username;
            AccountManager.save();

            resetAccountsView();
        };
    }

    private static ActionListener removeAccountAction() {
        return e -> {
            String[] accounts = AccountManager.accounts.keySet().toArray(new String[0]);
            JComboBox<String> accountsBox = new JComboBox<>(accounts);
            int option = JOptionPane.showConfirmDialog(null, accountsBox, "Select the account you want remove", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String selectedAccount = (String) accountsBox.getSelectedItem();

                AccountManager.accounts.remove(selectedAccount);

                if (AccountManager.selectedAccount.equals(selectedAccount)) {
                    AccountManager.selectedAccount = AccountManager.accounts.keySet().stream().findFirst().orElse("");
                }

                AccountManager.save();
                resetAccountsView();
            }
        };
    }
}
