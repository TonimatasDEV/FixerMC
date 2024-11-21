package dev.tonimatas.fixermc.gui.profiles;

import javax.swing.*;
import java.awt.*;

public class FixerProfileSearch extends JPanel {
    private final static int squareSize = 80;
    private final static int squareSpacing = 22;
    private final static int columns = 7;
    
    public FixerProfileSearch() {
        setLayout(new GridLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        setPreferredSize(new Dimension(750, 0));
        
        int totalSquares = 50;
        int contentHeight = (int) (Math.ceil((double) totalSquares / columns) * (squareSize + squareSpacing)) + 6;
        
        JPanel contentPanel  = new JPanel(null);
        contentPanel.setLayout(null);
        contentPanel.setPreferredSize(new Dimension(500, contentHeight));

        
        for (int i = 0; i < totalSquares; i++) {
            JPanel redSquare = new JPanel();
            redSquare.setBackground(Color.RED);
            redSquare.setBounds(squareSpacing + (i % columns) * 100, squareSpacing + (i / columns) * 100, squareSize, squareSize);
            contentPanel.add(redSquare);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        add(scrollPane);
    }
}
