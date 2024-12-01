import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        Player player = new Player(30,30,(float) window_width,(float) window_height, 1);
        if (isRunning){
            //TODO: game menu and logic

            
        }
    }

    // render function
    public void render(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 100);
    }

    
    public static int getWindowHeight(){
        return window_height;
    }

    public static int getWindowWidth(){
        return window_width;
    }


}

class KeysPressed extends KeyAdapter{

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                
                break;
            case KeyEvent.VK_A:
                break;
            case KeyEvent.VK_S:
                break;
            case KeyEvent.VK_D:
                break;
            default:
                break;
        }
    }          
}

