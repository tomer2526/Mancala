package com.point.mancala;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UIUX {
    public static boolean gameSound = true;

    // Play sound (wav files)
    public static void playSound(String filePath) {
        //System.out.println(filePath);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

//            clip.addLineListener(new LineListener() {
//                @Override
//                public void update(LineEvent event) {
//                    if (event.getType() == LineEvent.Type.STOP) {
//                        clip.close();
//                    }
//                }
//            });

            // Start playing the sound in a separate thread
            new Thread(clip::start).start();

            // Close the resources
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

//    private static void playSound(String sound){
//        // cl is the ClassLoader for the current class, ie. CurrentClass.class.getClassLoader();
//        URL file = cl.getResource(sound);
//        final Media media = new Media(file.toString());
//        final MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();
//    }

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
    protected void disable_hole_sound() {
        if(gameSound)
            playSound("src/main/resources/assets/sound effects/disable btn sound.wav");
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
    protected void achievement_sound() {
        if(gameSound)
            playSound("src/main/resources/assets/sound effects/achievement sound.wav");
    }
    protected void bonus_turn_sound() {
        if(gameSound)
            playSound("src/main/resources/assets/sound effects/bonus turn sound.wav");
    }



    // set glow effect on buttons when hover
    @FXML
    protected void btn_hover_effect_on(MouseEvent event){
        ((Button)event.getSource()).setEffect(new Glow(0.3));
    }
    @FXML
    protected void btn_hover_effect_off(MouseEvent event){
        ((Button)event.getSource()).setEffect(new Glow(0));
    }
}
