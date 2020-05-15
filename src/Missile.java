import java.awt.Point;
import java.awt.Rectangle;

public class Missile {

    
    final static int WIDTH = 20;
    final static int HEIGHT = 10;
    
    final int trackiness = 5;

    private Rectangle hitbox;

    boolean player;
    private int posX;
    private int posY;
    private int direction; //right is 0deg
    private int speed;
    private int trackingConstant;
    
    public Missile(int pX, int pY, int pDir, int pSpeed) {
        this(pX, pY, true);
        hitbox = new Rectangle(pX, pY, XWing.laserWidth, XWing.laserHeight);
        direction = pDir;
        speed = pSpeed;
        trackingConstant = 0;
    }

    public Missile(int pX, int pY, boolean player) {
        posX = pX;
        posY = pY;
        this.player = player;
    }

    public void tick(Point playerPos) {
    	double deltaX = Math.abs(playerPos.getX() - posX);
		double deltaY = (playerPos.getY()-posY);
		int angleToPlayer = 180-(int) Math.toDegrees(Math.atan(deltaY/deltaX));
		
		if(trackingConstant < trackiness)
		{
			if(direction < angleToPlayer)
				direction++;
			else if(direction > angleToPlayer)
				direction--;
			trackingConstant++;
		} else 
			trackingConstant = 0;
		
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
}