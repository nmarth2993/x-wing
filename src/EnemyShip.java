import java.util.Random;

public class EnemyShip {
    final static Random r = new Random();

    int type;
    int healthDrop; // something like health/shields/powerup
    int powerupDrop;

    public EnemyShip(int type) {
        this.type = type;
        healthDrop = r.nextInt(1000);
        powerupDrop = r.nextInt(1000);
    }

    public int getType() {
        return type;
    }

    public int getHealthDrop() {
        return healthDrop;
    }

    public int getPowerupDrop() {
        return powerupDrop;
    }

}