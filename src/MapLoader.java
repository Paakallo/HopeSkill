import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Handles loading and parsing game maps in the game "HopeSkill".
 * <p>
 * The {@code MapLoader} class is responsible for reading map data from JSON files,
 * creating game objects such as blocks, pipes, platforms, enemies, and setting up
 * the player and other interactable elements.
 * </p>
 *
 */
public class MapLoader {
    private ObjectHandler handler;


    /**
     * Constructs a new {@code MapLoader} instance.
     *
     * @param handler the {@code ObjectHandler} responsible for managing game objects
     */
    public MapLoader(ObjectHandler handler) {
        this.handler = handler;
    }


/**
     * Loads a map based on the selected level.
     * <p>
     * This method clears existing game objects, reads the JSON map file,
     * and parses various game objects including blocks, pipes, platforms,
     * enemies, the player, reflection points, and the level's flag.
     * </p>
     *
     * @param selLevel the game state representing the selected level
     */
    public void loadMap(GameState selLevel) {

        String mapFile = findMap(selLevel);
        try {
            // Clear existing objects
            handler.removeObjects();

            // Read JSON file content
            String content = new String(Files.readAllBytes(Paths.get(mapFile)));

            // Parse JSON content
            JSONObject json = new JSONObject(content);

            // Parse Blocks
            JSONArray blocks = json.getJSONArray("Block");
            for (int i = 0; i < blocks.length(); i++) {
                JSONObject block = blocks.getJSONObject(i);
                handler.addObj(new Block(
                        block.getInt("x"),
                        block.getInt("y"),
                        block.getInt("height"),
                        block.getInt("width"),
                        0
                ));

                int j = 50;
                while (block.getInt("y")+j<Main.WINDOW_HEIGHT+100){
                    handler.addObj(new Block(
                        block.getInt("x"),
                        block.getInt("y")+j,
                        block.getInt("height"),
                        block.getInt("width"),
                        1
                    ));
                    j+=50;
                }
                j = 50;
            }

            // Parse pipes
            if (json.has("Pipe")) {
                JSONArray pipes = json.getJSONArray("Pipe");
                for (int i = 0; i < pipes.length(); i++) {
                    JSONObject pipe = pipes.getJSONObject(i);
                    handler.addObj(new Pipe(
                        pipe.getInt("x"),
                        pipe.getInt("y"),
                        pipe.getInt("height"),
                        pipe.getInt("width")
                    ));
                }
            }
            // Parse platforms
            if (json.has("Platform")) {
                JSONArray platforms = json.getJSONArray("Platform");
                for (int i = 0; i < platforms.length(); i++) {
                    JSONObject platform = platforms.getJSONObject(i);
                    handler.addObj(new Platform(
                        platform.getInt("x"),
                        platform.getInt("y"),
                        platform.getInt("height"),
                        platform.getInt("width")
                    ));
                }
            }
            // Parse enemies
            if (json.has("EnemyPatroller")) {
                JSONArray patrollers = json.getJSONArray("EnemyPatroller");
                for (int i = 0; i < patrollers.length(); i++) {
                    JSONObject patroller = patrollers.getJSONObject(i);
                    handler.addObj(new EnemyPatroller(
                        patroller.getInt("x"),
                        patroller.getInt("y"),
                        patroller.getInt("width"),
                        patroller.getInt("height"),
                        patroller.getInt("minX"),
                        patroller.getInt("maxX"),
                        handler    
                    ));
                }
            }

            if (json.has("EnemyJumper")) {
                JSONArray jumpers = json.getJSONArray("EnemyJumper");
                for (int i = 0; i < jumpers.length(); i++) {
                    JSONObject jumper = jumpers.getJSONObject(i);
                    handler.addObj(new EnemyJumper(
                        jumper.getInt("x"),
                        jumper.getInt("y"),
                        handler    
                    ));
                }
            }
            // Parse Player
            JSONObject player = json.getJSONObject("Player");
            handler.setPlayer(new Player(
                    player.getInt("x"),
                    player.getInt("y"),
                    handler
            ));

            // Parse ReflectionPoints
            if (json.has("ReflectionPoint")) {
                JSONArray reflections = json.getJSONArray("ReflectionPoint");
                for (int i = 0; i < reflections.length(); i++) {
                    JSONObject reflection = reflections.getJSONObject(i);
                    handler.addObj(new ReflectionPoint(
                            reflection.getInt("x"),
                            reflection.getInt("y"),
                            reflection.getString("question"),
                            reflection.getString("answ1"),
                            reflection.getString("answ2")
                    ));
                }
            }

            // Parse Flag
            if (json.has("Flag")) {
                JSONObject flag = json.getJSONObject("Flag");
                handler.addObj(new Flag(
                    flag.getInt("x"),
                    flag.getInt("y")
                ));
            
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the file path for the specified level.
     *
     * @param selLevel the game state representing the selected level
     * @return the file path to the map JSON file
     */
    public String findMap(GameState selLevel){
        String mapPath;
        
        if (selLevel == GameState.L1){
            mapPath = "maps/level1.json";
        }
        else if (selLevel == GameState.L2){
            mapPath = "maps/level2.json";
        }
        else {
            return "";
        }
        System.out.println("Selected map"+ mapPath);
    
        return mapPath;
    }
}
