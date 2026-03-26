package ru.nsu.ccfit.tatur.tetris.model;
import java.awt.*;
public class Cell {
    private boolean filled;
    private Color color;

    public Cell() {
        this.filled = false;
        this.color = null;
    }

    public boolean getFilled() { return this.filled; }
    public Color getColor() { return this.color; }
    public void setFilled(boolean filled) { this.filled = filled; }
    public void setColor(Color color) { this.color = color; }
}
