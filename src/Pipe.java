import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pipe extends GameObject{
    
    private ObjectHandler handler;

    int height = 16;
    int width = 16;

    //textures
    private BufferedImage grass;

    public Pipe(float x, float y, int height, int width, int scale) {
        super(x, y, ObjectId.Block,width, height, scale);
        this.height = height;
        this.width = width;
        this.handler = handler;

        //initialize textures
        // try {
        //     grass = ImageIO.read(new File("resources/pipe.png"));
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

    }

    public void render(Graphics g) {

        // if (grass != null) {
        //     g.drawImage(grass, (int)getX(), (int)getY(), width, height, null);
        // } else {
        //     g.setColor(Color.white);
        //     g.drawRect((int)getX(), (int)getY(), width, height);
        // }
            g.setColor(Color.white);
            g.drawRect((int)getX(), (int)getY(), width, height);


        
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
