package data_access;

import entity.IndividualEntry;
import use_case.individual_entry.IndividualEntryDataAccessInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores individual entries in CSV files under /data.
 * One file per user: e.g. data/<username>_entries.csv
 */
public class IndividualEntryDataAccessObject
        implements IndividualEntryDataAccessInterface {

    private final Path dataDir;

    public IndividualEntryDataAccessObject(Path dataDir) {
        this.dataDir = dataDir;
    }

    private Path fileForUser(String username) {
        return dataDir.resolve(username + "_entries.csv");
    }

    @Override
    public void saveEntry(String username, IndividualEntry entry) {
        try {
            Files.createDirectories(dataDir);
            Path userFile = fileForUser(username);

            try (BufferedWriter writer = Files.newBufferedWriter(
                    userFile,
                    java.nio.charset.StandardCharsets.UTF_8,
                    Files.exists(userFile)
                            ? java.nio.file.StandardOpenOption.APPEND
                            : java.nio.file.StandardOpenOption.CREATE)) {

                // very simple CSV: name,category,amount,date
                writer.write(String.join(",",
                        escape(entry.getName()),
                        escape(entry.getCategory()),
                        Float.toString(entry.getAmount()),
                        entry.getDate().toString()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save entry for user " + username, e);
        }
    }

    @Override
    public List<IndividualEntry> loadEntriesForUser(String username) {
        List<IndividualEntry> result = new ArrayList<>();
        Path userFile = fileForUser(username);

        if (!Files.exists(userFile)) {
            return result;  // empty but not an error
        }

        try (BufferedReader reader = Files.newBufferedReader(
                userFile, java.nio.charset.StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length != 4) continue; // skip bad lines

                String name = unescape(parts[0]);
                String category = unescape(parts[1]);
                float amount = Float.parseFloat(parts[2]);
                LocalDate date = LocalDate.parse(parts[3]);

                result.add(new IndividualEntry(name, category, amount, date));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load entries for user " + username, e);
        }

        return result;
    }

    // Tiny helpers so commas in names don’t break the CSV completely.
    private String escape(String s) {
        return s.replace(",", "\\,");
    }

    private String unescape(String s) {
        return s.replace("\\,", ",");
    }
}
