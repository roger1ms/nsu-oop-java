package ru.nsu.ccfit.tatur.tetris.util;

import java.awt.*;

public class GameConstants {
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int CELL_SIZE = 37;
    public static final int DELAY_MS = 500;
    public static final int RENDER_DELAY_MS = 16;
    public static final int FLASH_DURATION_MS = 120;
    public static final char[] FIGURE_TYPES = {'I', 'J', 'L', 'O', 'S', 'T', 'Z'};

    public static final Color[] COLORS = {
            new Color(60, 120, 150),
            new Color(50, 90, 160),
            new Color(90, 110, 150),
            new Color(100, 140, 110),
            new Color(70, 150, 130),
            new Color(100, 80, 150),
            new Color(130, 90, 110)
    };

    public static final int[][] SHAPE_I = {
            {0, 1}, {1, 1}, {2, 1}, {3, 1}
    };
    public static final int PIVOT_I = 1;

    public static final int[][] SHAPE_J = {
            {0, 0}, {0, 1}, {1, 1}, {2, 1}
    };
    public static final int PIVOT_J = 1;

    public static final int[][] SHAPE_L = {
            {2, 0}, {0, 1}, {1, 1}, {2, 1}
    };
    public static final int PIVOT_L = 2;

    public static final int[][] SHAPE_O = {
            {0, 0}, {1, 0}, {0, 1}, {1, 1}
    };
    public static final int PIVOT_O = 0;

    public static final int[][] SHAPE_S = {
            {1, 0}, {2, 0}, {0, 1}, {1, 1}
    };
    public static final int PIVOT_S = 1;

    public static final int[][] SHAPE_T = {
            {1, 0}, {0, 1}, {1, 1}, {2, 1}
    };
    public static final int PIVOT_T = 2;

    public static final int[][] SHAPE_Z = {
            {0, 0}, {1, 0}, {1, 1}, {2, 1}
    };
    public static final int PIVOT_Z = 1;

    public static final int[][][] ALL_SHAPES = {
            SHAPE_I, SHAPE_J, SHAPE_L, SHAPE_O, SHAPE_S, SHAPE_T, SHAPE_Z
    };

    public static final int[] ALL_PIVOTS = {
            PIVOT_I, PIVOT_J, PIVOT_L, PIVOT_O, PIVOT_S, PIVOT_T, PIVOT_Z
    };

    public static final char[] ALL_TYPES = {
            'I', 'J', 'L', 'O', 'S', 'T', 'Z'
    };

    public static final int FIGURE_COUNT = FIGURE_TYPES.length;

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
}