package algorithms;

import model.TSPProblem;
import model.Tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SA implements TSPSolver {

    private static final double INITIAL_TEMPERATURE = 1000.0;
    private static final double MIN_TEMPERATURE = 1.0;
    private static final double COOLING_RATE = 0.995;

    private final Random random = new Random();

    @Override
    public String getName() {
        return "Simulated Annealing";
    }

    @Override
    public Tour solve(TSPProblem problem) {

        // Khởi tạo bằng Greedy
        Tour currentTour = new Greedy().solve(problem);
        Tour bestTour = currentTour.copy();

        double temperature = INITIAL_TEMPERATURE;

        while (temperature > MIN_TEMPERATURE) {

            Tour neighbor = generateNeighbor(currentTour, problem);

            double currentCost = currentTour.getCost();
            double neighborCost = neighbor.getCost();

            double delta = neighborCost - currentCost;

            // Nếu tốt hơn -> nhận luôn
            if (delta < 0) {

                currentTour = neighbor;

                if (neighborCost < bestTour.getCost()) {
                    bestTour = neighbor.copy();
                }

            } else {

                // Nếu xấu hơn -> nhận theo xác suất
                double probability =
                        Math.exp(-delta / temperature);

                if (random.nextDouble() < probability) {
                    currentTour = neighbor;
                }
            }

            temperature *= COOLING_RATE;
        }

        return bestTour;
    }

    /**
     * Tạo nghiệm lân cận bằng cách swap 2 thành phố
     */
    private Tour generateNeighbor(
            Tour current,
            TSPProblem problem) {

        List<Integer> newPath =
                new ArrayList<>(current.getPath());

        int n = newPath.size();

        int i = random.nextInt(n);
        int j = random.nextInt(n);

        while (i == j) {
            j = random.nextInt(n);
        }

        Collections.swap(newPath, i, j);

        double cost =
                calculateCost(newPath, problem);

        return new Tour(newPath, cost);
    }

    /**
     * Tính tổng chi phí của tour
     */
    private double calculateCost(
            List<Integer> path,
            TSPProblem problem) {

        double cost = 0;

        for (int i = 0; i < path.size() - 1; i++) {

            cost += problem.getDistance(
                    path.get(i),
                    path.get(i + 1)
            );
        }

        // Quay về điểm đầu
        cost += problem.getDistance(
                path.get(path.size() - 1),
                path.get(0)
        );

        return cost;
    }
}