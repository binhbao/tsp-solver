import algorithms.*;
import model.City;
import model.TSPProblem;
import model.Tour;
import utils.FileLoader;
import utils.VisualizationUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        File dataFolder = new File("data");

        if (!dataFolder.exists() || !dataFolder.isDirectory()) {

            System.out.println("Data folder not found!");
            return;
        }

        File[] files = dataFolder.listFiles(
                (dir, name) -> name.endsWith(".txt")
        );

        if (files == null || files.length == 0) {

            System.out.println("No dataset found!");
            return;
        }

        Arrays.sort(files);

        List<TSPSolver> solvers = List.of(
                new Greedy(),
                new SA(),
                new GA(),
                new BranchAndBound(),
                new ACO()
        );

        for (File file : files) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("Dataset: " + file.getName());
            System.out.println("==================================================");

            List<City> cities =
                    FileLoader.load(file.getPath());

            if (cities.isEmpty()) {

                System.out.println("Dataset is empty!");
                continue;
            }

            TSPProblem problem =
                    new TSPProblem(cities);

            System.out.println(
                    "Number of Cities: "
                            + problem.size());

            System.out.println();

            String datasetName =
                    file.getName()
                            .replace(".txt", "");

            for (TSPSolver solver : solvers) {

                if (solver instanceof BranchAndBound
                        && problem.size() > 15) {

                    System.out.printf(
                            "%-25s Skipped (n > 15)%n",
                            solver.getName()
                    );

                    continue;
                }

                try {

                    long start =
                            System.nanoTime();

                    Tour result =
                            solver.solve(problem);

                    long end =
                            System.nanoTime();

                    double elapsedMs =
                            (end - start)
                                    / 1_000_000.0;

                    System.out.printf(
                            "%-25s Cost = %-12.2f Time = %.3f ms%n",
                            solver.getName(),
                            result.getCost(),
                            elapsedMs
                    );

                    // Lưu ảnh
                    String imageFile =
                            "images/"
                            + datasetName
                            + "_"
                            + solver.getName()
                                    .replace(" ", "_")
                            + ".png";

                    VisualizationUtil.saveTour(
                            problem,
                            result,
                            imageFile
                    );

                } catch (Exception e) {

                    System.out.printf(
                            "%-25s ERROR: %s%n",
                            solver.getName(),
                            e.getMessage()
                    );
                }
            }

            System.out.println();
        }

        System.out.println("Benchmark completed.");
    }
}