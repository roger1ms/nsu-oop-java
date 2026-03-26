package ru.nsu.ccfit.tatur.tetris.view;

import ru.nsu.ccfit.tatur.tetris.model.Cell;
import ru.nsu.ccfit.tatur.tetris.model.GameModel;
import ru.nsu.ccfit.tatur.tetris.model.TetrisFigures;
import ru.nsu.ccfit.tatur.tetris.util.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JPanel {
    private final GameModel model;
    private final StarField starField;
    private final List<Integer> flashingLines = new ArrayList<>();
    private long flashStartTime = 0;

    private final CometField cometField_1;
    private final CometField cometField_2;
    private final CometField cometField_3;

    public GameView(GameModel model) {
        this.model = model;

        int width = GameConstants.BOARD_WIDTH * GameConstants.CELL_SIZE;
        int height = GameConstants.BOARD_HEIGHT * GameConstants.CELL_SIZE;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);

        starField = new StarField(200, width, height);
        cometField_1 = new CometField();
        cometField_2 = new CometField();
        cometField_3 = new CometField();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        GradientPaint bgGradient = new GradientPaint(
                0, 0, new Color(35, 15, 70),
                0, getHeight(), new Color(0, 0, 0)
        );
        g2d.setPaint(bgGradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        starField.draw(g2d, getWidth());
        drawSun(g2d);
        drawMoon(g2d);
        cometField_1.updateAndDraw(g2d, getWidth(), getHeight());
        cometField_2.updateAndDraw(g2d, getWidth(), getHeight());
        cometField_3.updateAndDraw(g2d, getWidth(), getHeight());
        drawGrid(g2d);
        drawBoard(g2d);
        drawCurrentFigure(g2d);
        drawGhostFigure(g2d);
        drawLineFlash(g2d);


        if (model.isGameOver()) {
            drawOverlay(g2d, "GAME OVER", "Press N for New Game");
        } else if (model.isPause()) {
            drawOverlay(g2d, "PAUSED", "Press P to Resume");
        }
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(new Color(70, 80, 110, 70));
        for (int x = 0; x <= GameConstants.BOARD_WIDTH; x++) {
            g2d.drawLine(x * GameConstants.CELL_SIZE, 0,
                    x * GameConstants.CELL_SIZE,
                    GameConstants.BOARD_HEIGHT * GameConstants.CELL_SIZE
            );
        }
        for (int y = 0; y <= GameConstants.BOARD_HEIGHT; y++) {
            g2d.drawLine(0, y * GameConstants.CELL_SIZE,
                    GameConstants.BOARD_WIDTH * GameConstants.CELL_SIZE,
                    y * GameConstants.CELL_SIZE
            );
        }
    }

    private void drawBoard(Graphics2D g2d) {
        Cell[][] board = model.getBoard();
        for (int x = 0; x < GameConstants.BOARD_WIDTH; x++) {
            for (int y = 0; y < GameConstants.BOARD_HEIGHT; y++) {
                if (board[x][y].getFilled()) {
                    drawStyledBlock(g2d, x, y, board[x][y].getColor());
                }
            }
        }
    }

    private void drawCurrentFigure(Graphics2D g2d) {
        TetrisFigures figure = model.getCurrentFigure();
        if (figure == null) {
            return;
        }

        for (Integer[] block : figure.getCoords()) {
            int x = block[0];
            int y = block[1];
            if (y >= 0) {
                drawStyledBlock(g2d, x, y, figure.getColor());
            }
        }
    }

    private void drawStyledBlock(Graphics2D g2d, int x, int y, Color baseColor) {
        int px = x * GameConstants.CELL_SIZE;
        int py = y * GameConstants.CELL_SIZE;
        int size = GameConstants.CELL_SIZE - 3;
        double t = System.currentTimeMillis() / 400.0;
        float factor = (float)(0.9 + 0.1 * Math.sin(t));

        Color animated = new Color(
                Math.min(255, (int)(baseColor.getRed() * factor)),
                Math.min(255, (int)(baseColor.getGreen() * factor)),
                Math.min(255, (int)(baseColor.getBlue() * factor))
        );
        GradientPaint gp = new GradientPaint(
                px, py,
                animated.darker().darker().darker(),
                px + size, py + size,
                animated
        );
        g2d.setPaint(gp);
        g2d.fill(new RoundRectangle2D.Float(px, py, size, size, 6, 6));

        g2d.setColor(Color.BLACK);
        g2d.drawRect(px, py, size, size);

        g2d.setColor(new Color(255, 255, 255, 150));
        g2d.fillRect(px + 2, py + 2, size - 4, 3);
        g2d.fillRect(px + 2, py + 2, 3, size - 4);

        g2d.setColor(new Color(255, 255, 255, 255));
        g2d.fillRect(px + 3, py + 3, 2, 2);
    }

    private void drawGhostFigure(Graphics2D g2d) {
        TetrisFigures figure = model.getCurrentFigure();
        if (figure == null) {
            return;
        }

        TetrisFigures ghost = figure.copy();
        while (model.move(ghost, 0, 1)) {
        }

        if (ghost.getX() == figure.getX() && ghost.getY() == figure.getY()) { return; }
        int alpha = (int) (100 + 50 * Math.sin(System.currentTimeMillis() / 300.0));
        Color ghostFill = new Color(210, 235, 255, alpha);
        Color ghostInner = new Color(255, 255, 255, Math.min(255, alpha + 20));

        for (Integer[] block : ghost.getCoords()) {
            int x = block[0];
            int y = block[1];

            if (y >= 0 && x >= 0) {
                int px = x * GameConstants.CELL_SIZE;
                int py = y * GameConstants.CELL_SIZE;
                int size = GameConstants.CELL_SIZE - 3;

                g2d.setColor(ghostFill);
                g2d.fillRect(px, py, size, size);

                g2d.setColor(ghostInner);
                g2d.drawRect(px + 1, py + 1, size - 2, size - 2);
            }
        }
    }

    private void drawLineFlash(Graphics2D g2d) {
        if (flashingLines.isEmpty()) {
            return;
        }

        long elapsed = System.currentTimeMillis() - flashStartTime;
        if (elapsed > GameConstants.FLASH_DURATION_MS) {
            flashingLines.clear();
            return;
        }

        int alpha = 255 - (int) (255.0 * elapsed / GameConstants.FLASH_DURATION_MS);
        alpha = Math.max(alpha, 0);
        int boardWidthPx = GameConstants.BOARD_WIDTH * GameConstants.CELL_SIZE;

        for (int line : flashingLines) {
            int py = line * GameConstants.CELL_SIZE;
            g2d.setColor(new Color(180, 240, 255, alpha));
            g2d.fillRect(0, py, boardWidthPx, GameConstants.CELL_SIZE);

            g2d.setColor(new Color(255, 255, 255, Math.min(255, alpha)));
            g2d.drawLine(0, py, boardWidthPx, py);
            g2d.drawLine(0, py + GameConstants.CELL_SIZE - 1, boardWidthPx, py + GameConstants.CELL_SIZE - 1);
        }
    }

    public void startLineFlash(List<Integer> lines) {
        flashingLines.clear();
        flashingLines.addAll(lines);
        flashStartTime = System.currentTimeMillis();
    }

    private void drawOverlay(Graphics2D g2d, String title, String subtitle) {
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        Font titleFont = new Font("Arial", Font.BOLD, 36);
        Font subtitleFont = new Font("Arial", Font.PLAIN, 18);

        FontMetrics titleMetrics = g2d.getFontMetrics(titleFont);
        FontMetrics subtitleMetrics = g2d.getFontMetrics(subtitleFont);

        int titleWidth = titleMetrics.stringWidth(title);
        int subtitleWidth = subtitleMetrics.stringWidth(subtitle);
        int titleHeight = titleMetrics.getHeight();

        int titleX = (getWidth() - titleWidth) / 2;
        int subtitleX = (getWidth() - subtitleWidth) / 2;
        int titleY = (getHeight() - titleHeight) / 2;
        int subtitleY = titleY + titleHeight + 10;

        g2d.setFont(titleFont);
        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.drawString(title, titleX + 2, titleY + 2);

        g2d.setColor(new Color(170, 220, 255));
        g2d.drawString(title, titleX, titleY);

        g2d.setFont(subtitleFont);
        g2d.setColor(new Color(210, 220, 235));
        g2d.drawString(subtitle, subtitleX, subtitleY);
    }

    private void drawSun(Graphics2D g2d) {
        int radius = 140;
        int centerX = 80;
        int centerY = 80;

        float[] dist = {0.0f, 0.2f, 0.6f, 1.0f};
        Color[] colors = {
                new Color(255, 255, 200, 200),
                new Color(255, 220, 120, 160),
                new Color(255, 140, 60, 80),
                new Color(255, 80, 30, 0)
        };

        RadialGradientPaint paint = new RadialGradientPaint(
                new Point(centerX, centerY),
                radius,
                dist,
                colors
        );

        g2d.setPaint(paint);
        g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    private void drawMoon(Graphics2D g2d) {
        int radius = 100;
        int x = getWidth() - 120;
        int y = 60;

        g2d.setColor(new Color(220, 230, 255, 120));
        g2d.fillOval(x, y, radius * 2, radius * 2);

        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillOval(x + 40, y, radius * 2, radius * 2);
    }

    public void refresh() {
        repaint();
    }
}