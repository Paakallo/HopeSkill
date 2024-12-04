import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;



public class Main extends Canvas implements Runnable {

    
    private static int window_width = 960;
    private static int window_height = 720;

    static boolean isRunning;

    private ObjectHandler handler;
    
    private Thread thread;

    public Main(){
        new GameWindow(window_width,window_height,"HopeSkill",this);
        handler = new ObjectHandler();
        handler.setPlayer(new Player(32, 32, 1, handler));
        this.addKeyListener(new Inputs(handler));        
    }

    public synchronized void start() {
        isRunning = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        try {
            isRunning = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // initialize game
        Main game = new Main();
        game.start();

    }

    //this is a game loop
    public void run() {
        final double nsc = 1000000000.0 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
		
        while (isRunning){
            long now = System.nanoTime();
		    delta = delta + (now - lastTime) / nsc;
		    lastTime = now;
            // while (delta >1){
            //     tick();
            // }
            tick();
            render();
        }
        stop();
    }

    //tick through all game objects
    private void tick(){
        handler.tick();
    }

    // render function (I aassume it renders current map)
    public void render(){
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        
        //g = getGraphics();
        //somehow there will be map
        //g.setColor(Color.RED);
        //g.fillRect(50, 50, 100, 100);

        handler.render(g);
        g.dispose();
        buf.show();
    }

    
    public static int getWindowHeight(){
        return window_height;
    }

    public static int getWindowWidth(){
        return window_width;
    }

}