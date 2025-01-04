import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

enum GameState {
    MENU,
    GAME
}

public class Main extends Canvas implements Runnable {
    private static int window_width = 1280;
    private static int window_height = 720;
    static boolean isRunning;
    private GameState state = GameState.GAME;

    private ObjectHandler handler;
    private Thread thread;
    private Menu menu;

    public Main() {
        new GameWindow(window_width, window_height, "HopeSkill", this);
        handler = new ObjectHandler();
        menu = new Menu(this); // Initialize menu
        this.addMouseListener(menu); // Add mouse listener for menu
        this.addKeyListener(new Inputs(handler));
    }

    public synchronized void start() {
        isRunning = true;
        startGame();
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
        Main game = new Main();
        game.start();
    }

    public void run() {
        while (isRunning) {
            tick();
            render();
        }
        stop();
    }

    private void tick() {
        if (state == GameState.GAME) {
            handler.tick();
        }
    }

    public void render() {
        BufferStrategy buf = this.getBufferStrategy();
        if (buf == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buf.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (state == GameState.MENU) {
            menu.render(g);
        } else if (state == GameState.GAME) {
            handler.render(g);
        }

        g.dispose();
        buf.show();
    }

    public void startGame() {
        state = GameState.GAME;
        handler.setPlayer(new Player(32, 32, 1, handler));
        for (int i = 0; i < 20; i++) {
            handler.addObj(new Block(i * 16, 320, 32, 32, 1));
            handler.addObj(new Block(i * 16, 120, 32, 32, 1));
        }
    }

    public static int getWindowHeight(){
        return window_height;
    }

    public static int getWindowWidth(){
        return window_width;
    }
}
