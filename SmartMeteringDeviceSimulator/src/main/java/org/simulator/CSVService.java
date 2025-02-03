package org.simulator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVService {

    public static List<Double> readFromFile(String filePath) throws IOException {
        List<Double> numbers = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    numbers.add(Double.parseDouble(nextLine[0].trim()));
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid number: " + nextLine[0]);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return numbers;
    }
}
