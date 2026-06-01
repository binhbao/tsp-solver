package utils;

import model.City;

public final class DistanceUtil {

    private DistanceUtil() {
        // Prevent instantiation
    }

    /**
     * Tính khoảng cách Euclidean giữa 2 thành phố
     */
    public static double distance(City a, City b) {

        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }
}