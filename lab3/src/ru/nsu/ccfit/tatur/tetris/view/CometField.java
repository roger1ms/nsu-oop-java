package ru.nsu.ccfit.tatur.tetris.view;

import java.awt.*;
import java.util.Random;

public class CometField {
    private final Random random = new Random();
    private Comet activeComet;

    public void updateAndDraw(Graphics2D g2d, int width, int height) {
        spawnCometIfNeeded(width, height);

        if (activeComet != null) {
            activeComet.update();
            activeComet.draw(g2d);

            if (activeComet.isOutOfBounds(width, height)) {
                activeComet = null;
            }
        }
    }

    private void spawnCometIfNeeded(int width, int height) {
        if (activeComet != null) {
            return;
        }

        if (random.nextDouble() > 0.1) {
            return;
        }

        boolean leftOrRight = random.nextBoolean();
        double startX;
        double startY;
        double dx;
        double dy;

        if (leftOrRight) {
            startX = -60;
            startY = random.nextInt(Math.max(1, height / 2));
            dx = 1.8 + random.nextDouble() * 1.2;
            dy = 0.8 + random.nextDouble() * 0.6;
        } else {
            startX = width + 60;
            startY = random.nextInt(Math.max(1, height / 2));
            dx = -(1.8 + random.nextDouble() * 1.2);
            dy = 0.8 + random.nextDouble() * 0.6;
        }

        int tailLength = 12 + random.nextInt(10);
        int headSize = 3 + random.nextInt(3);
        int alpha = 140 + random.nextInt(70);

        activeComet = new Comet(startX, startY, dx, dy, tailLength, headSize, alpha);
    }
}