import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Represents a reflection point in the game "HopeSkill".
 * <p>
 * The {@code ReflectionPoint} extends {@code GameObject} and serves as an interactive element
 * that prompts the player to complete reflection-based tasks. It provides methods to render
 * the reflection point, update its position, and manage activation and task completion states.
 * </p>
 *
 * <p>
 * Key implemented methods from {@code GameObject}:
 * </p>
 * <ul>
 *     <li>{@link #tick()}: Updates the reflection point's position based on its velocity.</li>
 *     <li>{@link #render(Graphics)}: Renders the reflection point's sprite on the screen.</li>
 *     <li>{@link #getBounds()}: Retrieves the bounding rectangle for collision detection.</li>
 * </ul>
 *
 * <p>
 * Additional functionality:
 * </p>
 * <ul>
 *     <li>{@link #activate(Player)}: Activates the reflection point when the player interacts with it.</li>
 *     <li>{@link #completeTask(Player)}: Marks the reflection point's task as completed.</li>
 * </ul>
 *
 */
public class ReflectionPoint extends GameObject {
    private boolean isActive = true;
    private boolean taskCompleted = false;

    private BufferedImage flower;

    String ques;
    String answ1;
    String answ2;

    public ReflectionPoint(float x, float y, String ques, String answ1, String answ2) {
        super(x, y, ObjectId.Reflection, 50, 50);
        this.ques = ques;
        this.answ1 = answ1;
        this.answ2 = answ2;

        try {
            flower = ImageIO.read(new File("resources/flower.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
     * Renders the reflection point's sprite on the screen.
     * <p>
     * Called during the rendering phase to display the reflection point.
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    @Override
    public void render(Graphics g) {  
        g.drawImage(flower, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }


/**
     * Updates the reflection point's position based on its velocity.
     * <p>
     * This method is called during the game's update cycle.
     * </p>
     */
    @Override
    public void tick() {
        setX(getX() + getVel_x());
        setY(getY() + getVel_y());
    }


/**
     * Retrieves the bounding rectangle of the reflection point.
     * <p>
     * This method is used for collision detection.
     * </p>
     *
     * @return a {@code Rectangle} representing the reflection point's bounds
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }


    /**
     * Activates the reflection point.
     * <p>
     * This method is triggered when the player interacts with the reflection point.
     * If the point is active, it becomes inactive and initiates the reflection task.
     * </p>
     *
     * @param player the player interacting with the reflection point
     * @return {@code true} if the point was successfully activated; {@code false} otherwise
     */
    public boolean activate(Player player) {
        if (isActive) {
            isActive = false;
            return true; // Wywołaj zadanie refleksji
        }
        return false;
    }


    /**
     * Marks the reflection point's task as completed.
     *
     * @param player the player completing the reflection task
     */
    public void completeTask(Player player) {
        taskCompleted = true;
    }


/**
     * Retrieves the question for the reflection task.
     *
     * @return the reflection task question
     */
    public String getQuestion() {
        return ques;
    }


/**
     * Retrieves the first answer option for the reflection task.
     *
     * @return the first answer option
     */
    public String getAnsw1() {
        return answ1;
    }


/**
     * Retrieves the second answer option for the reflection task.
     *
     * @return the second answer option
     */
    public String getAnsw2() {
        return answ2;
    }
}
