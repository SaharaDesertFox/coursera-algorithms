import java.util.Arrays;

/**
 * User: stas
 * Date: 23.02.14 21:54
 */

public class Brute {

    private static Point[] points;

    public static void main(String[] args) {
        readPoints(args);
        brute();
    }

    private static void readPoints(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        Arrays.sort(points);
    }

    private static void brute() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < points.length - 3; i++)
            for (int j = i + 1; j < points.length - 2; j++)
                for (int k = j + 1; k < points.length - 1; k++)
                    for (int m = k + 1; m < points.length; m++) {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[m];

                        if ((p1.slopeTo(p2) == p2.slopeTo(p3)) && (p2.slopeTo(p3) == p3.slopeTo(p4))) {
                            StdOut.println(p1 + " -> " + p2 + " -> " + p3 + " -> " + p4);
                            p1.drawTo(p4);
                        }
                    }
        for (Point p : points) {
            p.draw();
        }
    }

}
