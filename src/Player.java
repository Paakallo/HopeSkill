import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player extends GameObject{
    
    private ObjectHandler handler;
    static float height = 32;
    static float width = 16;
    private boolean jump = false;

    public Player(float x, float y, int scale, ObjectHandler handler) {
        super(x, y, ObjectId.Player,width, height, scale);
        this.handler = handler;
        //setVel_X(5);
        //setVel_y(5);
    }

    void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect((int)getX(), (int)getY(), (int) width, (int) height);
        showBounds(g);
    }

    public void tick(){
        //update position every tick
        setX(getVel_x()+getX());
        setY(getVel_y()+getY());
    }

    public Rectangle getBounds(){
        return new Rectangle((int) (getX()+getWidth()/2-getWidth()/4), 
        (int) (getY()+getHeight()/2), 
        (int) getWidth()/2, 
        (int) getHeight()/2);
    }

    public Rectangle getBoundsTop(){
        return new Rectangle((int) (getX()+getWidth()/2-getWidth()/4), 
        (int) getY(), 
        (int) getWidth()/2, 
        (int) getHeight()/2);
    }

    public Rectangle getBoundsRight(){
        return new Rectangle((int) (getX()+getWidth()/2 - 5), 
        (int) getY() + 5, 
        5, 
        (int) getHeight() - 10);
    }

    public Rectangle getBoundsLeft(){
        return new Rectangle((int) getX(), 
        (int) getY() + 5, 
        5, 
        (int) getHeight() - 10);
    }

    private void showBounds(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());
    }

    public boolean hasJumped(){
        return jump;
    }

    public void setJump(boolean jump){
       this.jump = jump;
    }
}
