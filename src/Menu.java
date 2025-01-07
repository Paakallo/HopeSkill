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

    public Menu(Main game) {
        this.game = game;

        try {
            // Load the background image
            backgroundImage = ImageIO.read(new File("resources/background.png"));
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
                // Render menu text and buttons
                g.setColor(Color.WHITE);
                // g.setFont(new Font("Arial", Font.BOLD, 50));
                // g.drawString("Game Menu", 400, 200);
                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.drawRect(510, 300, 200, 50);
                g.drawString("Start Game", 530, 335);

                g.drawRect(100, 100, 200, 50);
                g.drawString("Level 2", 120, 135);
            } else {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.drawRect(510, 300, 200, 50);
                g.drawString("1", 530, 335);

            }

        }

        else {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, 200, 50);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();


        if (game.getGameState() == GameState.MENU) {
            // Check if "Start Game" is clicked
            if (mx >= 400 && mx <= 600 && my >= 300 && my <= 350) {
                startGameClicked = true;
                
            }

            if (mx >= 100 && mx <= 300 && my >= 100 && my <= 150) {
                game.poziom2();
                
            }
            // level 1 clicked
            if (mx >= 0 && mx <= 200 && my >= 0 && my <= 350 && startGameClicked) {
                game.poziom1();
                startGameClicked = false;
                
            }
        } else {
            if (mx >= 0 && mx <= 200 && my >= 0 && my <= 50) {  
                game.setGameState(GameState.MENU); // switch to menu
            }

        }
    }
}
