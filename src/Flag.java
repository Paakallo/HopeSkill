import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Represents a flag in the game "HopeSkill".
 * <p>
 * The {@code Flag} extends {@code GameObject} and serves as the endpoint for a level. It provides methods
 * to render the flag on the screen, update its position, and handle collision detection.
 * </p>
 *
 * <p>
 * Key implemented methods from {@code GameObject}:
 * </p>
 * <ul>
 *     <li>{@link #tick()}: Updates the flag's position based on its velocity.</li>
 *     <li>{@link #render(Graphics)}: Renders the flag's sprite on the screen.</li>
 *     <li>{@link #getBounds()}: Retrieves the bounding rectangle for collision detection.</li>
 * </ul>
 *
 */
public class Flag extends GameObject {

    //textures
    private BufferedImage flag;


    /**
     * Constructs a new {@code Flag} instance.
     *
     * @param x the initial x-coordinate of the flag
     * @param y the initial y-coordinate of the flag
     */
    public Flag(float x, float y) {
        super(x, y, ObjectId.Flag, 32, 32);


        try {
            flag = ImageIO.read(new File("resources/obsidian.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
     * Updates the flag's position based on its velocity.
     * <p>
     * This method is called during the game's update cycle to adjust the flag's position.
     * </p>
     */
    @Override
    public void tick() {
        setX(getX() + getVel_x());
        setY(getY() + getVel_y());
    }


/**
     * Renders the flag's sprite on the screen.
     * <p>
     * Called during the rendering phase to display the flag.
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(flag, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }


/**
     * Retrieves the bounding rectangle of the flag.
     * <p>
     * This method is used for collision detection.
     * </p>
     *
     * @return a {@code Rectangle} representing the flag's bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), 32, 32); // Adjust to match the flag's dimensions
    }

}
