import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseListen implements MouseMotionListener {
    private XWing game;

    public MouseListen(XWing game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // game.setMouseCoords(e.getX() - 8, e.getY() - 31);
        // there's some really
        // strange offset
        game.setMouseCoords(e.getX(), e.getY());
    }

}