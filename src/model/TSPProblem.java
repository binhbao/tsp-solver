package model;

import java.util.List;

public class TSPProblem {

    private final List<City> cities;
    private final double[][] distanceMatrix;

    public TSPProblem(List<City> cities) {
        this.cities = cities;
        this.distanceMatrix = buildDistanceMatrix();
    }

    // ======================
    // BUILD DISTANCE MATRIX
    // ======================
    private double[][] buildDistanceMatrix() {

        int n = cities.size();
        double[][] dist = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = euclidean(
                            cities.get(i),
                            cities.get(j)
                    );
                }
            }
        }

        return dist;
    }

    // ======================
    // EUCLIDEAN DISTANCE
    // ======================
    private double euclidean(City a, City b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // ======================
    // API FOR ALGORITHMS
    // ======================

    public int size() {
        return cities.size();
    }

    public List<City> getCities() {
        return cities;
    }

    public City getCity(int index) {
        return cities.get(index);
    }

    public double getDistance(int i, int j) {
        return distanceMatrix[i][j];
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}