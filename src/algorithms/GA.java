package algorithms;

import model.TSPProblem;
import model.Tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GA implements TSPSolver {

    private static final int POPULATION_SIZE = 100;
    private static final int GENERATIONS = 500;
    private static final double MUTATION_RATE = 0.05;

    private final Random random = new Random();

    @Override
    public String getName() {
        return "Genetic Algorithm";
    }

    @Override
    public Tour solve(TSPProblem problem) {

        List<Tour> population = initializePopulation(problem);

        Tour bestTour = getBest(population);

        for (int generation = 0; generation < GENERATIONS; generation++) {

            List<Tour> newPopulation = new ArrayList<>();

            // Elitism: giữ cá thể tốt nhất
            newPopulation.add(bestTour.copy());

            while (newPopulation.size() < POPULATION_SIZE) {

                Tour parent1 = tournamentSelection(population);
                Tour parent2 = tournamentSelection(population);

                Tour child = crossover(parent1, parent2, problem);

                mutate(child, problem);

                newPopulation.add(child);
            }

            population = newPopulation;

            Tour currentBest = getBest(population);

            if (currentBest.getCost() < bestTour.getCost()) {
                bestTour = currentBest.copy();
            }
        }

        return bestTour;
    }

    /**
     * Khởi tạo quần thể ngẫu nhiên
     */
    private List<Tour> initializePopulation(TSPProblem problem) {

        int n = problem.size();

        List<Tour> population = new ArrayList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {

            List<Integer> path = new ArrayList<>();

            for (int city = 0; city < n; city++) {
                path.add(city);
            }

            Collections.shuffle(path);

            double cost = calculateCost(path, problem);

            population.add(new Tour(path, cost));
        }

        return population;
    }

    /**
     * Tournament Selection
     */
    private Tour tournamentSelection(List<Tour> population) {

        int tournamentSize = 5;

        Tour best = null;

        for (int i = 0; i < tournamentSize; i++) {

            Tour candidate =
                    population.get(random.nextInt(population.size()));

            if (best == null ||
                candidate.getCost() < best.getCost()) {

                best = candidate;
            }
        }

        return best;
    }

    /**
     * Ordered Crossover (OX)
     */
    private Tour crossover(
            Tour parent1,
            Tour parent2,
            TSPProblem problem) {

        int n = parent1.getPath().size();

        List<Integer> child =
                new ArrayList<>(Collections.nCopies(n, -1));

        int start = random.nextInt(n);
        int end = random.nextInt(n);

        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        // Copy đoạn từ parent1
        for (int i = start; i <= end; i++) {
            child.set(i, parent1.getPath().get(i));
        }

        // Điền phần còn lại từ parent2
        int currentIndex = (end + 1) % n;

        for (int i = 0; i < n; i++) {

            int city =
                    parent2.getPath().get((end + 1 + i) % n);

            if (!child.contains(city)) {

                child.set(currentIndex, city);

                currentIndex =
                        (currentIndex + 1) % n;
            }
        }

        double cost =
                calculateCost(child, problem);

        return new Tour(child, cost);
    }

    /**
     * Swap Mutation
     */
    private void mutate(
            Tour tour,
            TSPProblem problem) {

        if (random.nextDouble() > MUTATION_RATE) {
            return;
        }

        List<Integer> path =
                new ArrayList<>(tour.getPath());

        int i = random.nextInt(path.size());
        int j = random.nextInt(path.size());

        Collections.swap(path, i, j);

        tour.setPath(path);

        tour.setCost(
                calculateCost(path, problem)
        );
    }

    /**
     * Lấy cá thể tốt nhất
     */
    private Tour getBest(List<Tour> population) {

        return population.stream()
                .min(Comparator.comparingDouble(Tour::getCost))
                .orElseThrow();
    }

    /**
     * Tính chi phí tour
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

        cost += problem.getDistance(
                path.get(path.size() - 1),
                path.get(0)
        );

        return cost;
    }
}