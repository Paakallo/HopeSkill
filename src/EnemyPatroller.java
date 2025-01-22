import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


/**
 * Represents an enemy that patrols between defined bounds in the game "HopeSkill".
 * <p>
 * The {@code EnemyPatroller} extends {@code GameObject} and implements methods to update its state,
 * handle rendering, and detect collisions. It moves horizontally within the specified bounds
 * and reverses direction when reaching the edges or upon collision.
 * </p>
 *
 * <p>
 * Key implemented methods:
 * </p>
 * <ul>
 *     <li>{@link #tick()}: Updates the patroller's state, including movement, collisions, and direction reversal.</li>
 *     <li>{@link #render(Graphics)}: Renders the patroller's sprite on the screen.</li>
 *     <li>{@link #getBounds()}: Retrieves the bounding rectangle for collision detection.</li>
 * </ul>
 *
 */
public class EnemyPatroller extends GameObject {
    private float minX, maxX; // Patrol bounds
    private ObjectHandler handler;

    private BufferedImage patroller;


    /**
     * Constructs a new {@code EnemyPatroller} instance.
     *
     * @param x       the initial x-coordinate of the patroller
     * @param y       the initial y-coordinate of the patroller
     * @param width   the width of the patroller
     * @param height  the height of the patroller
     * @param minX    the minimum x-coordinate for patrolling
     * @param maxX    the maximum x-coordinate for patrolling
     * @param handler the {@code ObjectHandler} for managing game objects
     */
    public EnemyPatroller(float x, float y, int width, int height, float minX, float maxX, ObjectHandler handler) {
        super(x, y, ObjectId.Enemy, 32, 32);
        this.minX = minX;
        this.maxX = maxX;
        this.handler = handler;
        setVel_X(2); // Initial speed

        try {
            patroller = ImageIO.read(new File("resources/patroller.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Renders the patroller's sprite on the screen.
     * <p>
     * Called during the rendering phase to display the patroller.
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    @Override
    public void render(Graphics g) {
        if (isAlive){
            g.drawImage(patroller, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
        }
    }


     /**
     * Updates the patroller's state, including movement, collision handling, and direction reversal.
     * <p>
     * The patroller applies gravity, handles collisions, and reverses its horizontal direction
     * upon reaching the patrol bounds or colliding with an object.
     * </p>
     */
    @Override
    public void tick() {
        if (isAlive){
            setX(getX() + getVel_x());
            setY(getY() + getVel_y());

            // Apply gravity
            gravity();

            // Handle collisions
            handleCollisions();

            // Reverse direction when reaching patrol bounds
            if (getX() <= minX || getX() >= maxX) {
                setVel_X(-getVel_x());
            }
        } else{
            // avoid synchronization problems and don't delete enemies
            setX(0);
            setY(0);
        }
    }


    /**
     * Handles collisions between the patroller and other objects.
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
     * Adjusts the patroller's position and velocity to prevent overlapping and reverses
     * horizontal direction upon collision.
     * </p>
     *
     * @param obj the object the patroller collided with
     */
    private void resolveCollision(GameObject obj) {
        Rectangle enemyBounds = getBounds();
        Rectangle objBounds = obj.getBounds();

        if (enemyBounds.intersects(objBounds)) {
            Rectangle intersection = enemyBounds.intersection(objBounds);

            if (intersection.getWidth() > intersection.getHeight()) {
                // Vertical collision
                if (getY() < obj.getY()) {
                    // Colliding from the top
                    setY(obj.getY() - getHeight());
                    setVel_y(0);
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
                    setVel_X(-getVel_x());
                } else {
                    // Colliding from the right
                    setX(obj.getX() + obj.getWidth());
                    setVel_X(-getVel_x());
                }
            }
        }
    }

    
    /**
     * Retrieves the bounding rectangle of the patroller.
     * <p>
     * This method is used for collision detection.
     * </p>
     *
     * @return a {@code Rectangle} representing the patroller's bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
