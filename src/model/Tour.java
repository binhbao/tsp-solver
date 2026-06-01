package model;

import java.util.ArrayList;
import java.util.List;

public class Tour {

    private List<Integer> path;
    private double cost;

    public Tour() {
        this.path = new ArrayList<>();
        this.cost = Double.MAX_VALUE;
    }

    public Tour(List<Integer> path, double cost) {
        this.path = new ArrayList<>(path);
        this.cost = cost;
    }

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

    /**
     * Fitness dùng cho Genetic Algorithm
     */
    public double getFitness() {
        if (cost == 0) {
            return Double.MAX_VALUE;
        }
        return 1.0 / cost;
    }

    /**
     * Tạo bản sao của Tour
     */
    public Tour copy() {
        return new Tour(path, cost);
    }

    /**
     * Thêm thành phố vào tour
     */
    public void addCity(int city) {
        path.add(city);
    }

    /**
     * Số thành phố trong tour
     */
    public int size() {
        return path.size();
    }

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
          .append(String.format("%.2f", cost));

        return sb.toString();
    }
}