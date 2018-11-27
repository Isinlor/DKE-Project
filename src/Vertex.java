import java.awt.*;

class Vertex {

    public int x;
    public int y;
    public Color color;

    public Vertex(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
    }

    public boolean hasOverlap(int x, int y, int vertexSize) {

        boolean hasHorizontalOverlap = Math.abs(this.x-x) <= vertexSize;

        boolean hasVerticalOverlap =  Math.abs(this.y-y) <= vertexSize;

        return hasVerticalOverlap && hasHorizontalOverlap;

    }

}
