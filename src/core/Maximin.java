package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AR on 11.02.2017.
 */
public class Maximin {

    private int num_points;
    public ArrayList<Point> points;
    public ArrayList<Cluster> clusters;

    private final int MAX_X;
    private final int MAX_Y;

    private Double maxDistance;
    private Point newCentroid;

    public Maximin(int num_points, int maxX, int maxY) {
        MAX_X = maxX;
        MAX_Y = maxY;

        this.num_points = num_points;
        this.points = getRandomPoints();
        //          1

    }

    public void search(){
        this.clusters = new ArrayList<Cluster>();
        CreateFirstCluster();
        //          2
        createSecondCluster();
        //          3
        while (true) {
            clearClusters();
            assignClusters();//3
            if (!isSetNewCentroid())//4
                break;
        }
    }


    private boolean isSetNewCentroid() {
        double totalDistance = 0;
        ArrayList<Double> distances = new ArrayList<Double>();
        calcMaxDistance();
        for (int i = 0; i < clusters.size() - 1; i++) {
            for (int j = 1; j < clusters.size(); j++) {
                double currentDistance = getDistance(clusters.get(i).getCentroid(), clusters.get(j).getCentroid());
                distances.add(currentDistance);
                totalDistance += currentDistance;
            }

        }
        if (maxDistance > totalDistance / distances.size() / 2) {
            Cluster newCluster = new Cluster();
            newCluster.setCentroid(newCentroid);
            points.remove(points.indexOf(newCentroid));
            clusters.add(newCluster);
            return true;
        }
        return false;
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private void createSecondCluster() {
        double max = Double.MIN_VALUE;
        Point pointToCentroid = null;
        double distance = 0.0;
        Cluster cluster = clusters.get(0);
        Point centroid = cluster.getCentroid();
        for (Point point : points) {
            distance = getDistance(point, centroid);
            if (distance > max) {
                max = distance;
                pointToCentroid = point;
            }
        }
        points.remove(points.indexOf(pointToCentroid));
        Cluster newCluster = new Cluster();
        newCluster.setCentroid(pointToCentroid);
        clusters.add(newCluster);
    }

    private void calcMaxDistance() {
        double max = Double.MIN_VALUE;
        Point pointToCentroid = null;
        double distance = 0.0;
        for (Cluster cluster : clusters) {
            Point centroid = cluster.getCentroid();
            ArrayList<Point> points = cluster.getPoints();
            for (Point point : points) {
                distance = getDistance(point, centroid);
                if (distance > max) {
                    max = distance;
                    pointToCentroid = point;
                }
            }
        }
        maxDistance = max;
        newCentroid = pointToCentroid;
    }

    private void assignClusters() {
        int cluster = 0;
        double distance = 0;
        for (Point point : points) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < clusters.size(); i++) {
                Cluster c = clusters.get(i);
                distance = getDistance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            clusters.get(cluster).addPoint(point);
        }
    }

    private void CreateFirstCluster() {
        Cluster cluster = new Cluster();
        Point centroid = points.get(0);
        cluster.setCentroid(centroid);
        clusters.add(cluster);
        points.remove(0);
    }

    private double getDistance(Point point, Point centroid) {
        double x = centroid.getX() - point.getX();
        double y = centroid.getY() - point.getY();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    private ArrayList<Point> getRandomPoints() {
        Random rand = new Random();
        ArrayList<Point> res = new ArrayList<Point>(num_points);
        for (int i = 0; i < num_points; i++) {
            int x = rand.nextInt(MAX_X);
            int y = rand.nextInt(MAX_Y);
            res.add(new Point(x, y));
        }
        return res;
    }

}
