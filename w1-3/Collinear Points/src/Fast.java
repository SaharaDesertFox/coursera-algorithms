import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * User: stas
 * Date: 23.02.14 22:32
 */

public class Fast {
    private static Point[] points;

    public static void main(String[] args) {
        readPoints(args);
        fast();
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

    private static void fast() {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        HashMap<Point, ArrayList<Point>> lines = new HashMap<Point, ArrayList<Point>>();

        b:
        for (int i = 0; i < points.length - 3; i++) {
            Point p = points[i];
            Point[] slopePoints = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(slopePoints, p.SLOPE_ORDER);
            double currentSlope = Double.NaN;
            int count = 1;
            a:
            for (int j = 0; j < slopePoints.length; j++) {
                double slope = p.slopeTo(slopePoints[j]);
                if (slope == currentSlope) {
                    count++;
                } else {
                    if (count >= 3) {
//                        for (int k = i - 1; k >= 0; k--) {
//                            if (points[k].slopeTo(p) == currentSlope) {
//                                currentSlope = slope;
//                                count = 1;
//                                continue a;
//                            }
//                        }
                        Point last = slopePoints[j - 1];
                        Point beforeLast = slopePoints[j - 2];

                        ArrayList<Point> linePoints = lines.get(last);
                        if (linePoints != null && linePoints.contains(beforeLast)) {
                            currentSlope = slope;
                            count = 1;
                            continue;
                        }

                        if (linePoints == null) linePoints = new ArrayList<Point>();
                        linePoints.add(beforeLast);
                        lines.put(last, linePoints);

                        StdOut.print(p);
                        for (int k = j - count; k < j; k++) {
                            StdOut.print(" -> " + slopePoints[k]);
                        }
                        StdOut.println();
                        p.drawTo(last);
                    }
                    currentSlope = slope;
                    count = 1;
                }
            }
            if (count >= 3) {
                for (int k = i - 1; k >= 0; k--)
                    if (points[k].slopeTo(p) == currentSlope) {
                        continue b;
                    }
                StdOut.print(p);
                for (int k = slopePoints.length - count; k < slopePoints.length; k++) {
                    StdOut.print(" -> " + slopePoints[k]);
                }
                StdOut.println();
                p.drawTo(slopePoints[slopePoints.length - 1]);
            }

        }
        for (Point p : points) {
            p.draw();
        }
    }
}
