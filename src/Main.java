import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;


//this is a game loop
public class Main extends Canvas {

    
    private static int window_width = 960;
    private static int window_height = 720;

    static boolean isRunning = true;

    public Main(){
        super();
        new GameWindow(window_width,window_height,"HopeSkill",this);
    }

    public static void main(String[] args) {
        // initialize game
        Main game = new Main();

        if (isRunning){
            //TODO: game menu and logic

            
        }
    }

    // render function
    public void render(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 100);
    }

    

}


