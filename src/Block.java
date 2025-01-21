import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block extends GameObject{
    
    private ObjectHandler handler;

    int height = 16;
    int width = 16;

    int type; // 0 - grass block, 1 - dirt block

    //textures
    private BufferedImage grass;
    private BufferedImage dirt;

    public Block(float x, float y, int height, int width, int type) {
        super(x, y, ObjectId.Block, width, height);
        this.height = height;
        this.width = width;
        this.handler = handler;
        this.type = type;

        //initialize textures
        try {
            grass = ImageIO.read(new File("resources/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dirt = ImageIO.read(new File("resources/ground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void render(Graphics g) {

        if (type == 0) {
            g.drawImage(grass, (int)getX(), (int)getY(), width, height, null);
        } else {
            g.drawImage(dirt, (int)getX(), (int)getY(), width, height, null);
        }


        
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
