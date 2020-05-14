import java.awt.Rectangle;

public class Laser {

    final static int velX = 10;
    final static int velY = 10;

    final static int WIDTH = 20;
    final static int HEIGHT = 10;

    private Rectangle hitbox;

    boolean player;
    private int posX;
    private int posY;

    public Laser(int pX, int pY) {
        this(pX, pY, true);
        hitbox = new Rectangle(pX, pY, XWing.laserWidth, XWing.laserHeight);
    }

    public Laser(int pX, int pY, boolean player) {
        posX = pX;
        posY = pY;
        this.player = player;
    }

    public void tick() {
        posX += velX;
        hitbox = new Rectangle(posX - XWing.laserWidth / 2, posY - XWing.laserHeight / 2, XWing.laserWidth,
                XWing.laserHeight);
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public String toString() {
        String pos = "(" + getX() + ", " + getY() + ")";
        return (player ? "player" : "enemy") + " laser at: " + pos;
    }
}