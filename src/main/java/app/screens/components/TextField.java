package app.screens.components;

import app.App;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextField implements Component, Clickable {

    private Rectangle rectangle;
    private String text;
    private String placeholder;
    private boolean focused;
    private int cursorPosition;

    public TextField(Rectangle rectangle, String placeholder) {
        this.rectangle = rectangle;
        this.text = "";
        this.placeholder = placeholder;
        this.focused = false;
        this.cursorPosition = 0;
    }

    @Override
    public void update(App app) {
        // Text field doesn't need continuous updates
    }

    @Override
    public void render(App app, RenderHandler renderHandler, Graphics2D graphics2D) {
        // Draw background
        Color bgColor = focused ? Color.WHITE : Color.LIGHT_GRAY;
        graphics2D.setColor(bgColor);
        graphics2D.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        
        // Draw border
        graphics2D.setColor(focused ? Color.BLUE : Color.GRAY);
        graphics2D.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        
        // Draw text or placeholder
        graphics2D.setColor(Color.BLACK);
        String displayText = text.isEmpty() ? placeholder : text;
        Color textColor = text.isEmpty() ? Color.GRAY : Color.BLACK;
        graphics2D.setColor(textColor);
        
        int textY = rectangle.getY() + rectangle.getHeight() / 2 + 5;
        int textX = rectangle.getX() + 5;
        renderHandler.renderText(graphics2D, displayText, textX, textY);
        
        // Draw cursor if focused
        if (focused && !text.isEmpty()) {
            graphics2D.setColor(Color.BLACK);
            int cursorX = textX + renderHandler.getLineLength(graphics2D, text.substring(0, Math.min(cursorPosition, text.length())));
            graphics2D.drawLine(cursorX, rectangle.getY() + 5, cursorX, rectangle.getY() + rectangle.getHeight() - 5);
        }
    }

    @Override
    public void resize(App app) {
        // TextField doesn't need to resize automatically
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public boolean handleLeftMouseClick(App app, Rectangle mouseRectangle, boolean doubleClick) {
        if (mouseRectangle.intersects(rectangle)) {
            focused = true;
            cursorPosition = text.length(); // Move cursor to end
            return true;
        } else {
            focused = false;
            return false;
        }
    }

    @Override
    public boolean handleRightMouseClick(App app, Rectangle mouseRectangle) {
        return false;
    }

    public void handleKeyInput(char keyChar, int keyCode) {
        if (!focused) return;

        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (cursorPosition > 0) {
                text = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
                cursorPosition--;
            }
        } else if (keyCode == KeyEvent.VK_DELETE) {
            if (cursorPosition < text.length()) {
                text = text.substring(0, cursorPosition) + text.substring(cursorPosition + 1);
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (cursorPosition > 0) {
                cursorPosition--;
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            if (cursorPosition < text.length()) {
                cursorPosition++;
            }
        } else if (keyCode == KeyEvent.VK_HOME) {
            cursorPosition = 0;
        } else if (keyCode == KeyEvent.VK_END) {
            cursorPosition = text.length();
        } else if (Character.isLetterOrDigit(keyChar) || keyChar == ' ' || keyChar == '-' || keyChar == '_') {
            text = text.substring(0, cursorPosition) + keyChar + text.substring(cursorPosition);
            cursorPosition++;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text != null ? text : "";
        this.cursorPosition = this.text.length();
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}