package ru.nsu.ccfit.tatur.tetris.controller;

import ru.nsu.ccfit.tatur.tetris.model.GameModel;
import ru.nsu.ccfit.tatur.tetris.util.AudioManager;
import ru.nsu.ccfit.tatur.tetris.view.GameView;
import ru.nsu.ccfit.tatur.tetris.view.GameFrame;
import ru.nsu.ccfit.tatur.tetris.util.GameConstants;

import javax.swing.*;
import java.awt.event.*;

public class GameController implements KeyListener {

    private GameModel model;
    private GameView view;
    private GameFrame frame;
    private Timer gameTimer;
    private Timer renderTimer;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        gameTimer = new Timer(GameConstants.DELAY_MS, e -> gameLogicTick());
        renderTimer = new Timer(GameConstants.RENDER_DELAY_MS, e -> renderTick());
    }

    public void startGame() {
        model.startGame();
        frame.updateStats();
        frame.updateNextFigure();
        frame.updateHighScore();

        gameTimer.start();
        renderTimer.start();
    }

    public void togglePause() {
        model.togglePause();
        if (model.isPause()) {
            gameTimer.stop();
        } else {
            gameTimer.start();
        }
    }

    private void gameLogicTick() {
        if (!model.isPause() && !model.isGameOver()) {
            model.tick();

            java.util.List<Integer> clearedLines = model.getRecentlyClearedLines();
            if (!clearedLines.isEmpty()) {
                view.startLineFlash(clearedLines);
            }

            frame.updateStats();
            frame.updateNextFigure();
            frame.updateHighScore();
        }
    }

    private void renderTick() {
        view.refresh();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                AudioManager.playSound("move");
                model.move(model.getCurrentFigure(), -1, 0);
                break;

            case KeyEvent.VK_RIGHT:
                AudioManager.playSound("move");
                model.move(model.getCurrentFigure(), 1, 0);
                break;

            case KeyEvent.VK_DOWN:
                AudioManager.playSound("move");
                model.move(model.getCurrentFigure(), 0, 1);
                break;

            case KeyEvent.VK_UP:
                AudioManager.playSound("rotate");
                model.rotate(model.getCurrentFigure());
                break;

            case KeyEvent.VK_SPACE:
                hardDrop();
                AudioManager.playSound("drop");
                break;

            case KeyEvent.VK_P:
                togglePause();
                AudioManager.playSound("pause");
                break;

            case KeyEvent.VK_N:
                AudioManager.playMusic();
                startGame();
                break;

            default:
                return;
        }

        view.refresh();
        frame.updateStats();
        frame.updateNextFigure();
        frame.updateHighScore();
    }

    private void hardDrop() {
        while (model.move(model.getCurrentFigure(), 0, 1)) {
            // Двигаем вниз пока возможно
        }
        view.refresh();
        frame.updateStats();
        frame.updateNextFigure();
        frame.updateHighScore();
    }

    public void setFrame(GameFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}