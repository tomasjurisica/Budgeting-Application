package data_access;

import entity.*;
import use_case.add_household_entry.AddHouseholdEntryDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON-based DAO for Household, Users, and Entries using the org.json library.
 * This implementation is robust against structural corruption and special characters.
 */
public class FileUserDataAccessObject implements
        SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        AddHouseholdEntryDataAccessInterface {

    private final File jsonFile;
    // Map stores HouseholdID (String) -> Household object
    private final Map<String, Household> accounts = new HashMap<>();
    private String currentUsername;
    private final HouseholdFactory householdFactory;

    // --- Constructor & Initialization ---

    public FileUserDataAccessObject(String jsonPath, HouseholdFactory factory) {
        this.jsonFile = new File(jsonPath);
        this.householdFactory = factory;

        if (jsonFile.exists() && jsonFile.length() > 0) {
            load();
        } else {
            // If file doesn't exist or is empty, ensure an empty JSON object is saved
            save();
        }
    }


    private void save() {
        JSONObject data = new JSONObject();

        for (Household h : accounts.values()) {
            JSONObject householdJson = new JSONObject();

            // 1. Basic Household Data
            householdJson.put("password", h.getPassword());

            // 2. Household Entries
            householdJson.put("householdEntries", entriesToJson(h.getHouseholdEntries()));

            // 3. Users
            JSONArray usersArray = new JSONArray();
            for (User user : h.getUsers()) {
                JSONObject userJson = new JSONObject();
                userJson.put("name", user.getName());
                userJson.put("entries", entriesToJson(user.getEntries())); // User's entries
                usersArray.put(userJson);
            }
            householdJson.put("users", usersArray);

            data.put(h.getHouseholdID(), householdJson);
        }

        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(data.toString(4));
        } catch (IOException e) {
            throw new RuntimeException("Error saving JSON data", e);
        }
    }

    // Helper to convert List<Entry> to JSONArray
    private JSONArray entriesToJson(ArrayList<Entry> entries) {
        JSONArray entriesArray = new JSONArray();
        for (Entry e : entries) {
            JSONObject entryJson = new JSONObject();
            entryJson.put("name", e.getName());
            entryJson.put("category", e.getCategory());
            entryJson.put("amount", e.getAmount());
            entryJson.put("date", e.getDate().toString());
            entriesArray.put(entryJson);
        }
        return entriesArray;
    }


    private void load() {
        accounts.clear(); // Clear existing map before loading

        try (FileReader reader = new FileReader(jsonFile)) {
            // Read the entire file content as a single JSONObject
            JSONObject data = new JSONObject(new JSONTokener(reader));

            // Iterate through every key (Household ID) in the top-level JSONObject
            for (String householdID : data.keySet()) {
                JSONObject householdJson = data.getJSONObject(householdID);

                // 1. Basic Household Data and Creation
                String password = householdJson.getString("password");
                Household household = householdFactory.create(householdID, password);

                // 2. Household Entries
                JSONArray householdEntriesJson = householdJson.getJSONArray("householdEntries");
                household.getHouseholdEntries().addAll(jsonToEntries(householdEntriesJson));

                // 3. Users
                JSONArray usersJson = householdJson.getJSONArray("users");
                for (int i = 0; i < usersJson.length(); i++) {
                    JSONObject userJson = usersJson.getJSONObject(i);
                    String userName = userJson.getString("name");
                    User user = new User(userName); // Assuming User has a (name) constructor

                    // User Entries
                    JSONArray userEntriesJson = userJson.getJSONArray("entries");
                    user.addEntry(
                        jsonToEntries(userEntriesJson)); // Assuming addEntry handles List<Entry> or ArrayList<Entry>

                    household.addUser(user);
                }

                // Add the fully constructed household to the accounts map
                accounts.put(householdID, household);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading JSON data from file", e);
        }
    }

    // Helper to convert JSONArray to List<Entry>
    private ArrayList<Entry> jsonToEntries(JSONArray entriesJson) {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < entriesJson.length(); i++) {
            JSONObject entryJson = entriesJson.getJSONObject(i);

            String name = entryJson.getString("name");
            String category = entryJson.getString("category");
            float amount = entryJson.getFloat("amount");
            LocalDate date = LocalDate.parse(entryJson.getString("date"));

            entries.add(new Entry(name, category, amount, date));
        }
        return entries;
    }

    @Override
    /**
     *
     * @param newEntry: new household entry to be added
     */
    public void addHouseholdEntry(SharedEntry newEntry) {
        Household userHousehold = get(getCurrentUsername());
        List<Entry> individualEntries = newEntry.getEntries();
        List<User> selectedUsers = newEntry.getUsers();

        try (FileReader reader = new FileReader(jsonFile)) {
            // Read the entire file content as a single JSONObject
            JSONObject data = new JSONObject(new JSONTokener(reader));

            // Add the new household entry to the JSON
            JSONObject householdData = data.getJSONObject(userHousehold.getHouseholdID());
            JSONObject entryJson = new JSONObject();
            entryJson.put("name", newEntry.getName());
            entryJson.put("category", newEntry.getCategory());
            entryJson.put("amount", newEntry.getAmount());
            entryJson.put("date", newEntry.getDate().toString());
            householdData.getJSONArray("householdEntries").put(entryJson);

            // Add entry to each user
            JSONArray users = householdData.getJSONArray("users");

            int selectedUserIndex = 0;

            for (int i = 0; i < users.length(); i++) {
                // Get the list of users
                JSONObject userInfo = users.getJSONObject(i);
                String userName = userInfo.get("name").toString();
                System.out.println(selectedUserIndex);

                // Only if the user is on the household entry, add the object
                if (selectedUserIndex < selectedUsers.size() &&
                        userName.equals(selectedUsers.get(selectedUserIndex).getName())) {
                    // Create entry JSON object and put it in the array
                    Entry userEntry = individualEntries.get(selectedUserIndex);
                    entryJson = new JSONObject();
                    entryJson.put("name", userEntry.getName());
                    entryJson.put("category", userEntry.getCategory());
                    entryJson.put("amount", userEntry.getAmount());
                    entryJson.put("date", userEntry.getDate().toString());
                    userInfo.getJSONArray("entries").put(entryJson);

                    selectedUserIndex++;
                }
            }

            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(data.toString(4)); // Pretty print with 4-space indent
            } catch (IOException e) {
                throw new RuntimeException("Error writing JSON data to file", e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error loading JSON data from file", e);
        }
    }

    @Override
    public void save(Household household) {
        accounts.put(household.getHouseholdID(), household);
        save(); // Save all accounts to file
    }

    public synchronized void addEntry(Entry entry) {
        String username = getCurrentUsername();
        if (username == null) return;

        Household household = accounts.get(username);
        if (household == null) return;

        if (!household.getUsers().isEmpty()) {
            household.getUsers().get(0).addEntry(entry);
        }

        save();
    }

    public synchronized List<Entry> getAllEntries() {
        String username = getCurrentUsername();
        if (username == null) return new ArrayList<>();

        Household household = accounts.get(username);
        if (household == null) return new ArrayList<>();

        ArrayList<Entry> allEntries = new ArrayList<>();
        for (User user : household.getUsers()) {
            allEntries.addAll(user.getEntries());
        }
        return allEntries;
    }

    public synchronized void clearEntries() {
        String username = getCurrentUsername();
        if (username == null) return;

        Household household = accounts.get(username);
        if (household == null) return;

        for (User user : household.getUsers()) {
            user.getEntries().clear();
        }
        save();
    }

    @Override
    public Household get(String username) {
        return accounts.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    @Override
    public ArrayList<User> getUsers() {
        return get(getCurrentUsername()).getUsers();
    }
}
