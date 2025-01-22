import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Manages all game objects in the game world.
 * <p>
 * The {@code ObjectHandler} is responsible for adding, removing, updating, and rendering
 * game objects. It also tracks the player object and manages level completion status.
 * </p>
 *
 */
public class ObjectHandler {
    private List<GameObject> objects;
    private Player player;

    private boolean endOfLevel = false;


    public ObjectHandler(){
        objects = new LinkedList<GameObject>();
    }
    

    /**
     * Updates all game objects.
     * <p>
     * Iterates through the list of game objects and calls their {@code tick} method.
     * Uses an iterator to prevent {@code ConcurrentModificationException}.
     * </p>
     */
    public void tick() {
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            obj.tick();
        }
    }
    

    /**
     * Renders all game objects.
     * <p>
     * Iterates through the list of game objects and calls their {@code render} method.
     * </p>
     *
     * @param g the graphics context used for rendering
     */
    public void render(Graphics g){
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            obj.render(g);
        }
    }


/**
     * Adds a game object to the list.
     *
     * @param obj the game object to add
     */
    public void addObj(GameObject obj){
        objects.add(obj);
    }


    /**
     * Removes a game object from the list.
     *
     * @param obj the game object to remove
     */
    public void removeObj(GameObject obj){
        objects.remove(obj);
    }



    /**
     * Retrieves the list of all game objects.
     *
     * @return the list of game objects
     */
    public List<GameObject> getObjects(){
        return objects;
    }


/**
     * Sets the player object.
     * <p>
     * Adds the player to the list of game objects if it has not already been set.
     * </p>
     *
     * @param player the player object
     * @return {@code 0} if the player was successfully added; {@code -1} if a player already exists
     */
    public int setPlayer(Player player){
        if (this.player != null){
            return -1;
        }
        addObj(player);
        this.player = player;
        return 0;
    }


/**
     * Removes the player object.
     *
     * @return {@code 0} if the player was successfully removed; {@code -1} if no player exists
     */
    public int removePlayer(){
        if (player == null){
            return -1;
        }

        removeObj(player);
        this.player = null;
        return 0;
    }


    /**
     * Sets the player object.
     * <p>
     * Adds the player to the list of game objects if it has not already been set.
     * </p>
     *
     * @param player the player object
     * @return {@code 0} if the player was successfully added; {@code -1} if a player already exists
     */
    public Player getPlayer(){
        return player;
    }


    /**
     * Removes the player object.
     *
     * @return {@code 0} if the player was successfully removed; {@code -1} if no player exists
     */
    public void removeObjects(){
        objects.clear();
        System.out.println("Objects on the map:" + objects.size());
    }


    /**
     * Sets the level completion status.
     *
     * @param end {@code true} if the level is completed; {@code false} otherwise
     */
    public void setEndLevel(boolean end){
        endOfLevel = end;
    }


/**
     * Retrieves the level completion status.
     *
     * @return {@code true} if the level is completed; {@code false} otherwise
     */
    public boolean getEndLevel(){
        return endOfLevel;
    }
}
