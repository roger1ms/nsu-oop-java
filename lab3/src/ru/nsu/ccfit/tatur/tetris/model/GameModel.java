package ru.nsu.ccfit.tatur.tetris.model;
import ru.nsu.ccfit.tatur.tetris.util.AudioManager;
import ru.nsu.ccfit.tatur.tetris.util.GameConstants;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private Cell[][] board;
    private TetrisFigures currentFigure;
    private TetrisFigures nextFigure;
    private final List<Integer> recentlyClearedLines = new ArrayList<>();

    private int score;
    private int totalLinesCleared;
    private boolean isGameOver;
    private boolean pause;

    private static final String SCORE_FILE = "highScore.txt";
    int highScore;

    public GameModel() {
        board = new Cell[GameConstants.BOARD_WIDTH][GameConstants.BOARD_HEIGHT];
        for (int i = 0; i < GameConstants.BOARD_WIDTH; i++) {
            for (int j = 0; j < GameConstants.BOARD_HEIGHT; j++) {
                board[i][j] = new Cell();
            }
        }
        currentFigure = createRandomFigure();
        nextFigure = createRandomFigure();
        score = 0;
        totalLinesCleared = 0;
        isGameOver = false;
        pause = false;
    }

    public void startGame() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell();
            }
        }
        score = 0;
        totalLinesCleared = 0;
        isGameOver = false;
        pause = false;

        highScore = loadHighScore();
        currentFigure = createRandomFigure();
        nextFigure = createRandomFigure();
    }

    public boolean checkCollision(TetrisFigures figure) {
        ArrayList<Integer[]> coords = figure.getCoords();

        for (Integer[] figureCoords : coords) {
            int x = figureCoords[0];
            int y = figureCoords[1];
            if (x < 0 || y < 0 || x >= GameConstants.BOARD_WIDTH || y >= GameConstants.BOARD_HEIGHT) {
                return true;
            }
            if (board[x][y].getFilled()) {
                return true;
            }
        }
        return false;
    }

    public boolean move(TetrisFigures figure, int dx, int dy) {
        if (isGameOver || pause) {
            return false;
        }

        figure.move(dx, dy);
        if (checkCollision(figure)) {
            figure.move(-dx, -dy);
            return false;
        }
        return true;
    }

    public boolean rotate(TetrisFigures figure) {
        if (isGameOver || pause) {
            return false;
        }
        TetrisFigures figureCopy = figure.copy();
        figureCopy.rotate();
        if (checkCollision(figureCopy)) {
            return false;
        }
        figure.rotate();
        return true;
    }

    public void lockPiece(TetrisFigures figure) {
        ArrayList<Integer[]> coords = figure.getCoords();

        for (Integer[] figureCoords : coords) {
            int x = figureCoords[0];
            int y = figureCoords[1];
            if (y >= 0  && y < GameConstants.BOARD_HEIGHT && x >= 0 && x < GameConstants.BOARD_WIDTH) {
                board[x][y].setFilled(true);
                board[x][y].setColor(figure.getColor());
            }
        }
    }

    public void clearLine() {
        recentlyClearedLines.clear();
        int linesCleared = 0;
        for (int y = GameConstants.BOARD_HEIGHT - 1; y >= 0; y--) {
            boolean  isFull = true;
            for (int i = 0; i < GameConstants.BOARD_WIDTH; i++) {
                if (!board[i][y].getFilled()) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                recentlyClearedLines.add(y - linesCleared);
                for (int i = 0; i < GameConstants.BOARD_WIDTH; i++) {
                    board[i][y].setFilled(false);
                    board[i][y].setColor(null);
                }

                for (int y2 = y; y2 >= 1; y2--) {
                    for (int x = 0; x < GameConstants.BOARD_WIDTH; x++) {
                        board[x][y2] = board[x][y2-1];
                    }
                }
                for (int x = 0; x < GameConstants.BOARD_WIDTH; x++) {
                    board[x][0] = new Cell();
                }
                linesCleared++;
                y++;
                AudioManager.playSound("clear");
            }
        }
        if (linesCleared > 0) {
            totalLinesCleared += linesCleared;
            score += (80 + 10*(linesCleared-1))*linesCleared/2;
        }
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
    }

    public List<Integer> getRecentlyClearedLines() {
        List<Integer> result = new ArrayList<>(recentlyClearedLines);
        recentlyClearedLines.clear();
        return result;
    }

    public void tick() {
        if (pause || isGameOver) {
            return;
        }

        if (move(currentFigure,0,1)) {}
        else {
            lockPiece(currentFigure);
            clearLine();
            currentFigure = nextFigure;
            nextFigure = createRandomFigure();
            if (checkCollision(currentFigure)) {
                isGameOver = true;
                AudioManager.playSound("gameover1");
                AudioManager.stopMusic();
                AudioManager.setMusicEnabled(true);
            }
        }
    }

    private TetrisFigures createRandomFigure() {
        int index = GameConstants.getRandom(0, GameConstants.FIGURE_COUNT - 1);
        int[][] shape = GameConstants.ALL_SHAPES[index];
        int pivot = GameConstants.ALL_PIVOTS[index];
        Color color = GameConstants.COLORS[index];
        char type = GameConstants.ALL_TYPES[index];
        TetrisFigures figure = new TetrisFigures(shape, pivot, color, type);

        figure.setPosition(GameConstants.BOARD_WIDTH / 2 - 2, 0);

        return figure;
    }

    public void togglePause() {
        if (!isGameOver) {
            pause = !pause;
        }
    }

    public void saveHighScore() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            writer.println(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            return 0;
        }
    }

    public Cell[][] getBoard() { return this.board; }
    public TetrisFigures getCurrentFigure() { return this.currentFigure; }
    public TetrisFigures getNextFigure() { return this.nextFigure; }
    public int getScore() { return score; }
    public int getTotalLinesCleared() { return totalLinesCleared; }
    public boolean isGameOver() { return isGameOver; }
    public boolean isPause() { return pause; }
    public int getHighScore() { return highScore;
    }
}
