package app.listeners;

import app.App;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener  implements KeyListener, FocusListener {

    private App app;

    private boolean[] keys = new boolean[600];

    public KeyboardListener(App app) {
        this.app = app;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode < keys.length) {
            keys[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode < keys.length) {
            keys[keyCode] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void focusGained(FocusEvent event) {

    }

    @Override
    public void focusLost(FocusEvent event) {

    }

    // Specific key checks

    public boolean ctrl() {
        return keys[KeyEvent.VK_CONTROL];
    }

    public boolean shift() {
        return keys[KeyEvent.VK_SHIFT];
    }

    private static boolean isProperCharForTyping(KeyEvent event) {
        return Character.isAlphabetic(event.getKeyChar()) || Character.isDigit(event.getKeyChar()) || Character.isSpaceChar(event.getKeyChar());
    }
}
