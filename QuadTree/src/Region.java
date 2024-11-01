import java.util.ArrayList;
import java.util.List;

public class Region{
    List<Point> points = new ArrayList<>();
    int x1,y1;
    int x2,y2;
    Region(){}
    Region(int x1, int y1, int x2, int y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }

    @Override
    public String toString() {
        return "Region{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean contains(Point point) {

        return point.getX() >= this.x1
                && point.getX() < this.x2
                && point.getY() >= this.y1
                && point.getY() < this.y2;
    }

    public boolean insideBoundary(Point point){
        return this.x1 <= point.getX() && this.y1 <= point.getY() && this.x2 >= point.getX() &&  this.y2 >= point.getY();
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }
    Region createSubRegion(int i){
        int x1 = this.x1;
        int y1 = this.y1;
        int x2 = this.x2;
        int y2 = this.y2;
        Region subRegion = new Region();
        switch (i){
            case 0:
                subRegion.setBoundary(x1,y1,(x1+x2)/2,(y1+y2)/2);
                break;
            case 1:
                subRegion.setBoundary((x1+x2)/2,y1, x2, (y1+y2)/2);
                break;
            case 2:
                subRegion.setBoundary((x1+x2)/2,(y1+y2)/2,x2,y2);
                break;
            default:
                subRegion.setBoundary(x1,(y1+y2)/2,(x1+x2)/2,y2);
        }
        return subRegion;
    }

    private void setBoundary(int x1, int y1, int x2, int y2) {
        this.x1  = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean doesOverlap(Region testRegion) {
        if (testRegion.getX2() < this.getX1()) {
            return false;
        }
        // Is test region completely to right of my region?
        if (testRegion.getX1() > this.getX2()) {
            return false;
        }
        // Is test region completely above my region?
        if (testRegion.getY1() > this.getY2()) {
            return false;
        }
        // Is test region completely below my region?
        if (testRegion.getY2() < this.getY1()) {
            return false;
        }
        return true;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}