import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    //coordinates
    private float x,y;
    private float vel_x,vel_y;
    private ObjectId id;
    private float width, height;
    private int scale;



    public GameObject(float x, float y,ObjectId id, float width, float height, int scale){
        this.x = x*scale;
        this.y = y*scale;
        this.id = id;
        this.width = width*scale;
        this.height = height*scale;
        this.scale = scale;   
    }

    abstract void render(Graphics g);
    abstract void tick();
    abstract Rectangle getBounds();
    
    public void gravity(){
        setVel_y(3);
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
}
