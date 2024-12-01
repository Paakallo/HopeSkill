import java.awt.Graphics;

public abstract class GameObject {
    //coordinates
    private float x,y;
    private float vel_x,vel_y;

    private float width, height;
    private int scale;



    public GameObject(float x, float y, float width, float height, int scale){
        this.x = x*scale;
        this.y = y*scale;
        
        this.width = width*scale;
        this.height = height*scale;
        this.scale = scale;   
    }

    abstract void render(Graphics g);


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


    public float getX(float x){
        return this.x;
    }
    public float getY(float y){
        return this.y;
    }

    public float getVel_x(float vel_x){
        return this.vel_x;
    }

    public float getVel_y(float vel_y){
        return this.vel_y;
    }
}
