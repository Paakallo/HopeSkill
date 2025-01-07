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
        this.height = height;
        this.width = width;
        this.handler = handler;


    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.drawRect((int)getX(), (int)getY(), (int) width, (int) height);


        
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


}
