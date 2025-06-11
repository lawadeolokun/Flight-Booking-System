package gui;

import logging.AssignmentLogger;

import javax.swing.*;
import java.awt.*;

public class FlashingThread extends Thread{
    private JTextArea confirmation;
    private boolean running = true;

    private Color flashColor;

    public FlashingThread(JTextArea confirmation, Color flashColor){
        this.confirmation = confirmation;
        this.flashColor = flashColor;
    }

    public void run(){
        AssignmentLogger.logMethodEntry(this);
        int colorIndex = 0;
        while (running){
            try{
                confirmation.setBackground(flashColor);
                Thread.sleep(500);
                confirmation.setBackground(Color.WHITE);
                Thread.sleep(500);
            } catch (InterruptedException e){
                break;
            }
            AssignmentLogger.logMethodExit(this);
        }
    }

    public void stopThread(){
        AssignmentLogger.logMethodEntry(this);
        running = false;
        AssignmentLogger.logMethodExit(this);
    }
}
