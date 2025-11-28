package data_access;

import BudgetingObjects.Entry;
import BudgetingObjects.User;
import use_case.add_entry.AddEntryUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Data access object for a single user's entries.
 * - On construction, it loads existing entries from a CSV file.
 * - On addEntry, it updates the in-memory User and appends to the file.
 *
 * File format (comma-separated, one entry per line):
 *   name,category,amount,date
 *   e.g. 2025-10-05.
 */
public class UserEntryDataAccessObject implements AddEntryUserDataAccessInterface {

    private final User user;
    private final File storageFile;

    public UserEntryDataAccessObject(User user) {
        this.user = user;
        // Use the user's name to create a per-user file.
        String safeName = user.getName().replaceAll("[^a-zA-Z0-9_-]", "_");

        File dataDir = new File("data");
        if (!dataDir.exists()) {
            // Create data/ directory if it doesn’t exist
            dataDir.mkdirs();
        }

        this.storageFile = new File(dataDir, safeName + "_entries.csv");

        // Load any existing entries from file into the User entity
        loadEntriesFromFile();
    }

    @Override
    public void addEntry(Entry entry) {
        // Update in-memory user
        user.addEntry(entry);

        // Persist to file
        appendEntryToFile(entry);
    }

    @Override
    public List<Entry> getAllEntries() {
        // Return a copy of memory entries
        return new ArrayList<>(user.getEntries());
    }

    /**
     * Reads the user's file (if it exists) and adds all entries to the User.
     */
    private void loadEntriesFromFile() {
        if (!storageFile.exists()) {
            return; // nothing to load yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(storageFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    // bad line, skip
                    continue;
                }

                String name = parts[0];
                String category = parts[1];
                float amount;
                LocalDate date;

                try {
                    amount = Float.parseFloat(parts[2]);
                    date = LocalDate.parse(parts[3]); // YYYY-MM-DD
                } catch (Exception e) {
                    // bad data, skip line
                    continue;
                }

                Entry entry = new Entry(name, category, amount, date);
                user.addEntry(entry);
            }
        } catch (IOException e) {
            System.err.println("Could not read entries for user " + user.getName());
            e.printStackTrace();
        }
    }

    /**
     * Appends a single entry to the user's CSV file.
     */
    private void appendEntryToFile(Entry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile, true))) {
            writer.write(entry.getName() + ","
                    + entry.getCategory() + ","
                    + entry.getAmount() + ","
                    + entry.getDate()          // LocalDate -> "YYYY-MM-DD"
                    + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Could not save entry for user " + user.getName());
            e.printStackTrace();
        }
    }
}
