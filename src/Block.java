import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends GameObject{
    
    private ObjectHandler handler;
    float height = 16;
    float width = 16;

    public Block(float x, float y, int height, int width, int scale) {
        super(x, y, ObjectId.Block,width, height, scale);
        this.handler = handler;
        this.height = height; //this makes rendered block of a size of a bounding box
        this.width = width;
    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.drawRect((int)getX(), (int)getY(), (int) width, (int) height);
        showBounds(g);
        
    }

    public void tick(){
        //update position every tick
        setX(getVel_x()+getX());
        setY(getVel_y()+getY());
    }

    public Rectangle getBounds(){
        return new Rectangle((int) getX(), 
        (int) getY(), 
        (int) getWidth(), 
        (int) getHeight());
    }

    // function for debug purposes
    private void showBounds(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.red);
        g2d.draw(getBounds());
       
    }
}
