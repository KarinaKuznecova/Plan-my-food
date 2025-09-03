package app.graphics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Rectangle {

    protected static final Logger logger = LoggerFactory.getLogger(Rectangle.class);

    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean halfIntersects(Rectangle otherRectangle) {
        if (otherRectangle == null) {
            return false;
        }
        return halfIntersectsByX(otherRectangle) && halfIntersectsByY(otherRectangle);
    }

    private boolean halfIntersectsByX(Rectangle otherRectangle) {
        return Math.abs(x - otherRectangle.getX()) < width / 2f;
    }

    private boolean halfIntersectsByY(Rectangle otherRectangle) {
        return Math.abs(y - otherRectangle.getY()) < width / 2f;
    }

    public boolean fullyIntersects(Rectangle otherRectangle) {
        if (otherRectangle == null) {
            return false;
        }
        return fullyIntersectsByX(otherRectangle) && fullyIntersectsByY(otherRectangle);
    }

    private boolean fullyIntersectsByX(Rectangle otherRectangle) {
        return Math.abs(x - otherRectangle.getX()) < 4;
    }

    private boolean fullyIntersectsByY(Rectangle otherRectangle) {
        return Math.abs(y - otherRectangle.getY()) < 4;
    }

    public boolean intersects(Rectangle otherRectangle) {
        if (otherRectangle == null) {
            return false;
        }
        return (intersectsByX(otherRectangle) && intersectsByY(otherRectangle));
    }

    private boolean intersectsByY(Rectangle otherRectangle) {
        return !(y >= otherRectangle.getY() + otherRectangle.getHeight() || otherRectangle.getY() >= y + height);
    }

    private boolean intersectsByX(Rectangle otherRectangle) {
        return !(x >= otherRectangle.getX() + otherRectangle.getWidth() || otherRectangle.getX() >= x + width);
    }

    public boolean intersectsALot(Rectangle otherRectangle) {
        if (otherRectangle == null) {
            return false;
        }
        return (intersectsALotByY(otherRectangle) && intersectsALotByX(otherRectangle));
    }

    private boolean intersectsALotByY(Rectangle otherRectangle) {
        return Math.abs(y - otherRectangle.getY()) < 6f;
    }

    private boolean intersectsALotByX(Rectangle otherRectangle) {
        return Math.abs(x - otherRectangle.getX()) < 6f;
    }

    public boolean intersectsALittle(Rectangle otherRectangle) {
        if (otherRectangle == null) {
            return false;
        }
        return (intersectsALittleByY(otherRectangle) && intersectsALittleByX(otherRectangle));
    }

    private boolean intersectsALittleByY(Rectangle otherRectangle) {
        return Math.abs(y - otherRectangle.getY()) < width - 4f;
    }

    private boolean intersectsALittleByX(Rectangle otherRectangle) {
        return Math.abs(x - otherRectangle.getX()) < width - 4f;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle copy() {
        return new Rectangle(x, y, width, height);
    }

    public void alignToGrid() {
        if (getX() % 32 != 0) {
            logger.info("aligning x");
            if (getX() % 32 < 16) {
                setX(getX() - (getX() % 32));
            } else {
                setX(getX() + (32 - (getX() % 32)));
            }
        }
        if (getY() % 32 != 0) {
            logger.info("aligning y");
            if (getY() % 32 < 16) {
                setY(getY() - (getY() % 32));
            } else {
                setY(getY() + (32 - (getY() % 32)));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Float.compare(x, rectangle.x) == 0 && Float.compare(y, rectangle.y) == 0 && width == rectangle.width && height == rectangle.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }
}
