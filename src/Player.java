import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Player extends Ship {
    final static int DELTA = 3;
    final static int LASER_DAMAGE = 5;

    final static double maxSpeed = 12; // pixels per second?
    final static double maxAcceleration = 0.07;

    final static int laserOffsetX = 6;
    final static int laserOffsetY = 23;

    private int power;
    private int fireRate;
    private int shotCounter;

    public Player() {
        super(1, XWing.SCREEN_HEIGHT / 2, XWing.xWingWidth, XWing.xWingHeight, 100);
        power = 10;
        fireRate = 5;
        shotCounter = 1;
    }

    public void shootLaser() {
        if (shotCounter > 0) // left cannon
        {
            if (shotCounter >= fireRate) {
                lasers.add(new Laser((int) posX + laserOffsetX, (int) posY - laserOffsetY));
                shotCounter = -1;
            } else {
                shotCounter++;
            }
        } else { // right cannon
            if (shotCounter <= -1 * fireRate) {
                lasers.add(new Laser((int) posX + laserOffsetX, (int) posY + laserOffsetY));
                shotCounter = 1;
            } else {
                shotCounter--;
            }
        }
    }

    public void updatePos(int mouseX, int mouseY) {
        // System.out.println("(" + mouseX + ", " + mouseY + ")");
        double deltaX = (mouseX - posX + 0.5 / maxAcceleration);
        double deltaY = (mouseY - posY + 0.5 / maxAcceleration);
        double movementX = deltaX * maxAcceleration;
        double movementY = deltaY * maxAcceleration;

        if (Math.abs(movementX) > maxSpeed)
            movementX = Math.abs(movementX) / movementX * maxSpeed; // preserve its sign
        if (Math.abs(movementY) > maxSpeed)
            movementY = Math.abs(movementY) / movementY * maxSpeed; // preserve its sign
        // posX += movementX;
        // posY += movementY;
        setPosX(getPosX() + movementX);
        setPosY(getPosY() + movementY);
        // System.out.println("(" + getPosX() + ", " + getPosY() + ")");
        System.out.println("movementX: " + movementX);
        System.out.println("movementY: " + movementY);
    }

    public void setPosX(double posX) {
        if (posX + XWing.xWingWidth + 1 > XWing.SCREEN_WIDTH) {
            posX = XWing.SCREEN_WIDTH - XWing.xWingWidth - 1;
        }
        if (posX <= 1) {
            posX = 1;
        }
        this.posX = posX;
        hitbox = new Rectangle2D.Double(getPosX(), getPosY(), width, height);
    }

    public void setPosY(double posY) {
        if (posY + XWing.xWingWidth + 1 > XWing.SCREEN_HEIGHT) {
            posY = XWing.SCREEN_HEIGHT - XWing.xWingWidth - 1;
        }
        if (posY <= XWing.BORDER) {
            posY = XWing.BORDER + 1;
        }
        this.posY = posY;
        hitbox = new Rectangle2D.Double(getPosX(), getPosY(), width, height);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        if (power > 100) {
            power = 100;
        }
        this.power = power;
    }
}