package model;

import java.util.ArrayList;
import java.util.List;

public class Tour {

    // lưu index của city trong TSPProblem
    private List<Integer> path;

    // tổng chi phí tour
    private double cost;

    public Tour() {
        this.path = new ArrayList<>();
        this.cost = Double.MAX_VALUE;
    }

    public Tour(List<Integer> path, double cost) {
        this.path = new ArrayList<>(path);
        this.cost = cost;
    }

    // ======================
    // GETTERS / SETTERS
    // ======================
    public List<Integer> getPath() {
        return path;
    }

    public void setPath(List<Integer> path) {
        this.path = new ArrayList<>(path);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // ======================
    // UTILITY METHODS
    // ======================

    public void addCity(int cityIndex) {
        path.add(cityIndex);
    }

    public void clear() {
        path.clear();
        cost = Double.MAX_VALUE;
    }

    public int size() {
        return path.size();
    }

    public Tour copy() {
        return new Tour(new ArrayList<>(path), cost);
    }

    // ======================
    // VALIDATION
    // ======================
    public boolean isComplete(int n) {
        return path.size() == n;
    }

    // ======================
    // PRINT FORMAT
    // ======================
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Tour: ");

        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));

            if (i < path.size() - 1) {
                sb.append(" -> ");
            }
        }

        if (!path.isEmpty()) {
            sb.append(" -> ").append(path.get(0));
        }

        sb.append("\nCost: ")
          .append(String.format("%.4f", cost));

        return sb.toString();
    }
}