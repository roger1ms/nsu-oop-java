package ru.nsu.ccfit.tatur.tetris.view;

import ru.nsu.ccfit.tatur.tetris.model.TetrisFigures;

import javax.swing.*;
import java.awt.*;

public class NextPieceView extends JPanel {
    private TetrisFigures nextFigure;

    public NextPieceView() {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createLineBorder(new Color(70, 110, 170)));
    }

    public void setNextFigure(TetrisFigures figure) {
        this.nextFigure = figure;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(20, 28, 50),
                0, getHeight(), new Color(8, 12, 24)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (nextFigure == null) {
            return;
        }

        int cellSize = 20;
        int[][] shape = nextFigure.getShape();

        int minX = 10, minY = 10;
        for (int[] block : shape) {
            minX = Math.min(minX, block[0]);
            minY = Math.min(minY, block[1]);
        }

        int maxX = 0, maxY = 0;
        for (int[] block : shape) {
            maxX = Math.max(maxX, block[0]);
            maxY = Math.max(maxY, block[1]);
        }

        int figureWidth = (maxX - minX + 1) * cellSize;
        int figureHeight = (maxY - minY + 1) * cellSize;

        int offsetX = (getWidth() - figureWidth) / 2;
        int offsetY = (getHeight() - figureHeight) / 2;

        for (int[] block : shape) {
            int x = offsetX + block[0] * cellSize;
            int y = offsetY + block[1] * cellSize;

            drawBlock(g2d, x, y, cellSize, nextFigure.getColor());
        }
    }

    private void drawBlock(Graphics2D g2d, int x, int y, int cellSize, Color color) {
        int size = cellSize - 2;

        g2d.setColor(new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                45
        ));
        g2d.fillRect(x - 1, y - 1, size + 2, size + 2);

        g2d.setColor(color);
        g2d.fillRect(x, y, size, size);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, size, size);

        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fillRect(x + 2, y + 2, size - 4, 3);
        g2d.fillRect(x + 2, y + 2, 3, size - 4);

        g2d.setColor(new Color(255, 255, 255, 255));
        g2d.fillRect(x + 3, y + 3, 2, 2);
    }
}