package exercise;

// BEGIN

public class Segment {
    private Point beginPoint;
    private Point endPoint;

    Segment (Point beginPoint, Point endPoint) {
        this.beginPoint = beginPoint;
        this.endPoint = endPoint;
    }

    public Point getBeginPoint() {
        return beginPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Point getMidPoint() {
        return new Point(
                beginPoint.getY() + (endPoint.getX() - beginPoint.getY()) / 2,
                beginPoint.getY() + (endPoint.getY() - beginPoint.getY()) / 2
        );
    }
}
// END
