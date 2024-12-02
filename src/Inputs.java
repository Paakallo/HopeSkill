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
            case KeyEvent.VK_W:
                //jump
                break;
            //left
            case KeyEvent.VK_A:
                
                player.setX(player.getX() - player.getVel_x());
                break;
            //button for action
            case KeyEvent.VK_S:
                break;
            //right    
            case KeyEvent.VK_D:
                player.setX(player.getX() + player.getVel_x());
                break;
            default:
                break;
        }
    }          
}