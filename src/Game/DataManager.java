package Game;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The DataManager class handles reading and writing pet data to and from a CSV file.
 * It provides utility methods to initialize the CSV file and retrieve attributes
 * for specific pets based on their ID.
 */
public class DataManager {

    /**
     * Writes default pet data to a CSV file located at "Data/State.csv".
     * If the file or directory doesn't exist, this method creates it.
     * The CSV file contains a header row followed by default attributes for pets.
     */
    private static void writePetDataToCsv() {
        // Define the data for the CSV file
        String[] headers = {"petID", "happiness", "hunger", "health", "sleep"};
        String[][] petData = {
                {"1", "70", "70", "70", "70"},
                {"2", "70", "70", "70", "70"},
                {"3", "70", "70", "70", "70"},
                {"4", "70", "70", "70", "70"}
        };

        // Write data to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data/State.csv"))) {
            // Write the header
            writer.write(String.join(",", headers));
            writer.newLine();

            // Write the rows of data
            for (String[] row : petData) {
                writer.write(String.join(",", row));
                writer.newLine();
            }

            System.out.println("CSV file written successfully to " + "State.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the attributes of a pet identified by its petID.
     *
     * @param petID The ID of the pet whose attributes need to be retrieved.
     *              Valid IDs: { "1": Dog, "2": Fox, "3": Cat, "4": Rat }.
     * @return A map containing the pet's attributes (e.g., happiness, hunger, health, sleep),
     *         or an empty map if the petID is not found or an error occurs.
     *         The keys of the map are attribute names (e.g., "happiness"),
     *         and the values are the corresponding attribute values.
     */
    public static Map<String, String> getPetAttributes(String petID) {
        Map<String, String> attributes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Data/State.csv"))) {
            // Read the header line
            String headerLine = reader.readLine();
            if (headerLine == null) {
                writePetDataToCsv();
                headerLine = reader.readLine();
            }
            String[] headers = headerLine.split(",");

            // Read each row
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(petID)) { // Check if petID matches
                    for (int i = 1; i < headers.length; i++) {
                        attributes.put(headers[i], values[i]); // Map each attribute
                    }
                    return attributes; // Return attributes for the given petID
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attributes; // Empty if petID not found
    }

}
