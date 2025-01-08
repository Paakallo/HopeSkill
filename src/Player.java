import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Iterator;


public class Player extends GameObject{
    
    private ObjectHandler handler;
    static float height = 32;
    static float width = 16;
    private boolean jump = false;

    public Player(float x, float y, int scale, ObjectHandler handler) {
        super(x, y, ObjectId.Player,width, height, scale);
        this.handler = handler;
    }

    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect((int)getX(), (int)getY(), (int) width, (int) height);
        showBounds(g);
    }

    public void tick(){
        //update position every tick

        setX(getVel_x()+getX());
        setY(getVel_y()+getY());
        gravity();
        collision();
    }

    private void collision(){
        Iterator<GameObject> it = handler.getObjects().iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();

            // to be expanded for different objects
            if (obj.getId() == ObjectId.Block){
                if (getBounds().intersects(obj.getBounds())){
                    setY(obj.getY()-getHeight());
                    setVel_y(0);
                    jump = false;
                }

                if (getBoundsTop().intersects(obj.getBounds())){
                    setY(obj.getY()+obj.getHeight());
                    setVel_y(0);
                }

                if (getBoundsRight().intersects(obj.getBounds())){
                    setX(obj.getX()-getWidth());
                }

                if (getBoundsLeft().intersects(obj.getBounds())){
                    setX(obj.getX() + getWidth());
                }
            }
        }
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
    // function for debug purposes
    private void showBounds(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());
    }

    public boolean getJump(){
        return jump;
    }

    public void setJump(boolean jump){
       this.jump = jump;
    }
}
