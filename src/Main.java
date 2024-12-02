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

    private ObjectHandler handler;

    public Main(){
        new GameWindow(window_width,window_height,"HopeSkill",this);
        //Player player = new Player(30,30,(float) window_width,(float) window_height, 1);
        handler = new ObjectHandler();
        Player player = new Player(32, 32, 1, handler);
        handler.addObj(player);
    }

    public static void main(String[] args) {
        // initialize game
        Main game = new Main();
        //Player player = new Player(30,30, 1);
        if (isRunning){
            //TODO: game menu and logic
            //game.paint();
            
        }
    }

    //tick through all game objects
    private void tick(){
        handler.tick();
    }

    // render function (I aassume it renders current map)
    public void paint(Graphics g){

        //somehow there will be map
        // g.setColor(Color.RED);
        // g.fillRect(50, 50, 100, 100);

        handler.render(g);
    }

    
    public static int getWindowHeight(){
        return window_height;
    }

    public static int getWindowWidth(){
        return window_width;
    }
    //Player player = new Player(30,30,(float) window_width,(float) window_height, 1);

}