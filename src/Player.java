import java.util.ArrayList;

public class Player extends Ship {
    final static int WIDTH = 20;
    final static int HEIGHT = 20;
    final static int DELTA = 3;

    private int power;

    public Player() {
        super(1, XWing.SCREEN_HEIGHT / 2, WIDTH, HEIGHT, 100);
        power = 10;
    }

    public void setPosX(int posX) {
        if (posX + Player.WIDTH + 1 > XWing.SCREEN_WIDTH) {
            posX = XWing.SCREEN_WIDTH - Player.HEIGHT - 1;
        }
        if (posX <= 1) {
            posX = 1;
        }
        this.posX = posX;
    }

    public void setPosY(int posY) {
        if (posY + Player.HEIGHT + 1 > XWing.SCREEN_HEIGHT) {
            posY = XWing.SCREEN_HEIGHT - Player.HEIGHT - 1;
        }
        if (posY <= XWing.BORDER) {
            posY = XWing.BORDER + 1;
        }
        this.posY = posY;
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