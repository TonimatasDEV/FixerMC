package dev.tonimatas.fixermc.gui.profiles;

import javax.swing.*;
import java.awt.*;

public class FixerProfileSearch extends JScrollPane {
    private final static int squareSize = 160;
    private final static int squareSpacing = 15;
    private final static int columns = 4;
    
    public FixerProfileSearch() {
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        setPreferredSize(new Dimension(750, 0));
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUnitIncrement(7);
        
        int totalSquares = 15 + 1;
        int rows = (int) (Math.ceil((double) totalSquares / columns));
        
        if (rows < 5) {
            rows = 5;
        }
        
        int contentHeight = rows * (squareSize + squareSpacing) + 6;
        
        JPanel contentPanel  = new JPanel(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        contentPanel.setLayout(new GridLayout(rows, columns, 15, 15));
        contentPanel.setPreferredSize(new Dimension(500, contentHeight));

        int voidSquares = rows * columns - totalSquares;
        
        for (int i = 0; i < totalSquares - 1; i++) {
            FixerProfile profile = new FixerProfile();
            profile.setBackground(Color.BLACK);
            contentPanel.add(profile);
        }

        JButton createProfile = new JButton("+");
        createProfile.setFont(createProfile.getFont().deriveFont(20f));
        contentPanel.add(createProfile);

        for (int i = 0; i < voidSquares; i++) {
            JPanel profile = new JPanel();
            contentPanel.add(profile);
        }

        setViewportView(contentPanel);
    }
}
