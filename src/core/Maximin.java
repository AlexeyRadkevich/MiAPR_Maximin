package core;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AR on 11.02.2017.
 */
public class Maximin {

    private int num_points;
    public ArrayList<Point> arr_points;
    public ArrayList<Cluster> arr_clusters;

    public Maximin(int num_points) {
        this.num_points = num_points;
        arr_points = getRandomPoints();

        //          1
        arr_clusters = new ArrayList<Cluster>();
        createNewCluster(0);

        //          2
        calcDistances(arr_points, arr_clusters.get(0));
        getNewCluster(arr_clusters);
        //          3
        // while (check()){
        assignCluster();
        //          4
        for (Cluster cluster : arr_clusters) {
            cluster.clearDistances();
            calcDistances(cluster.getPoints(), cluster);
            System.out.println(cluster.getMaxDistance());
        }

        getNewCluster(arr_clusters);
        //} ;

    }

    private boolean check() {

        return true;
    }

    private void assignCluster() {
        int cluster = 0;
        double distance = 0;
        for (Point point : arr_points) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < arr_clusters.size(); i++) {
                Cluster c = arr_clusters.get(i);
                distance = getDistance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            arr_clusters.get(cluster).addPoint(point);
        }
    }

    private void calcDistances(ArrayList<Point> points, Cluster cluster) {
        double distance;
        Point centroid = cluster.getCentroid();
        for (Point point : points) {
            distance = getDistance(point, centroid);
            cluster.addDistance(distance);
        }
    }

    private void getNewCluster(ArrayList<Cluster> clusters) {
        ArrayList<Double> arr = clusters.get(0).getDistance();
        double max = clusters.get(0).getMaxDistance();
        if (clusters.size() >= 2) {
            for (Cluster cluster : clusters) {
                if (cluster.getMaxDistance() > max) {
                    max = cluster.getMaxDistance();
                    arr = cluster.getDistance();
                }
            }

        }
        createNewCluster(arr.indexOf(max));
    }

    private void createNewCluster(int index) {
        Cluster cluster = new Cluster();
        Point centroid = arr_points.get(index);
        cluster.setCentroid(centroid);
        arr_clusters.add(cluster);
        arr_points.remove(index);
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
            int x = rand.nextInt(640);
            int y = rand.nextInt(480);
            res.add(new Point(x, y));
        }
        return res;
    }

}
