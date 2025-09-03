package app.listeners;

import app.App;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseEventListener implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener {

    private final App app;

    public MouseEventListener(App app) {
        this.app = app;
    }

    @Override       //means clicked and released
    public void mouseClicked(MouseEvent e) {

    }

    @Override       //means just pressed
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            boolean doubleClick = event.getClickCount() == 2 && !event.isConsumed();
            app.leftClick(event.getX(), event.getY(), doubleClick);
        }
        if (event.getButton() == MouseEvent.BUTTON3) {
            app.rightClick(event.getX(), event.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent event) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (e.getPreciseWheelRotation() < 0) {
           // zoom in
        } else {
            // zoom out
        }
    }
}