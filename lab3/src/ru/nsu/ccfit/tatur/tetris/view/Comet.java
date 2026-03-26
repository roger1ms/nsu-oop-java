package ru.nsu.ccfit.tatur.tetris.view;

import java.awt.*;

public class Comet {
    private double x;
    private double y;
    private final double dx;
    private final double dy;
    private final int tailLength;
    private final int headSize;
    private final int alpha;

    public Comet(double x, double y, double dx, double dy, int tailLength, int headSize, int alpha) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tailLength = tailLength;
        this.headSize = headSize;
        this.alpha = alpha;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g2d) {
        int headX = (int) x;
        int headY = (int) y;

        int centerX = headX + headSize / 2;
        int centerY = headY + headSize / 2;

        for (int i = tailLength; i >= 1; i--) {
            double t = (double) i / tailLength;
            int tailAlpha = (int) (alpha * (1.0 - t) * 0.7);

            if (tailAlpha <= 0) {
                continue;
            }

            int x1 = (int) (centerX - dx * i * 3);
            int y1 = (int) (centerY - dy * i * 3);
            int x2 = (int) (centerX - dx * (i - 1) * 3);
            int y2 = (int) (centerY - dy * (i - 1) * 3);

            g2d.setColor(new Color(160, 220, 255, tailAlpha));
            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.setColor(new Color(180, 230, 255, alpha / 3));
        g2d.fillOval(headX - 2, headY - 2, headSize + 4, headSize + 4);

        g2d.setColor(new Color(235, 245, 255, alpha));
        g2d.fillOval(headX, headY, headSize, headSize);
    }

    public boolean isOutOfBounds(int width, int height) {
        return x > width + 100 || y > height + 100 || x < -100 || y < -100;
    }
}