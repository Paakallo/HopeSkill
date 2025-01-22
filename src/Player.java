import java.awt.*;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
//TODO: TOP COLLISION!!!
import java.io.File;
import java.io.IOException;

public class Player extends GameObject {
    private ObjectHandler handler;
    private boolean jump = false;

    private BufferedImage playerStand;

    public static int health = 5;
    public static int reflections = 0;
    public static boolean onPoint = false;

    private long lastDamageTime = 0; // Time in milliseconds

    public Player(float x, float y, ObjectHandler handler) {
        super(x, y, ObjectId.Player, 30, 40); // Player dimensions (width, height)
        this.handler = handler;
        
        try {
            playerStand = ImageIO.read(new File("resources/player_stand.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {

        // Debug: Draw collision boundaries
        //showBounds(g);
        g.drawImage(playerStand, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public void tick() {
        // Update position
        setX(getX() + getVel_x());

        int steps = Math.max(1, (int) Math.ceil(Math.abs(getVel_y()) / 5)); // Divide velocity into steps

        for (int i = 0; i < steps; i++) {
            setY(getY() + (getVel_y() / steps));
            handleCollisions(); //checking collision for every part of vertical velocity
        }

        // Apply gravity
        if (!isGrounded()) {
            gravity();
        }

        // Handle collision
        handleCollisions();

        // kill player if he falls off
        if (getY()>500){
            health = 0;
        }

        // punish player
        if (reflections<0){
            health-=1;
            reflections = 0;
        }
    }

    private void handleCollisions() {
        List<GameObject> objects = handler.getObjects();

        for (GameObject obj : objects) {
            if (obj == this) continue; // Skip self

            if (obj.getId() == ObjectId.Block || obj.getId() == ObjectId.Pipe || obj.getId() == ObjectId.Platform || obj.getId() == ObjectId.Reflection) {
                resolveCollision(obj);
            } else if (obj.getId() == ObjectId.Enemy || obj.getId() == ObjectId.EnemyPatroller) {
                handleEnemyCollision(obj);
            } else if (obj.getId() == ObjectId.Flag) {
                handleFlagCollision(obj);
            }
            
        }
    }
    // object collision
    private void resolveCollision(GameObject obj) {
        Rectangle playerBounds = getBounds();
        Rectangle objBounds = obj.getBounds();

        if (playerBounds.intersects(objBounds)) {
            Rectangle intersection = playerBounds.intersection(objBounds);

            if (intersection.getWidth() > intersection.getHeight()) {
                // Vertical collision
                if (getY() < obj.getY()) {    
                    // Colliding from the top
                    setY(obj.getY() - getHeight());
                    setVel_y(0);
                    jump = false;
                    // activate reflection task if player presses S while staying on a point
                    if (obj.getId() == ObjectId.Reflection){
                        if (onPoint){
                            ReflectionPoint reflection = (ReflectionPoint) obj;
                            if (reflection.activate(this)) {
                                    TaskManager.startReflectionTask(reflection, this);
                                }
                        }
                    }

                } else {
                    System.out.println("Collision detected with " + obj.getId());
                    // Colliding from the bottom
                    setY(obj.getY() + obj.getHeight());
                    setVel_y(0);
                }  
            } else {
                // Horizontal collision
                if (getX() < obj.getX()) {
                    // Colliding from the left
                    setX(obj.getX() - getWidth());
                } else {
                    // Colliding from the right
                    setX(obj.getX() + obj.getWidth());
                }
            }
        }
    }
 

    // enemy collision
    private void handleEnemyCollision(GameObject enemy) {
        // E enemyObj = (GameObject) enemy;

        Rectangle playerBounds = getBounds();
        Rectangle enemyBounds = enemy.getBounds();


        if (playerBounds.intersects(enemyBounds)) {

            long currentTime = System.currentTimeMillis();
            Rectangle intersection = playerBounds.intersection(enemyBounds);

            // Player hits the enemy from the top
            if (intersection.getWidth() > intersection.getHeight()) {
                
                
                setVel_y(-30); // Bounce effect
                //Main.playSound("sound/Voicy_Monster death sound effect.wav"); 
                enemy.freezeObject();
                

            } else if (currentTime - lastDamageTime >= 2000) { // Check 2-second cooldown

                // Player collides with the enemy otherwise
                health--;

                //Main.playSound("sound/terraria-male-player-hurt-sound.wav"); 
                System.out.println("Health: " + health);
                lastDamageTime = currentTime; // Update last damage time

                if (health <= 0) {
                    System.out.println("Game Over!");
                }
            }
        }
    }


    private void handleFlagCollision(GameObject obj){
        if (getBounds().intersects(obj.getBounds())) {
            handler.setEndLevel(true);
            //System.out.println("yesj");
        }
    }


    private boolean isGrounded() {
        List<GameObject> objects = handler.getObjects();

        for (GameObject obj : objects) {
            if (obj == this) continue;

            if ((obj.getId() == ObjectId.Block || obj.getId() == ObjectId.Platform) && getBounds().intersects(obj.getBounds())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth() + 2, (int) getHeight() + 2);
    }

    // Add directional bounds if needed (e.g., for fine-tuning collisions)
    public Rectangle getBoundsTop() {
        return new Rectangle((int) getX(), (int) getY() - 3, (int) getWidth(), (int) getHeight() / 4);
    }


    public boolean getJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }


    // debug purposes
    private void showBounds(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);
        g2d.draw(getBounds());
        g2d.draw(getBoundsTop());
    }
}
