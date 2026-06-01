package algorithms;

import model.TSPProblem;
import model.Tour;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBound implements TSPSolver {

    private double bestCost;
    private List<Integer> bestPath;

    private double[] minOutgoing;

    @Override
    public String getName() {
        return "Branch and Bound";
    }

    @Override
    public Tour solve(TSPProblem problem) {

        int n = problem.size();

        bestCost = Double.MAX_VALUE;
        bestPath = new ArrayList<>();

        computeMinOutgoing(problem);

        boolean[] visited = new boolean[n];

        List<Integer> currentPath =
                new ArrayList<>();

        visited[0] = true;
        currentPath.add(0);

        dfs(
                problem,
                0,
                visited,
                currentPath,
                0
        );

        return new Tour(bestPath, bestCost);
    }

    private void dfs(
            TSPProblem problem,
            int currentCity,
            boolean[] visited,
            List<Integer> currentPath,
            double currentCost) {

        double bound =
                calculateBound(
                        currentCost,
                        visited
                );

        if (bound >= bestCost) {
            return;
        }

        int n = problem.size();

        if (currentPath.size() == n) {

            double totalCost =
                    currentCost +
                    problem.getDistance(
                            currentCity,
                            0
                    );

            if (totalCost < bestCost) {

                bestCost = totalCost;

                bestPath =
                        new ArrayList<>(currentPath);
            }

            return;
        }

        for (int next = 0; next < n; next++) {

            if (!visited[next]) {

                visited[next] = true;
                currentPath.add(next);

                dfs(
                        problem,
                        next,
                        visited,
                        currentPath,
                        currentCost +
                        problem.getDistance(
                                currentCity,
                                next
                        )
                );

                currentPath.remove(
                        currentPath.size() - 1
                );

                visited[next] = false;
            }
        }
    }

    private void computeMinOutgoing(
            TSPProblem problem) {

        int n = problem.size();

        minOutgoing = new double[n];

        for (int i = 0; i < n; i++) {

            double min =
                    Double.MAX_VALUE;

            for (int j = 0; j < n; j++) {

                if (i == j) continue;

                min = Math.min(
                        min,
                        problem.getDistance(i, j)
                );
            }

            minOutgoing[i] = min;
        }
    }

    private double calculateBound(
            double currentCost,
            boolean[] visited) {

        double bound = currentCost;

        for (int i = 0; i < visited.length; i++) {

            if (!visited[i]) {
                bound += minOutgoing[i];
            }
        }

        return bound;
    }
}