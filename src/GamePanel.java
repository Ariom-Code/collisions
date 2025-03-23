import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private Rectangle2D obstacle;
    private Set<Integer> pressedKeys;
    private String lastPressedKeys;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        player = new Player(50, 50, 50, Color.RED);
        obstacle = new Rectangle2D.Double(300, 200, 100, 150);
        pressedKeys = new HashSet<>();
        lastPressedKeys = "Aucune touche pressée";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner le joueur et l'obstacle
        player.draw(g);
        g.setColor(Color.BLUE);
        g.fillRect((int) obstacle.getX(), (int) obstacle.getY(), (int) obstacle.getWidth(), (int) obstacle.getHeight());

        // Afficher les dernières touches pressées en bas à gauche
        drawLastPressedKeys(g);
    }

    private void drawLastPressedKeys(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));

        // Afficher le texte en bas à gauche
        g.drawString("Dernières touches pressées: " + lastPressedKeys, 10, getHeight() - 20);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        updateLastPressedKeys();
        handleMovement();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        repaint();
    }

    private void updateLastPressedKeys() {
        StringBuilder keys = new StringBuilder();
        for (int keyCode : pressedKeys) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    keys.append("Haut ");
                    break;
                case KeyEvent.VK_DOWN:
                    keys.append("Bas ");
                    break;
                case KeyEvent.VK_LEFT:
                    keys.append("Gauche ");
                    break;
                case KeyEvent.VK_RIGHT:
                    keys.append("Droite ");
                    break;
                case KeyEvent.VK_SPACE:
                    player.move(50, 50);
                    break;
                default:
                    keys.append(KeyEvent.getKeyText(keyCode)).append(" ");
                    break;
            }
        }
        lastPressedKeys = keys.toString().trim();
    }

    private void handleMovement() {
        int speed = 5;
        int dx = 0, dy = 0;

        // Calculer les déplacements en fonction des touches pressées
        if (pressedKeys.contains(KeyEvent.VK_UP)) dy -= speed;
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) dy += speed;
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) dx -= speed;
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) dx += speed;

        // Normaliser le vecteur de déplacement pour les diagonales
        if (dx != 0 && dy != 0) {
            double length = Math.sqrt(dx * dx + dy * dy);
            dx = (int) (dx / length * speed);
            dy = (int) (dy / length * speed);
        }

        // Déplacer le joueur
        player.move(dx, dy);

        // Résoudre les collisions
        CollisionChecker.resolveCollision(player.getBounds(), obstacle);
    }
}