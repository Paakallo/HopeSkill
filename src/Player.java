import java.awt.Graphics;

public class Player extends GameObject{
    private boolean[] mov_keys = new boolean[4];


    public Player(float x, float y, float width, float height, int scale) {
        super(x, y, width, height, scale);
        setVel_X(5);
        setVel_y(5);
    }

    void render(Graphics g){

    }


    public boolean[] getKeys(){
        return this.mov_keys;
    }


    public void setMov_keys(boolean[] mov_keys) {
        this.mov_keys = mov_keys;
    }

}
