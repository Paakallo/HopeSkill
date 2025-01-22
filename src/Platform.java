import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Represents a platform in the game "HopeSkill".
 * <p>
 * The {@code Platform} extends {@code GameObject} and serves as a static element used for navigation
 * in the game world. It provides methods to render the platform, update its position, and define
 * its collision boundaries.
 * </p>
 *
 * <p>
 * Key implemented methods from {@code GameObject}:
 * </p>
 * <ul>
 *     <li>{@link #tick()}: Updates the platform's position based on its velocity.</li>
 *     <li>{@link #render(Graphics)}: Renders the platform's texture on the screen.</li>
 *     <li>{@link #getBounds()}: Retrieves the bounding rectangle for collision detection.</li>
 * </ul>
 *
 */
public class Platform extends GameObject{
    
    private ObjectHandler handler;

    int height = 16;
    int width = 16;

    //textures
    private BufferedImage grass;

    public Platform(float x, float y, int height, int width) {
        super(x, y, ObjectId.Platform, width, height);
        this.height = height;
        this.width = width;
        this.handler = handler;

        //initialize textures
        try {
            grass = ImageIO.read(new File("resources/platform.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


/**
     * Renders the platform's texture on the screen.
     * <p>
     * Called during the rendering phase to display the platform.
     * If the texture is unavailable, a white rectangle is drawn instead.
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    public void render(Graphics g) {

        if (grass != null) {
            g.drawImage(grass, (int)getX(), (int)getY(), width, height, null);
        } else {
            g.setColor(Color.white);
            g.drawRect((int)getX(), (int)getY(), width, height);
        }


        
    }


/**
     * Updates the platform's position based on its velocity.
     * <p>
     * This method is called during the game's update cycle.
     * </p>
     */
    public void tick(){
        //update position every tick
        setX(getVel_x()+getX());
        setY(getVel_y()+getY());
    }


/**
     * Retrieves the bounding rectangle of the platform.
     * <p>
     * This method is used for collision detection.
     * </p>
     *
     * @return a {@code Rectangle} representing the platform's bounds
     */
    public Rectangle getBounds(){
        return new Rectangle((int) getX(), 
        (int) getY(), 
        (int) getWidth(), 
        (int) getHeight());
    }
}