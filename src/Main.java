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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Represents the various states of the game "HopeSkill".
 * <p>
 * The {@code GameState} enum is used to define and manage the different phases
 * or states the game can be in. These states determine the game's behavior,
 * rendering, and user interactions at any given time.
 * </p>
 *
 * <p>
 * Enum constants:
 * </p>
 * <ul>
 *     <li>{@link #MENU}: Represents the main menu of the game.</li>
 *     <li>{@link #L1}, {@link #L2}, {@link #L3}, {@link #L4}, {@link #L5}: Represent the playable levels of the game.</li>
 *     <li>{@link #GAME_OVER}: Indicates that the player has lost all lives or failed to complete a level.</li>
 *     <li>{@link #VICTORY}: Represents the state when the player successfully completes a level.</li>
 * </ul>
 *
 * <p>
 * Usage:
 * </p>
 * <p>
 * The game transitions between these states during gameplay. For example, the
 * game might start in {@code MENU}, transition to {@code L1} for level 1 gameplay,
 * and move to {@code GAME_OVER} if the player fails or {@code VICTORY} if the level is completed.
 * </p>
 *
 */
enum GameState {

    /**
     * Represents the main menu of the game.
     */
    MENU,

    /**
     * Represents level 1 of the game.
     */
    L1,

    /**
     * Represents level 2 of the game.
     */
    L2,

    /**
     * Represents level 3 of the game.
     */
    L3,

    /**
     * Represents level 4 of the game.
     */
    L4,

    /**
     * Represents level 5 of the game.
     */
    L5,

    /**
     * Indicates that the game is over, either due to the player losing all lives
     * or failing to complete a level.
     */
    GAME_OVER,

    /**
     * Indicates that the player successfully completed a level or achieved victory.
     */
    VICTORY;
}

/**
 * The main class for the game "HopeSkill".
 * <p>
 * This class initializes the game, manages the main game loop,
 * handles transitions between game states (e.g., menu, gameplay, game over),
 * and provides rendering and game logic updates.
 * </p>
 *

 */
public class Main extends Canvas implements Runnable {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;


/**
 * The current game state and the current level being played.
 * <p>
 * {@code state} represents the overall state of the game, such as whether the game is in the menu,
 * active gameplay, game over, or victory. {@code currLevel} keeps track of the specific level
 * being played and is used to restore the game state after transitions like game over.
 * </p>
 */
    private GameState state = GameState.MENU;
    private GameState currLevel = GameState.MENU;


/**
 * Core components for managing game objects, camera view, and map loading.
 * <p>
 * {@code handler} manages all the game objects, including their creation, updates, and removal.
 * {@code camera} handles the view, ensuring the player's position is centered within the game world.
 * {@code mapLoader} is responsible for loading and setting up the maps for each level.
 * </p>
 */    
    private ObjectHandler handler;
    private Camera camera;
    private MapLoader mapLoader;

    //threads
    private Thread menuThread;
    private Thread gameThread;

    private Menu menu;

    private int lifeCount = 3;

    private BufferedImage gameBackground;


    /**
     * Constructs the main game instance and initializes its components.
     * <p>
     * This constructor performs the following actions:
     * </p>
     * <ul>
     *     <li>Creates a new game window with the specified dimensions and title.</li>
     *     <li>Initializes the {@code ObjectHandler} to manage game objects.</li>
     *     <li>Attaches key and mouse listeners for player input and menu interactions.</li>
     *     <li>Initializes the {@code MapLoader} to load game levels and maps.</li>
     *     <li>Sets up the camera to follow the player during gameplay.</li>
     *     <li>Loads the background image from a specified file path.</li>
     * </ul>
     * <p>
     * Any errors during the loading of the background image are caught and logged.
     * </p>
     */
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


/**
 * Starts the menu thread and initializes the menu rendering process.
 * <p>
 * This method sets the game state to {@code GameState.MENU} and starts a new thread
 * named "MenuThread". The thread continuously renders the menu at approximately 60 frames
 * per second until interrupted. It ensures smooth updates of the menu screen.
 * Menu thread is always running, if {@code state} is MENU it renders menu,
 * otherwise it renders menu bar
 * </p>
*/
    public synchronized void startMenu() {
        state = GameState.MENU;
        menuThread = new Thread(() -> {
            playBackgroundMusic("sound/Music-Overworld_Day.wav");
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


    /**
     * The main game loop.
     * <p>
     * This method handles all core functionality of the game, ensuring continuous updates and rendering
     * at a stable frame rate. The game loop performs the following:
     * </p>
     * <ul>
     *     <li>Tracks elapsed time to manage consistent updates using the {@code tick} method.</li>
     *     <li>Renders the game state to the screen using {@code renderGame}.</li>
     *     <li>Handles transitions between game states such as game over or victory.</li>
     *     <li>Maintains a frame counter to calculate and display frames per second (FPS).</li>
     * </ul>
     * <p>
     * The loop runs as long as the game is in an active state (e.g., not in the menu or game over). If a
     * termination condition is met, such as the player losing all lives, the game transitions to the
     * appropriate state and cleans up resources using {@code stopGame}.
     * </p>
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 45.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (state != GameState.MENU && state != GameState.GAME_OVER && state != GameState.VICTORY) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick(); // Update game logic
                delta--;
            }
            
            renderGame(); // Render frame
            frames++;

            // game over after death
            if (Player.health == 0){
                currLevel = state;
                lifeCount--;
                state = GameState.GAME_OVER; // activates restart function in stopGame
            }
            // player finishes level
            if (handler.getEndLevel()){
                currLevel = state;
                state = GameState.VICTORY;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }

            try {
                Thread.sleep(16); // Approx. 60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        //display Game Over after dying for 2 sec
        if (state == GameState.GAME_OVER){
            renderGame();
            long timer1 = System.currentTimeMillis();
            while (System.currentTimeMillis() - timer1 < 2000) {
            }
        }
        stopGame();
    }


/**
     * Stops the game thread and handles state transitions.
     * <p>
     * This method ensures a clean termination of the game thread and transitions
     * the game to an appropriate state based on the outcome (e.g., game over or victory).
     * It performs cleanup by removing all game objects and resetting the player's health.
     * </p>
     * <p>
     * If the player has lost all lives, the game transitions back to the main menu. Otherwise,
     * it restarts the current level or progresses to the next level, depending on the state.
     * </p>
*/
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
                    lifeCount = 3;
                } else {
                    state = currLevel;
                    startLevel(currLevel);
                }
            } else if (state == GameState.VICTORY){
                    state = getNextState(currLevel);
                    System.out.println("NEXT STATE: "+ state);
                    handler.setEndLevel(false);
                    startLevel(state);
            } else {
                    lifeCount = 3;
            }
        }
    }


    /**
     * Updates game logic of each object
     */
    private void tick() {
        if (state != GameState.MENU && state != GameState.GAME_OVER) {
            handler.tick();

            // Update the camera to follow the player
            if (handler.getPlayer() != null) {
                camera.tick(handler.getPlayer());
            }
        }
    }


/**
     * Renders the menu screen.
     * <p>
     * This method is responsible for rendering all visual elements of the game's menu.
     * It uses a double-buffering strategy to ensure smooth animations and updates.
     * The {@code Menu} object handles the actual rendering of menu components.
     * </p>
     * <p>
     * If the buffer strategy is not initialized, it creates a triple-buffer strategy
     * to optimize rendering performance.
     * </p>
*/
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


    /**
     * Renders the game screen.
     * <p>
     * This method handles rendering the main game view, including the background,
     * game objects, and camera adjustments. It ensures that all graphical elements
     * are displayed correctly relative to the player's position.
     * </p>
     * <p>
     * If the game state is {@code GameState.GAME_OVER}, it displays a "GAME OVER"
     * message centered on the screen. Otherwise, it draws the game background,
     * adjusts the view based on the camera, and renders all active game objects
     * through the {@code ObjectHandler}.
     * </p>
     * <p>
     * Similar to {@code renderMenu}, a triple-buffering strategy is used to optimize
     * performance and reduce visual artifacts during rendering.
     * </p>
     */
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
        
            //give a place for menu
            g.drawImage(gameBackground, 0, 51, getWidth(), getHeight(), null);

        
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(-camera.getX(), -camera.getY()); // Shift the view based on camera
        
            handler.render(g2d); // Render objects relative to camera position
        
            g2d.translate(camera.getX(), camera.getY()); // Reset translation
            g.dispose();
            buf.show();
        }
    }
    

/**
     * Starts the specified game level.
     * <p>
     * This method loads the specified level using {@code MapLoader} and prepares
     * the game state for active gameplay. The current level is reset to {@code GameState.MENU}
     * before transitioning to the selected level. A new game thread is started to handle
     * the main game loop.
     * </p>
     *
     * @param selLevel the level to start
*/
    void startLevel(GameState selLevel){
        
        mapLoader.loadMap(selLevel);

        currLevel = GameState.MENU; //reset currentLevel
        state = selLevel;

        gameThread = new Thread(this, "Game");
        gameThread.start();
    }



    public static void main(String[] args) {
        Main main = new Main();
        
        main.startMenu();
    }


/**
 * Retrieves the next game state based on the current state.
 *
 * @param current the current game state
 * @return the next game state
 */
    public GameState getNextState(GameState current) {
        GameState[] values = GameState.values();
        int nextIndex = (current.ordinal() + 1) % values.length;
        return values[nextIndex];
    }

    GameState getGameState(){
        return state;
    }
    
    void setGameState(GameState nState){
        state=nState;  

    }

    int getLifeCount(){
        return lifeCount;
    }
    
    void setLifeCount(int nlife){
        lifeCount=nlife;  
    }



    public static void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    

    public void playBackgroundMusic(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
