import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Represents a block in the game "HopeSkill".
 * <p>
 * The {@code Block} extends {@code GameObject} and serves as a static element in the game world,
 * used for constructing platforms or obstacles. Blocks can have different types, such as grass
 * or dirt, which are represented with distinct textures.
 * </p>
 *
 * <p>
 * Key implemented methods from {@code GameObject}:
 * </p>
 * <ul>
 *     <li>{@link #tick()}: Updates the block's position based on its velocity.</li>
 *     <li>{@link #render(Graphics)}: Renders the block's texture on the screen.</li>
 *     <li>{@link #getBounds()}: Retrieves the bounding rectangle for collision detection.</li>
 * </ul>
 *
 * <p>
 * Additional functionality:
 * </p>
 * <ul>
 *     <li>Supports multiple block types (e.g., grass, dirt) for varied gameplay visuals.</li>
 * </ul>
 *
 * @author Kryst
 * @version 1.0
 * @since 2025-01-21
 */
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


/**
     * Renders the block's texture on the screen.
     * <p>
     * Called during the rendering phase to display the block.
     * The block's appearance depends on its type (grass or dirt).
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    public void render(Graphics g) {

        if (type == 0) {
            g.drawImage(grass, (int)getX(), (int)getY(), width, height, null);
        } else {
            g.drawImage(dirt, (int)getX(), (int)getY(), width, height, null);
        }


        
    }


/**
     * Updates the block's position based on its velocity.
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
     * Retrieves the bounding rectangle of the block.
     * <p>
     * This method is used for collision detection.
     * </p>
     *
     * @return a {@code Rectangle} representing the block's bounds
     */
    public Rectangle getBounds(){
        return new Rectangle((int) getX(), 
        (int) getY(), 
        (int) getWidth(), 
        (int) getHeight());
    }


}
