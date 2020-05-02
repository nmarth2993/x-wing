public class Laser {

    final static int velX = 10;
    final static int velY = 10;

    final static int WIDTH = 20;
    final static int HEIGHT = 10;

    boolean player;
    int posX;
    int posY;

    public Laser(int pX, int pY) {
        this(pX, pY, true);
    }

    public Laser(int pX, int pY, boolean player) {
        posX = pX;
        posY = pY;
        this.player = player;
    }

    public void tick() {
        posX += velX;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public String toString() {
        String pos = "(" + getX() + ", " + getY() + ")";
        return (player ? "player" : "enemy") + " laser at: " + pos;
    }
}