import java.awt.*;
import java.util.ArrayList;

public abstract class Ship {
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected int health;

    protected Rectangle hitbox;

    protected ArrayList<Laser> lasers;

    public Ship(int x, int y, int width, int height, int health) {
        posX = x;
        posY = y;
        this.width = width;
        this.height = height;
        this.health = health;
        hitbox = new Rectangle(x, y, width, height);
        lasers = new ArrayList<Laser>();
    }

    public int getPosX() {
        return posX;
    }

    public abstract void setPosX(int posX);

    public int getPosY() {
        return posY;
    }

    public abstract void setPosY(int posY);

    public int getHealth() {
        return health;
    }

    // XXX: specialize this?
    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

}