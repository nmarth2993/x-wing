import java.awt.Rectangle;
import java.util.ArrayList;

public class Player extends Ship {
    final static int DELTA = 3;

    private int power;

    public Player() {
        super(1, XWing.SCREEN_HEIGHT / 2, XWing.xWingWidth, XWing.xWingHeight, 100);
        power = 10;
    }

    public void setPosX(int posX) {
        if (posX + XWing.xWingWidth + 1 > XWing.SCREEN_WIDTH) {
            posX = XWing.SCREEN_WIDTH - XWing.xWingWidth - 1;
        }
        if (posX <= 1) {
            posX = 1;
        }
        this.posX = posX;
        hitbox = new Rectangle(getPosX(), getPosY(), width, height);
    }

    public void setPosY(int posY) {
        if (posY + XWing.xWingWidth + 1 > XWing.SCREEN_HEIGHT) {
            posY = XWing.SCREEN_HEIGHT - XWing.xWingWidth - 1;
        }
        if (posY <= XWing.BORDER) {
            posY = XWing.BORDER + 1;
        }
        this.posY = posY;
        hitbox = new Rectangle(getPosX(), getPosY(), width, height);
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