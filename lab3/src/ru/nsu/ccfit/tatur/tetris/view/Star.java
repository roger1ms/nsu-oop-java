package ru.nsu.ccfit.tatur.tetris.view;

public class Star {
    private double x;
    private final int y;
    private final int size;
    private final int baseAlpha;
    private final double phase;
    private final double speed;

    public Star(double x, int y, int size, int baseAlpha, double phase, double speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.baseAlpha = baseAlpha;
        this.phase = phase;
        this.speed = speed;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getBaseAlpha() {
        return baseAlpha;
    }

    public double getPhase() {
        return phase;
    }

    public void update(int width) {
        x -= speed;

        if (x + size < 0) {
            x = width;
        }
    }
}