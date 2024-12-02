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
        new GameWindow(window_width,window_height,"HopeSkill",this);
        Player player = new Player(30,30,(float) window_width,(float) window_height, 1);
    }

    public static void main(String[] args) {
        // initialize game
        Main game = new Main();
        Player player = new Player(30,30,(float) window_width,(float) window_height, 1);
        if (isRunning){
            //TODO: game menu and logic
            //game.paint();
            
        }
    }

    // render function (I aassume it renders current map)
    public void paint(Graphics g){

        //somehow there will be map
        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 100);
    }

    
    public static int getWindowHeight(){
        return window_height;
    }

    public static int getWindowWidth(){
        return window_width;
    }
    Player player = new Player(30,30,(float) window_width,(float) window_height, 1);



class KeysPressed extends KeyAdapter{

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                //jump
                break;
            //left
            case KeyEvent.VK_A:
                float x = player.getX();
                player.setX(player.getX() - player.getVel_x());
                break;
            //button for action
            case KeyEvent.VK_S:
                break;
            //right    
            case KeyEvent.VK_D:
                player.setX(player.getX() + player.getVel_x());
                break;
            default:
                break;
        }
    }          
}

}