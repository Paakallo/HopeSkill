import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    L5,
    GAME_OVER
}

public class Main extends Canvas implements Runnable {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    private GameState state = GameState.MENU;
    private GameState currLevel = GameState.MENU;

    private ObjectHandler handler;
    private Camera camera;
    private MapLoader mapLoader;

    //threads
    private Thread menuThread;
    private Thread gameThread;

    private Menu menu;

    private int lifeCount = 3;

    private BufferedImage gameBackground;

    public Main() {
        new GameWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "HopeSkill", this);
        handler = new ObjectHandler();
        this.addKeyListener(new Inputs(handler));

        mapLoader = new MapLoader(handler);

        menu = new Menu(this);
        this.addMouseListener(menu);

        camera = new Camera(0, 0); // Initialize camera at (0, 0)

        try {
            gameBackground = ImageIO.read(new File("resources/background_lake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void startMenu() {
        state = GameState.MENU;
        menuThread = new Thread(() -> {
            // for now True, as it will render only parts of menu depending on GameState
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
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (state != GameState.MENU && state != GameState.GAME_OVER) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick(); // Update game logic
                delta--;
            }
            
            renderGame(); // Render frame
            frames++;

            //end game after death
            if (Player.health == 0){
                currLevel = state;
                lifeCount--;
                state = GameState.GAME_OVER; // activates restart function in stopGame
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
            // lock on 60 FPS somehow makes rendering unstable that's why it is commented
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
            Player.health = 5;
            // without these 2 lines, it would work like a continue button :D

            if (state == GameState.GAME_OVER){
                if (lifeCount < 0){
                    state = GameState.MENU; //for now only this
                } else {
                    state = currLevel;
                    startLevel(currLevel);
                }
            } else {
                    lifeCount = 3;
            }
        }
    }

    private void tick() {
        if (state != GameState.MENU && state != GameState.GAME_OVER) {
            handler.tick();

            // Update the camera to follow the player
            if (handler.getPlayer() != null) {
                camera.tick(handler.getPlayer());
            }
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

        if (state == GameState.GAME_OVER){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", WINDOW_WIDTH / 2 - 150, WINDOW_HEIGHT / 2);
        } else {
        
            // g.setColor(Color.BLACK);
            // g.fillRect(0, 51, getWidth(), getHeight()); //give a place for menu
            g.drawImage(gameBackground, 0, 51, getWidth(), getHeight(), null);

        
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(-camera.getX(), -camera.getY()); // Shift the view based on camera
        
            handler.render(g2d); // Render objects relative to camera position
        
            g2d.translate(camera.getX(), camera.getY()); // Reset translation
            g.dispose();
            buf.show();
        }
    }
    

    void startLevel(GameState selLevel){
        
        mapLoader.loadMap(selLevel);
        currLevel = GameState.MENU; //reset currentLevel
        
        gameThread = new Thread(this, "Game");
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
