import model.*;
import utils.FileLoader;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<City> cities = FileLoader.load("data/cities.txt");

        TSPProblem problem = new TSPProblem(cities);

        System.out.println("Number of cities: " + problem.size());

        System.out.println("Distance matrix test:");
        System.out.println("0 -> 1 = " + problem.getDistance(0, 1));
        System.out.println("1 -> 2 = " + problem.getDistance(1, 2));
        System.out.println("2 -> 0 = " + problem.getDistance(2, 0));
    }
}