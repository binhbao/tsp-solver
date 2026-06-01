package algorithms;

import model.TSPProblem;
import model.Tour;

import java.util.ArrayList;
import java.util.List;

public class Greedy implements TSPSolver {

    @Override
    public String getName() {
        return "Greedy";
    }

    @Override
    public Tour solve(TSPProblem problem) {

        int n = problem.size();

        boolean[] visited = new boolean[n];

        List<Integer> path = new ArrayList<>();

        int currentCity = 0;

        visited[currentCity] = true;
        path.add(currentCity);

        double totalCost = 0.0;

        // Chọn thành phố gần nhất chưa thăm
        for (int step = 1; step < n; step++) {

            int nextCity = -1;
            double minDistance = Double.MAX_VALUE;

            for (int city = 0; city < n; city++) {

                if (!visited[city]) {

                    double distance =
                            problem.getDistance(currentCity, city);

                    if (distance < minDistance) {
                        minDistance = distance;
                        nextCity = city;
                    }
                }
            }

            visited[nextCity] = true;
            path.add(nextCity);

            totalCost += minDistance;

            currentCity = nextCity;
        }

        // Quay về thành phố xuất phát
        totalCost += problem.getDistance(
                currentCity,
                path.get(0)
        );

        return new Tour(path, totalCost);
    }
}