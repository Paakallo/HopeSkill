import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Inputs extends KeyAdapter{

    private boolean[] keys = new boolean[4];
    private ObjectHandler handler;

    public Inputs(ObjectHandler handler){
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //jump
            case KeyEvent.VK_W:
                if (!handler.getPlayer().hasJumped()){
                    handler.getPlayer().setVel_y(-15);
                    keys[0] = true;
                    handler.getPlayer().setJump(true);
                }
                break;
            //left
            case KeyEvent.VK_A:
                handler.getPlayer().setVel_X(-8);
                keys[1] = true;
                break;
            //right
            case KeyEvent.VK_D:
                handler.getPlayer().setVel_X(8);
                keys[2] = true;
                break;
            //button for action
            case KeyEvent.VK_S:
                break;
                
            case KeyEvent.VK_ESCAPE:
                System.exit(0);

            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keys[0] = false;
                break;
            
            case KeyEvent.VK_A:
                keys[1] = false;
                break;

            case KeyEvent.VK_D:
                keys[2] = false;
                break;
            
            default:
                if (!keys[1] && !keys[2]){
                    handler.getPlayer().setVel_X(0);
                }
                break;
        }
    }
}