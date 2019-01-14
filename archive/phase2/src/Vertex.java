import java.awt.*;

/**
 * Represents a vertex.
 *
 * Author: Tomek
 */
class Vertex {

    public int x;
    public int y;
    private Color color;

    public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasColor() {
        return color != null;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean hasOverlap(int x, int y, int vertexSize) {

        boolean hasHorizontalOverlap = Math.abs(this.x-x) <= vertexSize;

        boolean hasVerticalOverlap =  Math.abs(this.y-y) <= vertexSize;

        return hasVerticalOverlap && hasHorizontalOverlap;

    }

}
