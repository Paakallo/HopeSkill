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
    Player,
    Enemy,
    EnemyPatroller,
    Block,
    Pipe,
    Platform,
    Reflection,
    Flag;
}
