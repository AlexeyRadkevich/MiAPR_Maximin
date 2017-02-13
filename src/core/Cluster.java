package core;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by AR on 11.02.2017.
 */
public class Cluster {
    private ArrayList<Point> points;

    private Point centroid;

    public Cluster() {
        this.points = new ArrayList<>();
        this.centroid = null;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
    }
}
