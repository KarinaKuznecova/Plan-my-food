package app.screens.components;

import app.App;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;

import java.awt.*;
import java.util.List;

public class Dropdown<T> implements Component, Clickable {

    private Rectangle rectangle;
    private List<T> options;
    private T selectedOption;
    private String placeholder;
    private boolean expanded;
    private Rectangle dropdownRectangle;

    public Dropdown(Rectangle rectangle, List<T> options, String placeholder) {
        this.rectangle = rectangle;
        this.options = options;
        this.placeholder = placeholder;
        this.expanded = false;
        this.selectedOption = null;
        
        // Calculate dropdown rectangle for expanded state
        int dropdownHeight = Math.min(options.size() * 30, 150); // Max 5 items visible
        this.dropdownRectangle = new Rectangle(
            rectangle.getX(), 
            rectangle.getY() + rectangle.getHeight(), 
            rectangle.getWidth(), 
            dropdownHeight
        );
    }

    @Override
    public void update(App app) {
        // Dropdown doesn't need continuous updates
    }

    @Override
    public void render(App app, RenderHandler renderHandler, Graphics2D graphics2D) {
        // Draw main dropdown box
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        
        // Draw selected text or placeholder
        String displayText = selectedOption != null ? selectedOption.toString() : placeholder;
        Color textColor = selectedOption != null ? Color.BLACK : Color.GRAY;
        graphics2D.setColor(textColor);
        
        int textY = rectangle.getY() + rectangle.getHeight() / 2 + 5;
        int textX = rectangle.getX() + 5;
        renderHandler.renderText(graphics2D, displayText, textX, textY);
        
        // Draw dropdown arrow
        graphics2D.setColor(Color.BLACK);
        int arrowX = rectangle.getX() + rectangle.getWidth() - 20;
        int arrowY = rectangle.getY() + rectangle.getHeight() / 2;
        
        if (expanded) {
            // Up arrow
            graphics2D.drawLine(arrowX, arrowY + 5, arrowX + 5, arrowY);
            graphics2D.drawLine(arrowX + 5, arrowY, arrowX + 10, arrowY + 5);
        } else {
            // Down arrow
            graphics2D.drawLine(arrowX, arrowY - 5, arrowX + 5, arrowY);
            graphics2D.drawLine(arrowX + 5, arrowY, arrowX + 10, arrowY - 5);
        }
        
        // Draw expanded options
        if (expanded) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(dropdownRectangle.getX(), dropdownRectangle.getY(), 
                               dropdownRectangle.getWidth(), dropdownRectangle.getHeight());
            
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawRect(dropdownRectangle.getX(), dropdownRectangle.getY(), 
                               dropdownRectangle.getWidth(), dropdownRectangle.getHeight());
            
            // Draw options
            graphics2D.setColor(Color.BLACK);
            for (int i = 0; i < options.size(); i++) {
                int optionY = dropdownRectangle.getY() + (i + 1) * 25;
                if (optionY > dropdownRectangle.getY() + dropdownRectangle.getHeight()) break;
                
                T option = options.get(i);
                
                // Highlight if this is the selected option
                if (option.equals(selectedOption)) {
                    graphics2D.setColor(Color.LIGHT_GRAY);
                    graphics2D.fillRect(dropdownRectangle.getX() + 1, optionY - 20, 
                                       dropdownRectangle.getWidth() - 2, 25);
                    graphics2D.setColor(Color.BLACK);
                }
                
                renderHandler.renderText(graphics2D, option.toString(), 
                                       dropdownRectangle.getX() + 5, optionY - 5);
            }
        }
    }

    @Override
    public void resize(App app) {
        // Dropdown doesn't need to resize automatically
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public boolean handleLeftMouseClick(App app, Rectangle mouseRectangle, boolean doubleClick) {
        if (mouseRectangle.intersects(rectangle)) {
            expanded = !expanded;
            return true;
        } else if (expanded && mouseRectangle.intersects(dropdownRectangle)) {
            // Click in dropdown area
            int relativeY = mouseRectangle.getY() - dropdownRectangle.getY();
            int optionIndex = relativeY / 25;
            
            if (optionIndex >= 0 && optionIndex < options.size()) {
                selectedOption = options.get(optionIndex);
                expanded = false;
                return true;
            }
        } else if (expanded) {
            // Click outside - close dropdown
            expanded = false;
            return false;
        }
        
        return false;
    }

    @Override
    public boolean handleRightMouseClick(App app, Rectangle mouseRectangle) {
        return false;
    }

    public T getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(T selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}