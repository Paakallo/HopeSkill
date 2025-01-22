import java.awt.Graphics;
import java.awt.Rectangle;


/**
 * Represents a generic game object in the game world.
 * <p>
 * This class serves as a base for all entities and objects
 * in the game, providing common properties like position,
 * velocity, dimensions, and an identifier for object types.
 * </p>
 *
 */
public abstract class GameObject {
    //coordinates
    private float x,y;
    private float vel_x,vel_y;
    private ObjectId id;
    private float width, height;
    // a check if an object is alive
    protected boolean isAlive = true;


    /**
     * Constructs a new {@code GameObject} with the specified properties.
     *
     * @param x      the initial x-coordinate of the object
     * @param y      the initial y-coordinate of the object
     * @param id     the unique identifier for the object's type
     * @param width  the width of the object
     * @param height the height of the object
     */
    public GameObject(float x, float y,ObjectId id, float width, float height){
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height; 
    }

    /**
     * Renders the object on the screen. To be implemented by subclasses.
     *
     * @param g the graphics context used for rendering
     */
    abstract void render(Graphics g);

    /**
     * Updates the object's state. To be implemented by subclasses.
     */
    abstract void tick();

    /**
     * Retrieves the bounding area of the object.
     *
     * @return a {@code Rectangle} representing the object's bounds
     */
    abstract Rectangle getBounds();
    
    /**
     * Applies gravity to isntances of subclasses, which interact with the world
     * <p>
     * This method sets the value of vertical velocity to 3.
     * For proper function it requires {@link isGrounded method}
     * </p>
     */
    public void gravity(){
        setVel_y(3);
    }

    /**
     * Stops tick and render methods pf an object
     * <p>
     * This method exists to avoid synchronization issues,
     * when an object is removed from the {@link linkedList}
     * 
     * </p>
     */
    public void freezeObject(){
        isAlive = false;
    }


    // Getters and Setters


    public float setX(float x){
        return this.x=x;
    }
    public float setY(float y){
        return this.y=y;
    }


    public float setVel_X(float vel_x){
        return this.vel_x=vel_x;
    }

    public float setVel_y(float vel_y){
        return this.vel_y=vel_y;
    }


    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }


    public float getVel_x(){
        return this.vel_x;
    }

    public float getVel_y(){
        return this.vel_y;
    }


    public float getWidth(){
        return this.width;
    }

    public float getHeight(){
        return this.height;
    }


    public ObjectId getId(){
        return this.id;
    }

    public void setId(ObjectId Id){
        this.id=Id;
    }
}
