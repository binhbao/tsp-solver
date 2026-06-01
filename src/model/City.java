package model;

import java.util.Objects;

public class City {

    private final int id;
    private final double x;
    private final double y;

    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // ======================
    // GETTERS
    // ======================
    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // ======================
    // EQUALITY (QUAN TRỌNG CHO GA / SET)
    // ======================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ======================
    // DEBUG PRINT
    // ======================
    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}