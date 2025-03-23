import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Player {
    private Rectangle2D bounds; // Utiliser Rectangle2D pour les limites du joueur
    private Color color;

    public Player(double x, double y, double size, Color color) {
        this.bounds = new Rectangle2D.Double(x, y, size, size);
        this.color = color;
    }

    public void move(double dx, double dy) {
        bounds.setRect(bounds.getX() + dx, bounds.getY() + dy, bounds.getWidth(), bounds.getHeight());
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}