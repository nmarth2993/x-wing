import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Player extends Ship {
    final static double maxSpeed = 12; // pixels per second?
    final static double maxAcceleration = 0.07;
    final static int LASER_DAMAGE = 5;

    final static int laserOffsetX = 6;
    final static int laserOffsetY = 23;

    private int power;
    private int fireRate;
    private int shotCounter;

    private int maxHealth = 200;

    public Player() {
        super(100, XWing.SCREEN_HEIGHT / 2, XWing.xWingWidth / 2, XWing.xWingHeight, 99999);
        power = 10;
        fireRate = 3;
        shotCounter = 1;
        health = maxHealth;
    }

    public void shootLaser() {
        if (shotCounter > 0) // left cannon
        {
            if (shotCounter >= fireRate) {
                lasers.add(new Laser((int) posX + laserOffsetX, (int) posY - laserOffsetY, 0, 30));
                shotCounter = -1;
            } else {
                shotCounter++;
            }
        } else { // right cannon
            if (shotCounter <= -1 * fireRate) {
                lasers.add(new Laser((int) posX + laserOffsetX, (int) posY + laserOffsetY, 0, 30));
                shotCounter = 1;
            } else {
                shotCounter--;
            }
        }
    }

    public void updatePos(int mouseX, int mouseY) {
        double deltaX = (mouseX - posX + 0.5 / maxAcceleration);
        double deltaY = (mouseY - posY + 0.5 / maxAcceleration);
        double movementX = deltaX * maxAcceleration;
        double movementY = deltaY * maxAcceleration;

        if (Math.abs(movementX) > maxSpeed)
            movementX = Math.abs(movementX) / movementX * maxSpeed; // preserve its sign
        if (Math.abs(movementY) > maxSpeed)
            movementY = Math.abs(movementY) / movementY * maxSpeed;
        setPosX(getPosX() + movementX);
        setPosY(getPosY() + movementY);
    }

    public void setPosX(double posX) {
        if (posX + width / 2 + 1 > XWing.SCREEN_WIDTH) {
            posX = XWing.SCREEN_WIDTH - width / 2;
        }
        if (posX <= width / 2) {
            posX = width / 2;
        }
        this.posX = posX;
        hitbox = new Rectangle2D.Double(getPosX() - width / 2, getPosY() - height / 2, width, height);
    }

    public void setPosY(double posY) {
        if (posY + height / 2 + 1 > XWing.SCREEN_HEIGHT) {
            posY = XWing.SCREEN_HEIGHT - height / 2;
        }
        if (posY - height / 2 <= XWing.BORDER) {
            posY = XWing.BORDER + height / 2 + 1;
        }
        this.posY = posY;
        hitbox = new Rectangle2D.Double(getPosX() - width / 2, getPosY() - height / 2, width, height);
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

    public int getMaxHealth() {
        return maxHealth;
    }
}