import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class ObjectHandler {
    private List<GameObject> objects;
    
    public ObjectHandler(){
        objects = new LinkedList<GameObject>();
    }

    public void tick(){
        for (GameObject obj: objects){
            obj.tick();
        }
    }

    public void render(Graphics g){
        for (GameObject obj: objects){
            obj.render(g);
        }
    }

    public void addObj(GameObject obj){
        objects.add(obj);
    }

    public void removeObj(GameObject obj){
        objects.remove(obj);
    }

    public List<GameObject> getObjects(){
        return objects;
    }
}
