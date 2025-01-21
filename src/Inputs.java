import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Inputs extends KeyAdapter{

    private boolean[] keys = new boolean[4];
    private ObjectHandler handler;

    public Inputs(ObjectHandler handler){
        this.handler = handler;
    }

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
            
                
            if (key == KeyEvent.VK_ESCAPE){
                System.exit(0);
            }
    }
    
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