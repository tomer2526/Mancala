package com.point.mancala;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public abstract class UIUX {
    public static boolean gameSound = true;

    // Play sound (wav files)
    public static void playSound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Start playing the sound in a separate thread
            new Thread(clip::start).start();

            // Close the resources
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void small_btn_in_sound() throws Exception {
        playSound("src/main/resources/assets/sound effects/small btn sound - in.wav");
    }

    @FXML
    protected void small_btn_out_sound() throws Exception {
        playSound("src/main/resources/assets/sound effects/small btn sound - out.wav");
    }

    protected void select_hole_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/select hole sound.wav");
    }

    protected void disable_hole_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/disable btn sound.wav");
    }

    protected void hover_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/hover sound.wav");
    }

    protected void disable_hover_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/disable hover sound.wav");
    }

    protected void disable_btn_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/disable btn sound.wav");
    }

    protected void achievement_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/achievement sound.wav");
    }

    protected void bonus_turn_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/bonus turn sound.wav");
    }

    protected void start_game_sound() {
        if (gameSound)
            playSound("src/main/resources/assets/sound effects/start game sound.wav");
    }


    // set glow effect on buttons when hover
    @FXML
    protected void btn_hover_effect_on(MouseEvent event) {
        ((Button) event.getSource()).setEffect(new Glow(0.3));
    }

    @FXML
    protected void btn_hover_effect_off(MouseEvent event) {
        ((Button) event.getSource()).setEffect(new Glow(0));
    }
}


