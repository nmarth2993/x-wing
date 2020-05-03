import java.util.Random;
import java.awt.geom.*;

public class EnemyShip extends Ship {
    final static Random r = new Random();
    final static int CHANCE = 500; // where the chance is 1/x the
                                   // probabilityof a match
    final static int TIE = 1;
    final static int TIE_STRIKER = 2;
    final static int TIE_INTERCEPTOR = 3;
    final static int DESTROYER = 4;

    private PathIterator pathIterator; // interface
    private Path2D path; // interface

    private int delta;
    private int type;
    private int healthDrop; // drops could be health/shields/powerup
    private int powerupDrop;

    public EnemyShip(int x, int y, int width, int height, int health, int type, Path2D p) {
        super(x, y, width, height, health);
        this.type = type;

        switch (type) {
            case (TIE):
                delta = 2;
                break;
            case (TIE_STRIKER):
                delta = 2;
                break;
            case (TIE_INTERCEPTOR):
                delta = 3;
                break;
            case (DESTROYER):
                delta = 1;
                break;
            default:
                delta = 2;
        }

        healthDrop = r.nextInt(CHANCE);
        powerupDrop = r.nextInt(CHANCE);
        path = p;
        pathIterator = path.getPathIterator(new AffineTransform());

        // create test path:
        x = y = 0;
        path.moveTo(x, y);
        for (int i = 0; i < 500; i++) {
            // path.lineTo(r.nextInt(XWing.SCREEN_WIDTH / 2), r.nextInt(XWing.SCREEN_HEIGHT
            // / 2));
            path.lineTo(x + i, y + i);
        }
        path.curveTo(x, y, XWing.SCREEN_WIDTH, XWing.SCREEN_HEIGHT / 2, XWing.SCREEN_WIDTH / 2,
                XWing.SCREEN_HEIGHT / 2);
        path.lineTo(XWing.SCREEN_WIDTH / 2, XWing.SCREEN_HEIGHT / 2);
        path.closePath();
        pathIterator = path.getPathIterator(new AffineTransform());
        FlatteningPathIterator aslkdfj;
    }

    @Override
    public void setPosX(int posX) {
        // TODO: complete method
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        // TODO: complete method
        this.posY = posY;

    }

    public int getType() {
        return type;
    }

    public boolean isHealthDrop() {
        return healthDrop == 47; // ;)
    }

    public boolean isPowerupDrop() {
        return powerupDrop == 47; // ;)
    }

    public Path2D getPath() {
        return path;
    }

    public void step() {
        if (pathIterator.isDone()) {
            // System.out.print("done at point: ");
            // System.out.println("(" + path.getCurrentPoint().getX() + ", " +
            // path.getCurrentPoint().getY() + ")");
            return;
        }
        pathIterator.next();
        double[] coords = new double[7];
        pathIterator.currentSegment(coords);
        // for (int i = 0; i < coords.length; i++) {
        // System.out.println("coord: " + coords[i]);
        // }
        path.moveTo(coords[0], coords[1]);
        // System.out.print("setting point to: ");
        // System.out.println("(" + path.getCurrentPoint().getX() + ", " +
        // path.getCurrentPoint().getY() + ")");
        setPosX((int) coords[0]);
        setPosY((int) coords[1]);
    }

    public int getDelta() {
        return delta;
    }

}