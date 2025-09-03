package app.graphics;

import app.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static app.constants.Colors.*;
import static app.constants.FilePath.*;

public class RenderHandler {

    private static final Logger logger = LoggerFactory.getLogger(RenderHandler.class);

    private App app;

    private Font fontLight;
    private Font fontBold;
    private Font fontRegular;

    public RenderHandler(App app) {
        this.app = app;
        try {
            loadFonts();
        } catch (IOException | FontFormatException exception) {
            logger.error("Exception with fonts!");
        }
    }

    private void loadFonts() throws FontFormatException, IOException {
        InputStream is = getClass().getResourceAsStream(FONT_LIGHT_PATH);
        if (is != null) {
            fontLight = Font.createFont(Font.TRUETYPE_FONT, is);
        } else {
            fontLight = Font.getFont(Font.SANS_SERIF);
        }

        is = getClass().getResourceAsStream(FONT_BOLD_PATH);
        if (is != null) {
            fontBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } else {
            fontBold = Font.getFont(Font.SANS_SERIF);
        }

        is = getClass().getResourceAsStream(FONT_REGULAR_PATH);
        if (is != null) {
            fontRegular = Font.createFont(Font.TRUETYPE_FONT, is);
        } else {
            fontRegular = Font.getFont(Font.SANS_SERIF);
        }
    }

    public void renderBackground(Graphics2D graphics2D) {
        renderBackground(graphics2D, bgColorForUI, app.getWidth(), app.getHeight());
    }

    public void renderBackground(Graphics2D graphics2D, Color color) {
        renderBackground(graphics2D, color, app.getWidth(), app.getHeight());
    }

    public void renderBackground(Graphics2D graphics2D, Color color, int width, int height) {
        graphics2D.setColor(color);
        graphics2D.fillRect(0, 0, width, height);
    }

    public int getLineLength(Graphics2D graphics2D, String text) {
        return getLineLength(graphics2D, text, 20F);
    }

    public int getLineLength(Graphics2D graphics2D, String text, float fontSize) {
        graphics2D.setFont(getFontRegular().deriveFont(fontSize));
        return (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
    }

    public void renderText(Graphics2D graphics2D, String text, int x, int y) {
        renderText(graphics2D, text, x, y, 20F);
    }

    public void renderText(Graphics2D graphics2D, String text, int x, int y, float fontSize) {
        graphics2D.setColor(textColorForUI);
        graphics2D.setFont(getFontRegular().deriveFont(fontSize));
        graphics2D.drawString(text, x, y);
    }

    public void drawFrame(Graphics2D graphics2D, int x, int y, int width, int height) {
        drawFrame(graphics2D, x, y, width, height, frameColorForUI, 3);
    }

    public void drawFrame(Graphics2D graphics2D, int x, int y, int width, int height, int strokeWidth) {
        drawFrame(graphics2D, x, y, width, height, frameColorForUI, strokeWidth);
    }

    public void drawFrame(Graphics2D graphics2D, int x, int y, int width, int height, Color frameColor) {
        drawFrame(graphics2D, x, y, width, height, frameColor, 3);
    }

    public void drawFrame(Graphics2D graphics2D, int x, int y, int width, int height, Color frameColor, int strokeWidth) {
        drawFrame(graphics2D, x, y, width, height, frameColor, strokeWidth, 120);
    }

    public void drawFrame(Graphics2D graphics2D, int x, int y, int width, int height, Color frameColor, int strokeWidth, int alpha) {
        if (alpha == 0) {
            drawFrame(graphics2D, x, y, width, height, frameColor, null, strokeWidth);
        }
        Color bgColor = new Color(bgColorForUI.getRed(), bgColorForUI.getGreen(), bgColorForUI.getBlue(), alpha);
        drawFrame(graphics2D, x, y, width, height, frameColor, bgColor, strokeWidth);
    }

    public void drawFrame(Graphics2D graphics2D, int x, int y, int width, int height, Color frameColor, Color bgColor, int strokeWidth) {
        if (bgColor != null) {
            graphics2D.setColor(bgColor);
            graphics2D.fillRoundRect(x, y, width, height, strokeWidth * 7, strokeWidth * 7);
        }

        graphics2D.setColor(frameColor);
        graphics2D.setStroke(new BasicStroke(strokeWidth));
        graphics2D.drawRoundRect(x + strokeWidth, y + strokeWidth, width - (strokeWidth * 2), height - (strokeWidth * 2), strokeWidth * 5, strokeWidth * 5);
    }

    public void renderTextInTheMiddleOnDarkRect(App app, Graphics2D graphics2D, String text) {
        Color color = new Color(3, 27, 65, 60);
        graphics2D.setColor(color);

        graphics2D.fillRect(0, 0, app.getWidth(), app.getHeight());

        renderTextInTheMiddle(app, graphics2D, text);
    }

    public void renderTextInTheMiddle(App app, Graphics2D graphics2D, String text) {
        graphics2D.setColor(underlineColor);

        graphics2D.setFont(fontBold.deriveFont(48F));

        int lineLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        graphics2D.drawString(text, app.getWidth() / 2 - lineLength / 2, app.getHeight() / 2 - 100);

        graphics2D.setColor(pauseTextColor);
        graphics2D.drawString(text, app.getWidth() / 2 - lineLength / 2 - 2, app.getHeight() / 2 - 102);
    }


    public BufferedImage tintImage(BufferedImage image, Color color) {
        return tintImage(image, color, 1f);
    }

    public BufferedImage tintImage(BufferedImage image, Color color, float alpha) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage tinted = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tinted.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        return tinted;
    }

    public BufferedImage tintImageLightParts(BufferedImage image, Color color) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage tinted = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                // Extract RGB components
                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // Calculate brightness (simple average of RGB)
                float brightness = (red + green + blue) / 3.0f / 255.0f;
//                brightness = (float) Math.sqrt(brightness);
                brightness = (float) Math.pow(brightness, 0.7);

                // Adjust tint intensity based on brightness (affect light tones more)
                int tintRed = (int) (color.getRed() * brightness);
                int tintGreen = (int) (color.getGreen() * brightness);
                int tintBlue = (int) (color.getBlue() * brightness);

                // Combine original color with the tint
                int newRed = Math.min(255, tintRed);
                int newGreen = Math.min(255, tintGreen);
                int newBlue = Math.min(255, tintBlue);

                // Set the new pixel color
                int newPixel = (alpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                tinted.setRGB(x, y, newPixel);
            }
        }

        return tinted;
    }

    public BufferedImage joinImages(List<BufferedImage> images) {
        BufferedImage firstImage = images.get(0);
        int width = firstImage.getWidth();
        int height = firstImage.getHeight();
        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = combined.createGraphics();
        for (BufferedImage image : images) {
            g2.drawImage(image, null, 0, 0);
        }
        g2.dispose();
        return combined;
    }

    public Font getFontLight() {
        return fontLight;
    }

    public Font getFontBold() {
        return fontBold;
    }

    public Font getFontRegular() {
        return fontRegular;
    }

    public App getApp() {
        return app;
    }
}
