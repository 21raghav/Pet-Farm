package Game;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The DataManager class handles reading and writing pet data to and from CSV files.
 * It also manages save files for up to 4 pets.
 */
public class DataManager {
    private static final String DATA_DIRECTORY = "Data/";
    private static final String STATE_FILE_PATH = DATA_DIRECTORY + "State.csv";
    private static final int MAX_SAVE_FILES = 4;

    // Ensure the Data directory exists
    static {
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    /**
     * Initializes the `State.csv` file and individual pet save files.
     */
    public static void initializeSaveFiles() {
        initializeStateFile();
    }

    /**
     * Initializes the `State.csv` file with default pet data if it doesn't exist.
     */
    private static void initializeStateFile() {
        File stateFile = new File(STATE_FILE_PATH);
        if (!stateFile.exists()) {
            writePetDataToCsv();
        }
    }

    /**
     * Writes default pet data to the `State.csv` file.
     */
    private static void writePetDataToCsv() {
        String[] headers = {"petID", "happiness", "hunger", "health", "sleep"};
        String[][] petData = {
                {"1", "70", "70", "70", "70"}, // Fox
                {"2", "70", "70", "70", "70"}, // Dog
                {"3", "70", "70", "70", "70"}, // Cat
                {"4", "70", "70", "70", "70"}  // Rat
        };

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATE_FILE_PATH))) {
            writer.write(String.join(",", headers));
            writer.newLine();
            for (String[] row : petData) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
            System.out.println("State.csv file initialized.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves default attributes for a pet from the `State.csv` file using the pet ID.
     *
     * @param petID The ID of the pet.
     * @return A map containing the pet's default attributes.
     */
    public static Map<String, String> getPetAttributes(String petID) {
        Map<String, String> attributes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(STATE_FILE_PATH))) {
            String headerLine = reader.readLine(); // Read headers
            if (headerLine == null) {
                throw new IOException("State.csv is empty!");
            }
            String[] headers = headerLine.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(petID)) { // Match the pet ID
                    for (int i = 1; i < headers.length; i++) {
                        attributes.put(headers[i], values[i]);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            DataManager.resetState("State.csv");
            return getPetAttributes(petID);
        }

        return attributes;
    }

    /**
     * Saves the current state of the pet to a CSV file.
     *
     * @param slotName    The name of the pet (used as the save file name).
     * @param attributes The pet's attributes to save.
     */
    public static void saveState(String slotName, Map<String, String> attributes) {
        String filePath = DATA_DIRECTORY + slotName.toLowerCase();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.join(",", attributes.keySet()));
            writer.newLine();
            writer.write(String.join(",", attributes.values()));
            System.out.println("State saved successfully for " + slotName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads the saved state of a pet from a CSV file.
     *
     * @param petName The name of the pet whose save file should be loaded.
     * @return A map containing the pet's attributes, or an empty map if no save file is found.
     */
    public static Map<String, String> loadState(String petName, String file) {
        String filePath = DATA_DIRECTORY + file;
        Map<String, String> attributes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String[] headers = reader.readLine().split(",");
            String[] values = reader.readLine().split(",");
            for (int i = 0; i < headers.length; i++) {
                attributes.put(headers[i], values[i]);
            }
        } catch (IOException e) {
            DataManager.resetState(file);
            return loadState(petName, file);
        }

        return attributes;
    }

    /**
     * Retrieves a list of save file names (without extensions).
     *
     * @return An array of save file names.
     */
    public static String[] getSaveFileNames() {
        File folder = new File(DATA_DIRECTORY);
        String[] saveFiles = folder.list((dir, name) -> name.endsWith(".csv") && !name.equals("State.csv"));
        if (saveFiles == null) return new String[0];

        for (int i = 0; i < saveFiles.length; i++) {
            saveFiles[i] = saveFiles[i].replace(".csv", ""); // Remove the ".csv" extension
        }

        return saveFiles;
    }

    public static void resetState(String fileName) {
        if (fileName.toLowerCase().equals("restrictions.csv")) {
            var newData = new HashMap<String, String>();

            newData.put("StartTime", "Not set");
            newData.put("EndTime", "Not set");
            newData.put("Total", "00:00");
            newData.put("Average", "00:00");
            newData.put("Times", "0");

            DataManager.saveState(fileName, newData);
        }
        else {

            var newData = new HashMap<String, String>();

            newData.put("orange", "0");
            newData.put("banana", "0");
            newData.put("apple", "0");
            newData.put("bbone", "0");
            newData.put("strawberry", "0");
            newData.put("ybone", "0");

            switch(fileName) {
                case ("slot1.csv"):
                    newData.put("happiness", "100");
                    newData.put("sleep", "50");
                    newData.put("health", "50");
                    newData.put("hunger", "50");
                    break;
                case ("slot2.csv"):
                    newData.put("happiness", "50");
                    newData.put("sleep", "100");
                    newData.put("health", "50");
                    newData.put("hunger", "50");
                    break;
                case ("slot3.csv"):
                    newData.put("happiness", "50");
                    newData.put("sleep", "50");
                    newData.put("health", "100");
                    newData.put("hunger", "50");
                    break;
                case ("slot4.csv"):
                    newData.put("happiness", "50");
                    newData.put("sleep", "50");
                    newData.put("health", "50");
                    newData.put("hunger", "100");
                    break;
            }

            DataManager.saveState(fileName, newData);
        }
    }

    /**
     * Retrieves the pet ID corresponding to a pet name.
     *
     * @param petName The name of the pet.
     * @return The pet ID, or null if not found.
     */
    public static String getPetID(String petName) {
        switch (petName.toLowerCase()) {
            case "fox":
                return "1";
            case "dog":
                return "2";
            case "cat":
                return "3";
            case "rat":
                return "4";
            default:
                return null;
        }
    }
}
