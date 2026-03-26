package ru.nsu.ccfit.tatur.tetris;

import ru.nsu.ccfit.tatur.tetris.model.GameModel;
import ru.nsu.ccfit.tatur.tetris.util.AudioManager;
import ru.nsu.ccfit.tatur.tetris.view.GameView;
import ru.nsu.ccfit.tatur.tetris.view.GameFrame;
import ru.nsu.ccfit.tatur.tetris.controller.GameController;

import javax.swing.*;

public class TetrisApp {
    public static void main(String[] args) {
        AudioManager.init();
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController( model, view);
        GameFrame frame = new GameFrame(model, view, controller);
        controller.setFrame(frame);
        view.addKeyListener(controller);
        view.setFocusable(true);
        view.requestFocusInWindow();

        controller.startGame();
        AudioManager.playMusic();
    }
}