package com.point.mancala;

import javafx.fxml.FXML;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class UIUX {
    public static boolean gameSound = true;

    // Play sound (wav files)
    public static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });

            // Start playing the sound in a separate thread
            new Thread(clip::start).start();


            // Close the resources
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void small_btn_in_sound() throws Exception{
        playSound("src/main/resources/assets/sound effects/small btn sound - in.wav");
    }
    @FXML
    protected void small_btn_out_sound() throws Exception{
        playSound("src/main/resources/assets/sound effects/small btn sound - out.wav");
    }
    protected void select_hole_sound() {
        if(gameSound)
            playSound("src/main/resources/assets/sound effects/select hole sound.wav");
    }
    protected void hover_sound() {
        if(gameSound)
            playSound("src/main/resources/assets/sound effects/hover sound.wav");
    }
    protected void disable_hover_sound() {
        if(gameSound)
            playSound("src/main/resources/assets/sound effects/disable hover sound.wav");
    }
    protected void disable_btn_sound() {
        if(gameSound)
            playSound("src/main/resources/assets/sound effects/disable btn sound.wav");
    }
}
