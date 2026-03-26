package ru.nsu.ccfit.tatur.tetris.util;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static final Map<String, Clip> soundEffects = new HashMap<>();

    private static boolean musicEnabled = true;
    private static boolean sfxEnabled = true;

    private static final String[] SFX_NAMES = {
            "move", "rotate", "clear", "drop", "gameover1", "background1", "pause"
    };

    public static void init() {
        preloadAllSounds();
    }

    private static void preloadAllSounds() {
        for (String name : SFX_NAMES) {
            Clip clip = loadClip("music/" + name + ".wav");
            if (clip != null) {
                soundEffects.put(name, clip);
                System.out.println("Download: " + name);
            } else {
                System.out.println("Dont find: " + name);
            }
        }
    }

    private static Clip loadClip(String relativePath) {
        try {
            File file = new File(relativePath);

            if (!file.exists()) {
                return null;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;

        } catch (Exception e) {
            System.out.println("Error: " + relativePath + " - " + e.getMessage());
            return null;
        }
    }

    public static void playSound(String name) {
        if (!sfxEnabled) return;

        Clip clip = soundEffects.get(name);
        if (clip == null) return;
        if (clip.isRunning()) {
            return;
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public static void playMusic() {
        if (!musicEnabled) {
            System.out.println("Music turned off");
            return;
        }
        Clip clip = soundEffects.get("background1");
        if (clip == null) {
            return;
        }
        musicEnabled = false;
        clip.stop();
        clip.setFramePosition(0);
        clip.flush();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public static void stopMusic() {
        if (!musicEnabled) {
            System.out.println("Music turned off");
        }
        Clip clip = soundEffects.get("background1");
        musicEnabled = true;
        if (clip == null) {
            return;
        }
        clip.stop();
    }

    public static boolean isMusicEnabled() {
        return musicEnabled;
    }

    public static void setMusicEnabled(boolean newState) { musicEnabled = newState; }
}