import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    //coordinates
    private float x,y;
    private float vel_x,vel_y;
    private ObjectId id;
    private float width, height;

    protected boolean isAlive = true;


    public GameObject(float x, float y,ObjectId id, float width, float height){
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height; 
    }

    abstract void render(Graphics g);
    abstract void tick();
    abstract Rectangle getBounds();
    
    public void gravity(){
        setVel_y(3);
    }

    public void freezeObject(){
        isAlive = false;
    }

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
