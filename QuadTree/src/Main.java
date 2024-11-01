import java.util.ArrayList;
import java.util.List;

class QuadTree{
    private static QuadTree instance;
    Region region;
     List<QuadTree> quadTrees = new ArrayList<>();
    int max_points = 3;
    StringBuilder traversePath;
    private QuadTree(){}
    public static QuadTree getInstance(){
        if(instance==null){
            synchronized (QuadTree.class){
                if(instance == null){
                    instance = new QuadTree();

                }
            }
        }
        return instance;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public boolean addPoint(Point point) {

        if(this.region.contains(point)){
            if(this.region.insideBoundary(point) && this.region.getPoints().size() < max_points){
                this.region.addPoint(point);
                System.out.println("inserted "+ point +" in the region ="+region);
                return true;
            }else{
                if(quadTrees.size() == 0){
                    createQuadrants();
                }
                return addPointToOneQuadrant(point);

            }
        }
        return  false;
    }

    private boolean addPointToOneQuadrant(Point point) {
        for(int i=0;i<4;i++){
            QuadTree q = quadTrees.get(i);
            boolean isPointAdded = q.addPoint(point);
            if(isPointAdded)
                return true;
        }
        return false;
    }

    private  void createQuadrants() {

        for(int i=0;i<4;i++){
            quadTrees.add(new QuadTree());
            System.out.println("Creating sub region for Region " +this.region);
            quadTrees.get(i).setRegion(this.region.createSubRegion(i));
        }
    }

    public List<Point> search(Region searchArea, List<Point> matches, String depthIndicator) {
        traversePath = new StringBuilder("");
        if(matches == null){
            matches = new ArrayList<Point>();
             traversePath.append(depthIndicator).append("Search Boundary =").append(searchArea).append("\n");
        }
        if(!this.region.doesOverlap(searchArea)){
             return matches;
        }else{
            for(Point p: this.region.getPoints()){
                if(searchArea.contains(p)){
                    traversePath.append(depthIndicator).append("Found match =").append(p).append(" \n");
                    matches.add(p);
                }
            }
            if(this.quadTrees.size() >0){
                for(QuadTree q: this.quadTrees){
                    traversePath.append(depthIndicator).append("Quadrant: ").append(q.getRegion()).append("\n");
                    q.search(searchArea, matches, depthIndicator + "\t");
                    traversePath.append(q.printSearchTraversePath());
                }
            }
        }
        return matches;
    }
    public String printSearchTraversePath() {
        return traversePath.toString();
    }

    public void printRegionsWithPoints(String depthIndicator) {
        System.out.println(depthIndicator + "Region: " + this.region + ", Points Count: " + this.region.getPoints().size());
        if (this.quadTrees.size() > 0) {
            for (QuadTree q : this.quadTrees) {
                q.printRegionsWithPoints(depthIndicator + "\t");
            }
        }
    }

}
public class Main {
    public static void main(String[] args) {
        Region area = new Region(0, 0, 400, 400);

        QuadTree quadTree = QuadTree.getInstance();
        quadTree.setRegion(area);

        float[][] points = new float[][] {
                { 21, 25 }, { 55, 53 }, { 70, 318 }, { 98, 302 },
                { 49, 229 }, { 135, 229 }, { 224, 292 }, { 206, 321 },
                { 197, 258 }, { 245, 238 }
        };
        for(int i=0;i<points.length;i++){
            Point point = new Point(points[i][0],points[i][1]);
            quadTree.addPoint(point);
        }

        System.out.println("\n--- Region Points Distribution ---");
        quadTree.printRegionsWithPoints("");
        System.out.println("\n--- ----------------------------------------- ---");

        // Define the search region within the QuadTree
        Region searchArea = new Region(200, 200, 250, 250);

        // Perform search
        List<Point> result = quadTree.search(searchArea, null, "");

        // Print the search traversal path
        System.out.println("Search Traversal Path:");
         System.out.println(quadTree.printSearchTraversePath());

        // Print the search results (points within the search area)
        System.out.println("Points within the search area:");
        for (Point p : result) {
            System.out.println(p);
        }
    }
}

/*
*
* Connected to the target VM, address: '127.0.0.1:56531', transport: 'socket'
inserted Point{x=21.0, y=25.0} in the region =Region{x1=0, y1=0, x2=400, y2=400}
inserted Point{x=55.0, y=53.0} in the region =Region{x1=0, y1=0, x2=400, y2=400}
inserted Point{x=70.0, y=318.0} in the region =Region{x1=0, y1=0, x2=400, y2=400}
Creating sub region for Region Region{x1=0, y1=0, x2=400, y2=400}
Creating sub region for Region Region{x1=0, y1=0, x2=400, y2=400}
Creating sub region for Region Region{x1=0, y1=0, x2=400, y2=400}
Creating sub region for Region Region{x1=0, y1=0, x2=400, y2=400}
inserted Point{x=98.0, y=302.0} in the region =Region{x1=0, y1=200, x2=200, y2=400}
inserted Point{x=49.0, y=229.0} in the region =Region{x1=0, y1=200, x2=200, y2=400}
inserted Point{x=135.0, y=229.0} in the region =Region{x1=0, y1=200, x2=200, y2=400}
inserted Point{x=224.0, y=292.0} in the region =Region{x1=200, y1=200, x2=400, y2=400}
inserted Point{x=206.0, y=321.0} in the region =Region{x1=200, y1=200, x2=400, y2=400}
Creating sub region for Region Region{x1=0, y1=200, x2=200, y2=400}
Creating sub region for Region Region{x1=0, y1=200, x2=200, y2=400}
Creating sub region for Region Region{x1=0, y1=200, x2=200, y2=400}
Creating sub region for Region Region{x1=0, y1=200, x2=200, y2=400}
inserted Point{x=197.0, y=258.0} in the region =Region{x1=100, y1=200, x2=200, y2=300}
inserted Point{x=245.0, y=238.0} in the region =Region{x1=200, y1=200, x2=400, y2=400}

--- Region Points Distribution ---
Region: Region{x1=0, y1=0, x2=400, y2=400}, Points Count: 3
	Region: Region{x1=0, y1=0, x2=200, y2=200}, Points Count: 0
	Region: Region{x1=200, y1=0, x2=400, y2=200}, Points Count: 0
	Region: Region{x1=200, y1=200, x2=400, y2=400}, Points Count: 3
	Region: Region{x1=0, y1=200, x2=200, y2=400}, Points Count: 3
		Region: Region{x1=0, y1=200, x2=100, y2=300}, Points Count: 0
		Region: Region{x1=100, y1=200, x2=200, y2=300}, Points Count: 1
		Region: Region{x1=100, y1=300, x2=200, y2=400}, Points Count: 0
		Region: Region{x1=0, y1=300, x2=100, y2=400}, Points Count: 0

--- ----------------------------------------- ---
Search Traversal Path:
Search Boundary =Region{x1=200, y1=200, x2=250, y2=250}
Quadrant: Region{x1=0, y1=0, x2=200, y2=200}
Quadrant: Region{x1=200, y1=0, x2=400, y2=200}
Quadrant: Region{x1=200, y1=200, x2=400, y2=400}
	Found match =Point{x=245.0, y=238.0}
Quadrant: Region{x1=0, y1=200, x2=200, y2=400}
	Quadrant: Region{x1=0, y1=200, x2=100, y2=300}
	Quadrant: Region{x1=100, y1=200, x2=200, y2=300}
	Quadrant: Region{x1=100, y1=300, x2=200, y2=400}
	Quadrant: Region{x1=0, y1=300, x2=100, y2=400}

Points within the search area:
Point{x=245.0, y=238.0}
* */