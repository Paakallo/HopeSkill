import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {
    public Main game;

    public Menu(Main game) {
        this.game = game;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Menu", 400, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawRect(400, 300, 200, 50);
        g.drawString("Start Game", 420, 335);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        // Check if "Start Game" is clicked
        if (mx >= 400 && mx <= 600 && my >= 300 && my <= 350) {
            game.startGame();
        }
    }
}
