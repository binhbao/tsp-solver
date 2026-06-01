package utils;

import model.City;
import model.TSPProblem;
import model.Tour;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class VisualizationUtil {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int MARGIN = 50;

    private VisualizationUtil() {
    }

    public static void saveTour(
            TSPProblem problem,
            Tour tour,
            String outputFile) {

        List<City> cities = problem.getCities();

        if (cities.isEmpty()) {
            return;
        }

        BufferedImage image =
                new BufferedImage(
                        WIDTH,
                        HEIGHT,
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (City city : cities) {

            minX = Math.min(minX, city.getX());
            maxX = Math.max(maxX, city.getX());

            minY = Math.min(minY, city.getY());
            maxY = Math.max(maxY, city.getY());
        }

        // Vẽ tour
        g.setColor(Color.BLUE);

        List<Integer> path = tour.getPath();

        for (int i = 0; i < path.size(); i++) {

            City from = cities.get(path.get(i));

            City to;

            if (i == path.size() - 1) {
                to = cities.get(path.get(0));
            } else {
                to = cities.get(path.get(i + 1));
            }

            int x1 = scaleX(from.getX(), minX, maxX);
            int y1 = scaleY(from.getY(), minY, maxY);

            int x2 = scaleX(to.getX(), minX, maxX);
            int y2 = scaleY(to.getY(), minY, maxY);

            g.drawLine(x1, y1, x2, y2);
        }

        // Vẽ thành phố
        for (City city : cities) {

            int x = scaleX(city.getX(), minX, maxX);
            int y = scaleY(city.getY(), minY, maxY);

            g.setColor(Color.RED);
            g.fillOval(x - 5, y - 5, 10, 10);

            g.setColor(Color.BLACK);
            g.drawString(
                    String.valueOf(city.getId()),
                    x + 6,
                    y - 6
            );
        }

        // Thông tin tour
        g.setColor(Color.BLACK);
        g.drawString(
                "Cost = " +
                String.format("%.2f", tour.getCost()),
                20,
                20
        );

        g.dispose();

        try {

            File file = new File(outputFile);

            File parent = file.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            ImageIO.write(
                    image,
                    "png",
                    file
            );

            System.out.println(
                    "Saved image: " + outputFile
            );

        } catch (IOException e) {

            System.err.println(
                    "Cannot save image: "
                            + outputFile
            );

            e.printStackTrace();
        }
    }

    private static int scaleX(
            double x,
            double minX,
            double maxX) {

        return MARGIN +
                (int) ((x - minX)
                        / (maxX - minX)
                        * (WIDTH - 2 * MARGIN));
    }

    private static int scaleY(
            double y,
            double minY,
            double maxY) {

        return HEIGHT - MARGIN -
                (int) ((y - minY)
                        / (maxY - minY)
                        * (HEIGHT - 2 * MARGIN));
    }
}