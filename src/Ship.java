import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public abstract class Ship {
    protected double posX;
    protected double posY;
    protected int width;
    protected int height;
    protected int health;
    protected boolean dead;

    protected Rectangle2D hitbox;

    protected ArrayList<Laser> lasers;

    public Ship(int x, int y, int width, int height, int health) {
        dead = false;
        posX = x;
        posY = y;
        this.width = width;
        this.height = height;
        this.health = health;
        hitbox = new Rectangle(x, y, width, height);
        lasers = new ArrayList<Laser>();
    }

    public double getPosX() {
        return posX;
    }

    public abstract void setPosX(double posX);

    public double getPosY() {
        return posY;
    }

    public abstract void setPosY(double posY);

    public int getHealth() {
        return health;
    }

    // XXX: specialize this?
    public void setHealth(int health) {
        if (health <= 0) {
            health = 0;
            dead = true;
        }
        this.health = health;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public Rectangle2D getHitbox() {
        return hitbox;
    }

    public boolean isDead() {
        return dead;
    }

}