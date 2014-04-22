import java.util.Comparator;

/**
 * User: stas
 * Date: 23.02.14 21:31
 */

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point p1, Point p2) {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);
            if (s1 == s2) return 0;
            else if (s1 < s2) return -1;
            else return 1;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        double slope = ((double) (that.y - y)) / (that.x - x);
        if (slope == Double.NEGATIVE_INFINITY) slope = Double.POSITIVE_INFINITY;
        else if (slope == -0.0d) slope = +0.0d;
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (x == that.x && y == that.y) return 0;
        else if (y < that.y || (y == that.y && x < that.x)) return -1;
        else return 1;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    public static void main(String[] args) {
//        double s1 = Double.NEGATIVE_INFINITY;
//        double s2 = Double.POSITIVE_INFINITY;
//        double s5 = Double.POSITIVE_INFINITY;
        double s3 = 0.0;
        double s4 = -0.0;
//        double s6 = Double.NaN;
//        double s7 = Double.NaN;
        StdOut.println(s4 < s3);
        StdOut.println(s4);
        StdOut.println(s3);
        s3 = +0.0;
        StdOut.println(s3);
    }
}