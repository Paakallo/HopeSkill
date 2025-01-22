import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Represents a pipe in the game "HopeSkill".
 * <p>
 * The {@code Pipe} extends {@code GameObject} and serves as a static element in the game world.
 * It provides methods to render the pipe on the screen, update its position, and handle
 * collision detection through its bounding box.
 * </p>
 *
 * <p>
 * Key implemented methods from {@code GameObject}:
 * </p>
 * <ul>
 *     <li>{@link #tick()}: Updates the pipe's position based on its velocity.</li>
 *     <li>{@link #render(Graphics)}: Renders the pipe's sprite on the screen.</li>
 *     <li>{@link #getBounds()}: Retrieves the bounding rectangle for collision detection.</li>
 * </ul>
 *
 */
public class Pipe extends GameObject{
    
    private ObjectHandler handler;

    int height = 16;
    int width = 16;

    //textures
    private BufferedImage pipe;


    /**
     * Constructs a new {@code Pipe} instance.
     *
     * @param x      the initial x-coordinate of the pipe
     * @param y      the initial y-coordinate of the pipe
     * @param height the height of the pipe
     * @param width  the width of the pipe
     */
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


    /**
     * Renders the pipe's sprite on the screen.
     * <p>
     * Called during the rendering phase to display the pipe.
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    public void render(Graphics g) {

        g.drawImage(pipe, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);  
    }


/**
     * Updates the pipe's position based on its velocity.
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
     * Retrieves the bounding rectangle of the pipe.
     * <p>
     * This method is used for collision detection.
     * </p>
     *
     * @return a {@code Rectangle} representing the pipe's bounds
     */ 
    public Rectangle getBounds(){
        return new Rectangle((int) getX(), 
        (int) getY(), 
        (int) getWidth(), 
        (int) getHeight());
    }


}
