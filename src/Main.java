import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;

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
    private Thread menuThread;
    private Thread gameThread;

    public Main() {
        new GameWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "HopeSkill", this);
        handler = new ObjectHandler();
        this.addKeyListener(new Inputs(handler));
    }

    public synchronized void startMenu() {
        menuThread = new Thread(() -> {
            Menu menu = new Menu(this);
            this.addMouseListener(menu);
            while (state == GameState.MENU) {
                renderMenu();
                try {
                    Thread.sleep(16); // Approx. 60 FPS
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            stopMenu();
            //startGame();
        }, "MenuThread");
        menuThread.start();
    }

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
    // this function will run
    // public synchronized void startGame() {
    //     state = GameState.GAME;
    //     poziom1();
    //     gameThread = new Thread(this, "GameThread");
    //     gameThread.start();
    // }

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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Menu", 400, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawRect(400, 300, 200, 50);
        g.drawString("Start Game", 420, 335);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawRect(100, 100, 200, 50);
        g.drawString("Level 2", 120, 135);
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
        g.fillRect(0, 0, getWidth(), getHeight());
        handler.render(g);
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
        gameThread = new Thread(this, "Level2");
        gameThread.start();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startMenu();
    }
}
