import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObjectHandler {
    private List<GameObject> objects;
    private Player player;

    public ObjectHandler(){
        objects = new LinkedList<GameObject>();
    }

    // public void tick(){
    //     for (GameObject obj: objects){
    //         obj.tick();
    //     }
    // }
    
    //Using iterator prevents ConcurrentModificationException
    public void tick() {
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            obj.tick();
        }
    }
    

    public void render(Graphics g){
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            obj.render(g);
        }
        
        // for (GameObject obj: objects){
        //     obj.render(g);
        // }
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

    public int setPlayer(Player player){
        if (this.player != null){
            return -1;
        }
        addObj(player);
        this.player = player;
        return 0;
    }

    public int removePlayer(){
        if (player == null){
            return -1;
        }

        removeObj(player);
        this.player = null;
        return 0;
    }

    public Player getPlayer(){
        return player;
    }
}
