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
    public boolean clicked = false;
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
        
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, game.getWidth(), game.getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, game.getWidth(), game.getHeight());
        }

        // Render menu text and buttons
        g.setColor(Color.WHITE);
        // g.setFont(new Font("Arial", Font.BOLD, 50));
        // g.drawString("Game Menu", 400, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawRect(510, 300, 200, 50);
        g.drawString("Start Game", 530, 335);

        g.drawRect(100, 100, 200, 50);
        g.drawString("Level 2", 120, 135);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        // Check if "Start Game" is clicked
        if (mx >= 400 && mx <= 600 && my >= 300 && my <= 350) {
            game.poziom1();
            //game.stopThread();
            //clicked = true;
        }
        if (mx >= 100 && mx <= 300 && my >= 100 && my <= 150){
            game.poziom2();
            //game.stopThread();
            //clicked = true;
        }
    }
}
