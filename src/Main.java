import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.events.MouseEvent;

enum GameState {
    MENU,
    GAME,
    L1,
    L2,
    L3,
    L4,
    L5
}

public class Main extends Canvas implements Runnable {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;

    private GameState state = GameState.MENU;

    private ObjectHandler handler;
    //threads
    private Thread menuThread;
    private Thread gameThread;

    private Menu menu;

    public Main() {
        new GameWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "HopeSkill", this);
        handler = new ObjectHandler();
        this.addKeyListener(new Inputs(handler));

        menu = new Menu(this);
        this.addMouseListener(menu);
    }

    public synchronized void startMenu() {
        state = GameState.MENU;
        menuThread = new Thread(() -> {
            // for now True, as it will render only parts dependent on GameState
            while (true) {
                renderMenu();
                try {
                    Thread.sleep(16); // Approx. 60 FPS
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            // stopMenu();
            
        },"MenuThread");
        menuThread.start();
    }
    // function for debug purposes
    private synchronized void stopMenu() {
        if (menuThread != null && menuThread.isAlive()) {
            menuThread.interrupt();
            try {
                menuThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        while (state != GameState.MENU) {
            tick();
            renderGame();
            try {
                Thread.sleep(16); // Approx. 60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        stopGame();
    }


    private synchronized void stopGame() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // free all the resources
            handler.removePlayer();
            handler.removeObjects();
            // without these 2 lines, it would work like a continue button :D
        }
    }

    private void tick() {
        if (state != GameState.MENU) {
            handler.tick();
        }
    }


    private void renderMenu() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = buf.getDrawGraphics();

        menu.render(g);

        g.dispose();
        buf.show();
    }


    private void renderGame() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = buf.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 100, getWidth(), getHeight()); //give a place for menu
        handler.render(g); //renders all level objects
        g.dispose();
        buf.show();
    }


    //poziomy
    void poziom1(){
        state = GameState.L1;
        handler.setPlayer(new Player(32, 32, 1, handler));
        for (int i = 0; i < 20; i++) {
            handler.addObj(new Block(i * 16, 320, 32, 32, 1));
            handler.addObj(new Block(i * 16, 120, 32, 32, 1));
        }

        gameThread = new Thread(this, "Level1");
        gameThread.start();
    }


    void poziom2(){
        state = GameState.L2;
        handler.setPlayer(new Player(32, 32, 1, handler));
        for (int i = 0; i < 20; i++) {
            handler.addObj(new Block(i * 16, 320, 32, 32, 1));
            //handler.addObj(new Block(i * 16, 120, 32, 32, 1));
        }

        // MapLoader mapLoader = new MapLoader(handler);
        // mapLoader.loadMap("maps/test.json");

        gameThread = new Thread(this, "Level2");
        gameThread.start();
    }

    public static void main(String[] args) {
        Main main = new Main();
        
        main.startMenu();
    }


    GameState getGameState(){
        return state;
    }
    
    void setGameState(GameState nState){
        state=nState;  

    }
}
