import java.awt.Rectangle;

public class Laser {

    
    final static int WIDTH = 20;
    final static int HEIGHT = 10;

    private Rectangle hitbox;

    boolean player;
    private int posX;
    private int posY;
    private int direction; //right is 0deg
    private int speed;
    
    public Laser(int pX, int pY, int pDir, int pSpeed) {
        this(pX, pY, true);
        hitbox = new Rectangle(pX, pY, XWing.laserWidth, XWing.laserHeight);
        direction = pDir;
        speed = pSpeed;
    }

    public Laser(int pX, int pY, boolean player) {
        posX = pX;
        posY = pY;
        this.player = player;
    }

    public void tick() {
        posX += speed*Math.cos(Math.toRadians(direction));
        posY += speed*Math.sin(Math.toRadians(direction));
        hitbox = new Rectangle(posX - XWing.laserWidth / 4, posY - XWing.laserHeight / 2, 
        		XWing.laserWidth/2,XWing.laserHeight);
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getDirection() {
    	return direction;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }

    public String toString() {
        String pos = "(" + getX() + ", " + getY() + ")";
        return (player ? "player" : "enemy") + " laser at: " + pos;
    }
}