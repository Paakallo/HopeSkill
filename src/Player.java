import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player extends GameObject{
    private boolean[] mov_keys = new boolean[4];

    static float height = 32;
    static float width = 16;
    public Player(float x, float y, int scale) {
        super(x, y, width, height, scale);

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

    public boolean[] getKeys(){
        return this.mov_keys;
    }


    public void setMov_keys(boolean[] mov_keys) {
        this.mov_keys = mov_keys;
    }

}
