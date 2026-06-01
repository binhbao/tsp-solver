package algorithms;

import model.TSPProblem;
import model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ACO implements TSPSolver {

    private static final int NUM_ANTS = 30;
    private static final int MAX_ITERATIONS = 200;

    private static final double ALPHA = 1.0;
    private static final double BETA = 5.0;

    private static final double EVAPORATION = 0.5;
    private static final double Q = 100.0;

    private double[][] pheromone;

    private final Random random = new Random();

    @Override
    public String getName() {
        return "Ant Colony Optimization";
    }

    @Override
    public Tour solve(TSPProblem problem) {

        int n = problem.size();

        initializePheromones(n);

        Tour bestTour = null;

        for (int iteration = 0;
             iteration < MAX_ITERATIONS;
             iteration++) {

            List<Tour> antTours =
                    new ArrayList<>();

            for (int ant = 0;
                 ant < NUM_ANTS;
                 ant++) {

                Tour tour =
                        buildTour(problem);

                antTours.add(tour);

                if (bestTour == null ||
                    tour.getCost()
                    < bestTour.getCost()) {

                    bestTour = tour.copy();
                }
            }

            updatePheromones(
                    antTours,
                    problem
            );
        }

        return bestTour;
    }

    private void initializePheromones(int n) {

        pheromone =
                new double[n][n];

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                pheromone[i][j] = 1.0;
            }
        }
    }

    private Tour buildTour(
            TSPProblem problem) {

        int n = problem.size();

        boolean[] visited =
                new boolean[n];

        List<Integer> path =
                new ArrayList<>();

        int current =
                random.nextInt(n);

        path.add(current);

        visited[current] = true;

        while (path.size() < n) {

            int next =
                    selectNextCity(
                            current,
                            visited,
                            problem
                    );

            path.add(next);

            visited[next] = true;

            current = next;
        }

        double cost =
                calculateCost(
                        path,
                        problem
                );

        return new Tour(path, cost);
    }

    private int selectNextCity(
            int current,
            boolean[] visited,
            TSPProblem problem) {

        int n = problem.size();

        double[] probability =
                new double[n];

        double total = 0;

        for (int city = 0;
             city < n;
             city++) {

            if (!visited[city]) {

                double tau =
                        Math.pow(
                                pheromone[current][city],
                                ALPHA
                        );

                double eta =
                        Math.pow(
                                1.0 /
                                problem.getDistance(
                                        current,
                                        city
                                ),
                                BETA
                        );

                probability[city] =
                        tau * eta;

                total +=
                        probability[city];
            }
        }

        double r =
                random.nextDouble()
                * total;

        double cumulative = 0;

        for (int city = 0;
             city < n;
             city++) {

            if (!visited[city]) {

                cumulative +=
                        probability[city];

                if (cumulative >= r) {
                    return city;
                }
            }
        }

        for (int city = 0;
             city < n;
             city++) {

            if (!visited[city]) {
                return city;
            }
        }

        return -1;
    }

    private void updatePheromones(
            List<Tour> tours,
            TSPProblem problem) {

        int n = problem.size();

        // Evaporation
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                pheromone[i][j] *=
                        (1 - EVAPORATION);
            }
        }

        // Deposit
        for (Tour tour : tours) {

            double contribution =
                    Q / tour.getCost();

            List<Integer> path =
                    tour.getPath();

            for (int i = 0;
                 i < path.size() - 1;
                 i++) {

                int from =
                        path.get(i);

                int to =
                        path.get(i + 1);

                pheromone[from][to]
                        += contribution;

                pheromone[to][from]
                        += contribution;
            }

            int last =
                    path.get(path.size() - 1);

            int first =
                    path.get(0);

            pheromone[last][first]
                    += contribution;

            pheromone[first][last]
                    += contribution;
        }
    }

    private double calculateCost(
            List<Integer> path,
            TSPProblem problem) {

        double cost = 0;

        for (int i = 0;
             i < path.size() - 1;
             i++) {

            cost +=
                    problem.getDistance(
                            path.get(i),
                            path.get(i + 1)
                    );
        }

        cost +=
                problem.getDistance(
                        path.get(path.size() - 1),
                        path.get(0)
                );

        return cost;
    }
}