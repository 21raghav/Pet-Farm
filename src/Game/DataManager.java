package Game;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private final File csvFile;

    DataManager() {
        this.csvFile = new File("Data/State.csv");
        try {
            FileReader reader = new FileReader(this.csvFile);
        } catch( IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getPetAttributes(String fileName, String petID) {
        Map<String, Integer> attributes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.csvFile)) {
            // Read the header line
            final String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IOException("CSV file is empty");
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return attributes; // Empty if petID not found
    }

}
