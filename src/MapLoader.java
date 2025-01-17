import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MapLoader {
    private ObjectHandler handler;

    public MapLoader(ObjectHandler handler) {
        this.handler = handler;
    }

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
                        1,
                        0
                ));

                int j = 50;
                while (block.getInt("y")+j<Main.WINDOW_HEIGHT+100){
                    handler.addObj(new Block(
                        block.getInt("x"),
                        block.getInt("y")+j,
                        block.getInt("height"),
                        block.getInt("width"),
                        1,
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
                        pipe.getInt("width"),
                            1 // Assuming scale is 1
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
                        platform.getInt("width"),
                            1 // Assuming scale is 1
                    ));
                }
            }
            // Parse enemy
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
                        1,
                        handler    
                    ));
                }
            }
            // Parse Player
            JSONObject player = json.getJSONObject("Player");
            handler.setPlayer(new Player(
                    player.getInt("x"),
                    player.getInt("y"),
                    1, // Assuming scale is 1
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
                            1
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
