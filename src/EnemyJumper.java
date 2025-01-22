import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


/**
 * Represents an enemy that jumps periodically or in response to collisions in the game "HopeSkill".
 * <p>
 * The {@code EnemyJumper} extends {@code GameObject} and implements methods to update its state,
 * handle rendering, and detect collisions. It uses gravity for movement and periodically jumps
 * when grounded.
 * </p>
 *
 * <p>
 * Key implemented methods:
 * </p>
 * <ul>
 *     <li>{@link #tick()}: Updates the jumper's state, including movement and collision detection.</li>
 *     <li>{@link #render(Graphics)}: Renders the jumper's sprite on the screen.</li>
 *     <li>{@link #getBounds()}: Retrieves the bounding rectangle for collision detection.</li>
 * </ul>
 *
 * @author Kryst
 * @version 1.0
 * @since 2025-01-21
 */
public class EnemyJumper extends GameObject {
    private boolean isJumping = false;
    private ObjectHandler handler;

    private BufferedImage jumper;


    /**
     * Constructs a new {@code EnemyJumper} instance.
     *
     * @param x       the initial x-coordinate of the jumper
     * @param y       the initial y-coordinate of the jumper
     * @param handler the {@code ObjectHandler} for managing game objects
     */
    public EnemyJumper(float x, float y, ObjectHandler handler) {
        super(x, y, ObjectId.Enemy, 32, 32);
        this.handler = handler;

         try {
            jumper = ImageIO.read(new File("resources/jumper.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
     * Renders the jumper's sprite on the screen.
     * <p>
     * Called during the rendering phase to display the jumper.
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    @Override
    public void render(Graphics g) {
        if (isAlive){
            g.drawImage(jumper, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        }
    }


/**
     * Updates the jumper's state, including movement and collision handling.
     * <p>
     * This method applies gravity, moves the jumper, and handles collisions
     * with other objects. The jumper periodically initiates a jump when grounded.
     * </p>
     */
    @Override
    public void tick() {
        if (isAlive){
            setX(getX() + getVel_x());
            setY(getY() + getVel_y());

            // Apply gravity
            if (!isGrounded()) {
                gravity();
            }

            // Handle collisions
            handleCollisions();


            if (isJumping) {
                setVel_y(-80); // Jump up
                isJumping = false;
            }
        } else{
            // avoid synchronization problems and don't delete enemies
            setX(0);
            setY(0);
        }
        
    }


 /**
     * Handles collisions between the jumper and other objects.
     */
    private void handleCollisions() {
        List<GameObject> objects = handler.getObjects();

        for (GameObject obj : objects) {
            if (obj == this) continue; // Skip self

            if (obj.getId() == ObjectId.Block || obj.getId() == ObjectId.Pipe || obj.getId() == ObjectId.Platform) {
                resolveCollision(obj);
            }
        }
    }


/**
     * Resolves collisions with static objects such as blocks and platforms.
     * <p>
     * Adjusts the jumper's position and velocity to prevent overlapping.
     * </p>
     *
     * @param obj the object the jumper collided with
     */
    private void resolveCollision(GameObject obj) {
        Rectangle jumperBounds = getBounds();
        Rectangle objBounds = obj.getBounds();

        if (jumperBounds.intersects(objBounds)) {
            Rectangle intersection = jumperBounds.intersection(objBounds);

            if (intersection.getWidth() > intersection.getHeight()) {
                // Vertical collision
                if (getY() < obj.getY()) {
                    // Colliding from the top
                    setY(obj.getY() - getHeight());
                    setVel_y(0);
                    isJumping = true; // Reset jump state, by default jumper is jumping
                } else {
                    // Colliding from the bottom
                    setY(obj.getY() + obj.getHeight());
                    setVel_y(0);
                }
            } else {
                // Horizontal collision
                if (getX() < obj.getX()) {
                    // Colliding from the left
                    setX(obj.getX() - getWidth());
                } else {
                    // Colliding from the right
                    setX(obj.getX() + obj.getWidth());
                }
            }
        }
    }


/**
     * Checks if the jumper is grounded by detecting collisions below.
     *
     * @return {@code true} if the jumper is on the ground; {@code false} otherwise
     */
    private boolean isGrounded() {
        

        for (GameObject obj : handler.getObjects()) {
            if (obj == this) continue;

            if ((obj.getId() == ObjectId.Block || obj.getId() == ObjectId.Platform) && getBounds().intersects(obj.getBounds())) {
                return true;
            }
        }
        return false;
    }

   
/**
     * Retrieves the bounding rectangle of the jumper.
     * <p>
     * This method is used for collision detection.
     * </p>
     *
     * @return a {@code Rectangle} representing the jumper's bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
