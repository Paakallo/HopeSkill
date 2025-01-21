import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Menu extends MouseAdapter {
    private Main game;
    private boolean startGameClicked = false;

    //graphics
    private BufferedImage backgroundImage;
    private BufferedImage levelButton;
    private BufferedImage heart;
    private BufferedImage reflection;

    static int BUTTON_WIDTH = 200;
    static int BUTTON_HEIGHT = 100;

    int PLAY_BUTTON_X = 510;
    int PLAY_BUTTON_Y = 300;


    int RETURN_BUTTON_X = 0;
    int RETURN_BUTTON_Y = 0;

    int RETURN_BUTTON_HEIGHT = 50;

    int SELECT_LEVEL_WIDTH = 165;
    int SELECT_LEVEL_HEIGHT = 100;

    int HEART_WIDTH = 30;
    int HEART_HEIGHT = 30;

    public Menu(Main game) {
        this.game = game;

        try {
            // Load the background image
            backgroundImage = ImageIO.read(new File("resources/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            levelButton = ImageIO.read(new File("resources/open_level.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            heart = ImageIO.read(new File("resources/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reflection = ImageIO.read(new File("resources/reflection.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void render (Graphics g){

        if (game.getGameState() == GameState.MENU) {

            g.drawImage(backgroundImage, 0, 0, game.getWidth(), game.getHeight(), null);
            
            if (!startGameClicked) {
                renderStartMenu(g);
        
            } else {
                renderChooseLevel(g);
            }

        } else { //draw in-game menu bar and health bar
            //draw menu-bar every time because of health bar
            g.drawImage(backgroundImage, 0, 0, 1280, 50, 0, 0, backgroundImage.getWidth(), 50, null);
            
            g.setColor(Color.WHITE);
            g.drawRect(RETURN_BUTTON_X, RETURN_BUTTON_Y, BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Return", RETURN_BUTTON_X + 20, RETURN_BUTTON_Y + 35);

           
            // Draw hearts based on current health
            for (int i = 0; i < Player.health; i++) {
                g.drawImage(heart, 1220 - (i * 30), 0, HEART_WIDTH, HEART_HEIGHT, null);
            }
            
            // draw relfections
            g.drawImage(reflection, 950, 0, HEART_WIDTH, HEART_HEIGHT, null);
            g.drawString(String.valueOf(Player.reflections), 910, 25); // Position the number near the reflection image
            g.drawString(String.valueOf("X"), 930, 25); // Position the number near the reflection image

            g.drawString(String.valueOf(game.getLifeCount()), 810, 25); // Position the number near the reflection image
        }
    }

   
    public void renderStartMenu(Graphics g){
            // Render menu text and buttons
        
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            
            g.drawString("Start Game", PLAY_BUTTON_X + 20, PLAY_BUTTON_Y + 35);

            g.drawString("Quit", PLAY_BUTTON_X + 20, PLAY_BUTTON_Y + 135);
    }

    // render what level to choose
    public void renderChooseLevel(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));

        g.drawString("Return", RETURN_BUTTON_X + 20, RETURN_BUTTON_Y + 35);

        for (int i = 1;i<6;i++){
            g.drawImage(levelButton, SELECT_LEVEL_WIDTH * i, PLAY_BUTTON_Y, SELECT_LEVEL_WIDTH, SELECT_LEVEL_HEIGHT, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();


        if (game.getGameState() == GameState.MENU) {
             // check if quit button
            if (mx >= PLAY_BUTTON_X && mx <= PLAY_BUTTON_X + BUTTON_WIDTH && my >= PLAY_BUTTON_Y + 100 && my <= PLAY_BUTTON_Y + 100 + BUTTON_HEIGHT) {
                System.exit(0);
            } 


            // Check if "Start Game" is clicked
            if (mx >= PLAY_BUTTON_X && mx <= PLAY_BUTTON_X + BUTTON_WIDTH && my >= PLAY_BUTTON_Y && my <= PLAY_BUTTON_Y + BUTTON_HEIGHT) {
                startGameClicked = true;
                return; // ensures that a click on Start Game doesn't click any level behind it automatically!
                
            }
            
            //return button in menu
            if (mx >= RETURN_BUTTON_X && mx <= BUTTON_WIDTH && my >= RETURN_BUTTON_Y && my <= RETURN_BUTTON_HEIGHT) {  
                game.setGameState(GameState.MENU); // switch to menu
                startGameClicked = false; // in case button was clicked in the menu
                return;
            }

            // level 1 clicked
            if (mx >= SELECT_LEVEL_WIDTH && mx <= 2 * SELECT_LEVEL_WIDTH && my >= PLAY_BUTTON_Y && my <= PLAY_BUTTON_Y + SELECT_LEVEL_HEIGHT && startGameClicked) {
                // game.poziom1();
                game.setGameState(GameState.L1);
                game.startLevel(GameState.L1);
                startGameClicked = false;
                return;
                
            }
            // level 2 clicked
            if (mx >= 2 * SELECT_LEVEL_WIDTH && mx <= 4 * SELECT_LEVEL_WIDTH && my >= PLAY_BUTTON_Y && my <= PLAY_BUTTON_Y + SELECT_LEVEL_HEIGHT && startGameClicked) {
                // game.poziom2();
                game.setGameState(GameState.L2);
                game.startLevel(GameState.L2);
                startGameClicked = false;
                return;
                
            }
        
        } else {
            // return button in game
            if (mx >= RETURN_BUTTON_X && mx <= BUTTON_WIDTH && my >= RETURN_BUTTON_Y && my <= RETURN_BUTTON_HEIGHT) {  
                game.setGameState(GameState.MENU); // switch to menu
                return;
            }
        }
    }


    
    
}
