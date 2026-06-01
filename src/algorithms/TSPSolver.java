package algorithms;

import model.TSPProblem;
import model.Tour;

public interface TSPSolver {
    Tour solve(TSPProblem problem);
    String getName();
}