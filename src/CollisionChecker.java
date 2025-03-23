import java.awt.geom.Rectangle2D;

public class CollisionChecker {

    /**
     * Vérifie si le joueur entre en collision avec l'obstacle.
     * Si c'est le cas, repousse le joueur hors de l'obstacle.
     *
     * @param playerBounds Les limites du joueur.
     * @param obstacle    L'obstacle.
     */
    public static void resolveCollision(Rectangle2D playerBounds, Rectangle2D obstacle) {
        if (playerBounds.intersects(obstacle)) {
            // Calculer les distances de chevauchement sur chaque axe
            double overlapX = Math.min(
                    playerBounds.getMaxX() - obstacle.getX(),
                    obstacle.getMaxX() - playerBounds.getX()
            );
            double overlapY = Math.min(
                    playerBounds.getMaxY() - obstacle.getY(),
                    obstacle.getMaxY() - playerBounds.getY()
            );

            // Déplacer le joueur dans la direction de la plus petite intersection
            if (overlapX < overlapY) {
                if (playerBounds.getCenterX() < obstacle.getCenterX()) {
                    // Repousser à gauche
                    playerBounds.setRect(
                            obstacle.getX() - playerBounds.getWidth(),
                            playerBounds.getY(),
                            playerBounds.getWidth(),
                            playerBounds.getHeight()
                    );
                } else {
                    // Repousser à droite
                    playerBounds.setRect(
                            obstacle.getMaxX(),
                            playerBounds.getY(),
                            playerBounds.getWidth(),
                            playerBounds.getHeight()
                    );
                }
            } else {
                if (playerBounds.getCenterY() < obstacle.getCenterY()) {
                    // Repousser vers le haut
                    playerBounds.setRect(
                            playerBounds.getX(),
                            obstacle.getY() - playerBounds.getHeight(),
                            playerBounds.getWidth(),
                            playerBounds.getHeight()
                    );
                } else {
                    // Repousser vers le bas
                    playerBounds.setRect(
                            playerBounds.getX(),
                            obstacle.getMaxY(),
                            playerBounds.getWidth(),
                            playerBounds.getHeight()
                    );
                }
            }
        }
    }
}