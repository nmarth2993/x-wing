import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class MouseListen implements MouseInputListener {

    Point mousePos;

    @Override
    public void mouseClicked(MouseEvent e) {
        // shoot
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // shooting = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
        // mouseClicked(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = e.getPoint();
    }

}