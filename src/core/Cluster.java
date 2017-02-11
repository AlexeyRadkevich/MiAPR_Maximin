package core;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by AR on 11.02.2017.
 */
public class Cluster {
    private ArrayList<Point> points;
    private ArrayList<Double> arr_distances;

    private Point centroid;

    public Cluster() {
        this.points = new ArrayList<>();
        this.arr_distances = new ArrayList<>();
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

    public void clearPoints() {
        points.clear();
    }

    public void addDistance(double distance) {
        arr_distances.add(distance);
    }

    public ArrayList<Double> getDistance() {
        return arr_distances;
    }


    public void clearDistances() {
        arr_distances.clear();
    }

    public double getMaxDistance() {
        double max = arr_distances.get(0);
        for (int i = 0; i < arr_distances.size(); i++) {
            if (arr_distances.get(i) > max) {
                max = arr_distances.get(i);
            }
        }
        return max;
    }
}
