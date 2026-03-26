package ru.nsu.ccfit.tatur.tetris.view;

import ru.nsu.ccfit.tatur.tetris.controller.GameController;
import ru.nsu.ccfit.tatur.tetris.model.GameModel;
import ru.nsu.ccfit.tatur.tetris.util.GameConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private final GameModel model;
    private JLabel scoreLabel;
    private JLabel linesLabel;
    private JLabel highScoreLabel;
    private NextPieceView nextPieceView;

    public GameFrame(GameModel model, GameView view, GameController controller) {
        this.model = model;

        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        add(view, BorderLayout.CENTER);

        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.EAST);

        JMenuBar menuBar = createMenuBar(controller);
        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(170, GameConstants.BOARD_HEIGHT * GameConstants.CELL_SIZE));
        sidebar.setBackground(new Color(8, 12, 24));
        sidebar.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel nextPanel = createSectionPanel();
        JLabel nextLabel = createSectionTitle("NEXT");
        nextPanel.add(nextLabel);
        nextPanel.add(Box.createVerticalStrut(10));

        nextPieceView = new NextPieceView();
        nextPieceView.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextPanel.add(nextPieceView);

        JPanel statsPanel = createSectionPanel();
        JLabel statsTitle = createSectionTitle("STATS");
        statsPanel.add(statsTitle);
        statsPanel.add(Box.createVerticalStrut(12));

        scoreLabel = createValueLabel("Score: " + model.getScore());
        linesLabel = createValueLabel("Lines: " + model.getTotalLinesCleared());
        highScoreLabel = createAccentLabel("Best: " + model.getHighScore());

        statsPanel.add(scoreLabel);
        statsPanel.add(Box.createVerticalStrut(8));
        statsPanel.add(linesLabel);
        statsPanel.add(Box.createVerticalStrut(8));
        statsPanel.add(highScoreLabel);

        JPanel controlsPanel = createSectionPanel();
        JLabel controlsTitle = createSectionTitle("CONTROLS");
        controlsPanel.add(controlsTitle);
        controlsPanel.add(Box.createVerticalStrut(12));
        controlsPanel.add(createHintLabel("← ↓ →  Move"));
        controlsPanel.add(Box.createVerticalStrut(6));
        controlsPanel.add(createHintLabel("↑  Rotate"));
        controlsPanel.add(Box.createVerticalStrut(6));
        controlsPanel.add(createHintLabel("SPACE  Drop"));

        sidebar.add(nextPanel);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(statsPanel);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(controlsPanel);
        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JPanel createSectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(14, 20, 36));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 110, 170)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(new Color(170, 220, 255));
        label.setFont(new Font("Arial", Font.BOLD, 15));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(new Color(220, 230, 255));
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JLabel createAccentLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(new Color(120, 200, 255));
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JLabel createHintLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(new Color(180, 190, 215));
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    private JMenuBar createMenuBar(GameController controller) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(14, 20, 36));
        menuBar.setBorder(BorderFactory.createLineBorder(new Color(70, 110, 170)));

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setForeground(new Color(220, 230, 255));

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startGame();
                updateStats();
                updateNextFigure();
            }
        });
        gameMenu.add(newGameItem);

        gameMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gameMenu.add(exitItem);

        menuBar.add(gameMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(new Color(220, 230, 255));

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        GameFrame.this,
                        "Tetris Game\nVersion 1.0\nStudent: Tatur",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);

        return menuBar;
    }

    public void updateStats() {
        scoreLabel.setText("Score: " + model.getScore());
        linesLabel.setText("Lines: " + model.getTotalLinesCleared());
    }

    public void updateNextFigure() {
        nextPieceView.setNextFigure(model.getNextFigure());
    }

    public void updateHighScore() {
        highScoreLabel.setText("Best: " + model.getHighScore());
    }
}