import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu extends MouseAdapter {
    public Main game;
    public boolean startGameClicked = false;
    //graphics
    private BufferedImage backgroundImage;
    private BufferedImage playButton;

    static int BUTTON_WIDTH = 200;
    static int BUTTON_HEIGHT = 100;

    int PLAY_BUTTON_X = 510;
    int PLAY_BUTTON_Y = 300;

    // static int BUTTON_WIDTH = 200;
    // static int BUTTON_HEIGHT = 100;

    int RETURN_BUTTON_X = 0;
    int RETURN_BUTTON_Y = 0;

    int RETURN_BUTTON_HEIGHT = 50;

    public Menu(Main game) {
        this.game = game;

        try {
            // Load the background image
            backgroundImage = ImageIO.read(new File("resources/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Load the background image
            playButton = ImageIO.read(new File("resources/play_button.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void render (Graphics g){

        if (game.getGameState() == GameState.MENU) {
            // Draw the background image
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, game.getWidth(), game.getHeight(), null);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, game.getWidth(), game.getHeight());
            }

            if (!startGameClicked) {
                renderStartMenu(g);
        
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 30));

                g.drawRect(510, 300, 200, 50);
                g.drawString("1", 530, 335);

                g.drawRect(100, 100, 200, 50);
                g.drawString("Level 2", 120, 135);

                g.drawString("Return", RETURN_BUTTON_X + 20, RETURN_BUTTON_Y + 35);

            }

        } else { //draw in-game menu bar
            g.setColor(Color.WHITE);
            g.drawRect(RETURN_BUTTON_X, RETURN_BUTTON_Y, BUTTON_WIDTH, RETURN_BUTTON_HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Return", RETURN_BUTTON_X + 20, RETURN_BUTTON_Y + 35);
        }
    }

    /** 
    * render start menu
    */
    // TODO use good font
    public void renderStartMenu(Graphics g){
        // Render menu text and buttons
        
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            // g.drawRect(510, 300, 200, 50);
            g.drawString("Start Game", PLAY_BUTTON_X + 20, PLAY_BUTTON_Y + 35);

            g.drawString("Quit", PLAY_BUTTON_X + 20, PLAY_BUTTON_Y + 135);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        
        // check if quit button
        if (mx >= PLAY_BUTTON_X && mx <= PLAY_BUTTON_X + BUTTON_WIDTH && my >= PLAY_BUTTON_Y + 100 && my <= PLAY_BUTTON_Y + 100 + BUTTON_HEIGHT) {
            
            System.exit(0);
            
        } 

        if (game.getGameState() == GameState.MENU) {
            // Check if "Start Game" is clicked
            if (mx >= PLAY_BUTTON_X && mx <= PLAY_BUTTON_X + BUTTON_WIDTH && my >= PLAY_BUTTON_Y && my <= PLAY_BUTTON_Y + BUTTON_HEIGHT) {
                startGameClicked = true;
                
            }

            if (mx >= 100 && mx <= 300 && my >= 100 && my <= 150 && startGameClicked) {
                game.poziom2();
                startGameClicked = false;
                
            }
            // level 1 clicked
            if (mx >= 0 && mx <= 200 && my >= 0 && my <= 350 && startGameClicked) {
                game.poziom1();
                startGameClicked = false;
                
            }
        } else {
            // return button
            if (mx >= RETURN_BUTTON_X && mx <= BUTTON_WIDTH && my >= RETURN_BUTTON_Y && my <= RETURN_BUTTON_HEIGHT) {  
                game.setGameState(GameState.MENU); // switch to menu
            }

        }
    }
}
