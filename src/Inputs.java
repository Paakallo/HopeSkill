import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Handles keyboard input for the game "HopeSkill".
 * <p>
 * This class listens for key presses and releases, interpreting them as actions
 * for the player character such as movement, jumping, or performing other in-game
 * interactions.
 * </p>
 *
 */
public class Inputs extends KeyAdapter{
    /**
     * Tracks the state of movement keys (W, A, S, D).
     */
    private boolean[] keys = new boolean[4];
    private ObjectHandler handler;


    /**
     * Constructs a new Inputs instance for handling keyboard input.
     *
     * @param handler the {@code ObjectHandler} responsible for managing game objects
     */
    public Inputs(ObjectHandler handler){
        this.handler = handler;
    }


/**
     * Invoked when a key is pressed.
     * <p>
     * Handles player actions such as jumping, moving left or right,
     * performing in-game actions, and exiting the game.
     * </p>
     *
     * @param e the key event
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //jump
        if (key == KeyEvent.VK_W){
                if (!handler.getPlayer().getJump()){

                    handler.getPlayer().setVel_y(-90);

                    keys[0] = true;
                    handler.getPlayer().setJump(true);
                    Main.playSound("sound/Mario-jump-sound.wav");
                }
            }
            //left
            if (key == KeyEvent.VK_A){
                handler.getPlayer().setVel_X(-5);
                keys[1] = true;
               }
            //right
            if (key == KeyEvent.VK_D){
                handler.getPlayer().setVel_X(5);
                keys[2] = true;
            }
            //button for action
            if (key == KeyEvent.VK_S){
                Player.onPoint = true;
            }
            //
            if (e.getKeyCode() == KeyEvent.VK_E){
                if (Player.reflections >= 0)
                    Player.health+=1;
                    Player.reflections-=1;
            }

                
            if (key == KeyEvent.VK_ESCAPE){
                System.exit(0);
            }
    }
    

    /**
     * Invoked when a key is released.
     * <p>
     * Updates the state of movement keys and resets player actions as needed.
     * </p>
     *
     * @param e the key event
     */
    public void keyReleased(KeyEvent e){
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_W){
                keys[0] = false;

        }
            
        if (key == KeyEvent.VK_A){
                keys[1] = false;
        }

        if (key == KeyEvent.VK_D){
                keys[2] = false;
                
        }

        if (key == KeyEvent.VK_S){
            Player.onPoint = false;
        }
            
        if (!keys[1] && !keys[2]){
            handler.getPlayer().setVel_X(0);
        }
                
        
    }
}