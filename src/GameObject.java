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

    

}
