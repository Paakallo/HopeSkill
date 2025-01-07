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

    public void loadMap(String mapFile) {
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
                        1 // Assuming scale is 1
                ));
            }

            // Parse Player
            JSONObject player = json.getJSONObject("Player");
            handler.setPlayer(new Player(
                    player.getInt("x"),
                    player.getInt("y"),
                    1, // Assuming scale is 1
                    handler
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
