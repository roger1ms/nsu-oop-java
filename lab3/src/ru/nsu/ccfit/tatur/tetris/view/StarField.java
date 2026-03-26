package ru.nsu.ccfit.tatur.tetris.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarField {
    private final List<Star> stars = new ArrayList<>();
    private final Random random = new Random();

    public StarField(int count, int width, int height) {
        generateSmallStars(count, width, height);
        generateBigStars(count / 4, width, height);
    }

    private void generateSmallStars(int count, int width, int height) {
        for (int i = 0; i < count; i++) {
            double x = random.nextInt(width);
            int y = random.nextInt(height);
            int size = 1;
            int alpha = 80 + random.nextInt(40);

            double phase = random.nextDouble() * Math.PI * 2;
            double speed = 0.1 + random.nextDouble() * 0.2;

            stars.add(new Star(x, y, size, alpha, phase, speed));
        }
    }

    private void generateBigStars(int count, int width, int height) {
        for (int i = 0; i < count; i++) {
            double x = random.nextInt(width);
            int y = random.nextInt(height);
            int size = random.nextInt(2) + 2;
            int alpha = 150 + random.nextInt(80);

            double phase = random.nextDouble() * Math.PI * 2;
            double speed = 0.2 + random.nextDouble() * 0.3;

            stars.add(new Star(x, y, size, alpha, phase, speed));
        }
    }

    public void draw(Graphics2D g2d, int width) {
        double t = System.currentTimeMillis() / 200.0;

        for (Star star : stars) {
            int alpha = (int) (star.getBaseAlpha() + 50 * Math.sin(t + star.getPhase()));
            alpha = Math.max(30, Math.min(255, alpha));

            g2d.setColor(new Color(255, 255, 255, alpha));
            g2d.fillOval(star.getX(), star.getY(), star.getSize(), star.getSize());

            star.update(width);
        }
    }
}