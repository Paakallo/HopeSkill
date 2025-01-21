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
    private BufferedImage pipe;

    public Pipe(float x, float y, int height, int width) {
        super(x, y, ObjectId.Block,width, height);
        this.height = height;
        this.width = width;
        this.handler = handler;


        try {
            pipe = ImageIO.read(new File("resources/pipe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void render(Graphics g) {

        g.drawImage(pipe, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        

        
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
