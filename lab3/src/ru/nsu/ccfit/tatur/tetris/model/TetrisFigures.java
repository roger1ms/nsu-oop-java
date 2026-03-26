package ru.nsu.ccfit.tatur.tetris.model;

import ru.nsu.ccfit.tatur.tetris.util.GameConstants;

import java.awt.*;
import java.util.ArrayList;

public class TetrisFigures {
    private int[][] shape;
    private int x, y;
    private Color color;
    private int pivotIndex;
    private char type;

    public TetrisFigures(int[][] shape, int pivotIndex, Color color, char type) {
        this.shape = new int[4][2];
        for (int i = 0; i < 4; i++) {
            this.shape[i][0] = shape[i][0];
            this.shape[i][1] = shape[i][1];
        }
        this.pivotIndex = pivotIndex;
        this.color = color;
        this.x = 0;
        this.y = 0;
        this.type = type;
    }

    public int[][] getShape() { return this.shape; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public Color getColor() { return this.color; }
    public int getPivotIndex() { return this.pivotIndex; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }

    public ArrayList<Integer[]> getCoords() {
        ArrayList<Integer[]> coords = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int absX = x + shape[i][0];
            int absY = y + shape[i][1];
            coords.add(new Integer[]{absX, absY});
        }
        return coords;
    }

    public void rotate() {
        if (this.type == 'O') { return; }
        int pivotX = shape[pivotIndex][0];
        int pivotY = shape[pivotIndex][1];

        for (int i = 0; i < 4; i++) {
            int relX = shape[i][0] - pivotX;
            int relY = shape[i][1] - pivotY;

            shape[i][0] = pivotX - relY;
            shape[i][1] = pivotY + relX;
        }
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public TetrisFigures copy() {
        int[][] copyShape = new int[4][2];
        for (int i = 0; i < 4; i++) {
            copyShape[i][0] = this.shape[i][0];
            copyShape[i][1] = this.shape[i][1];
        }
        TetrisFigures copyFigure = new TetrisFigures(copyShape, pivotIndex, color, type);
        copyFigure.setX(this.x);
        copyFigure.setY(this.y);
        return copyFigure;
    }
}