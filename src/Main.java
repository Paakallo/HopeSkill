import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


// Main Game Class
public class Main extends JPanel implements ActionListener, KeyListener {
    private Timer timer;                 // Game loop timer
    private boolean isRunning = true;    // Game running status
    private int playerX = 50;            // Example player x-position
    private int playerY = 50;            // Example player y-position
    private int playerSpeed = 5;         // Example player speed

    // Constructor
    public Main() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, this);     // Game loop timer (approx. 60 FPS)
        timer.start();
    }

    // Game Loop
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            updateGame();   // Update game state
            repaint();      // Render game
        }
    }

    // Update game logic
    private void updateGame() {
        // Example: simple movement logic
        // Add other game state updates here
    }

    // Render game
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderGame(g);
    }

    // Render game elements
    private void renderGame(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, 50, 50);  // Example player
        // Add rendering logic for other objects here
    }

    // Key handling
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) playerY -= playerSpeed;
        if (key == KeyEvent.VK_DOWN) playerY += playerSpeed;
        if (key == KeyEvent.VK_LEFT) playerX -= playerSpeed;
        if (key == KeyEvent.VK_RIGHT) playerX += playerSpeed;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release if needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this template
    }

    // Main method
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Game Template");
        Main game = new Main();

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
