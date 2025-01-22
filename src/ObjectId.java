/**
 * Enumerates the unique identifiers for game objects in "HopeSkill".
 * <p>
 * Each {@code ObjectId} represents a specific type of game object, enabling
 * identification and differentiation within the game logic.
 * </p>
 *
 * <p>
 * Common uses include:
 * </p>
 * <ul>
 *     <li>Determining object behavior based on its type.</li>
 *     <li>Handling collisions and interactions between objects.</li>
 *     <li>Rendering specific objects differently based on their type.</li>
 * </ul>
 *
 */
public enum ObjectId {

    /**
     * Represents the player character.
     */
    Player,

    /**
     * Represents a generic enemy.
     */
    Enemy,

    /**
     * Represents an enemy that patrols a specified area.
     */
    EnemyPatroller,

    /**
     * Represents a block, typically used for constructing platforms or obstacles.
     */
    Block,

    /**
     * Represents a pipe, often used as an interactive or decorative element.
     */
    Pipe,

    /**
     * Represents a platform, often used for jumping or navigation.
     */
    Platform,

    /**
     * Represents a reflection point, used for specific tasks or challenges.
     */
    Reflection,

    /**
     * Represents the flag, signaling the end of a level.
     */
    Flag;
}
