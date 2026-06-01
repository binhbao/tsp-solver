package utils;

import model.City;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileLoader {

    private FileLoader() {
        // Prevent instantiation
    }

    /**
     * Đọc dữ liệu city từ file
     * Format:
     * x y
     * x y
     * ...
     */
    public static List<City> load(String filePath) {

        List<City> cities = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            String line;
            int id = 0;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length < 2) {
                    continue;
                }

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);

                cities.add(new City(id++, x, y));
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }

        return cities;
    }
}