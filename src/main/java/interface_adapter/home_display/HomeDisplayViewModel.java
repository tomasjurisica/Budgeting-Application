package interface_adapter.home_display;

import data_access.FileUserDataAccessObject;
import entity.Entry;
import entity.Household;
import entity.User;
import use_case.select_user.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HomeDisplayViewModel {
    private final List<Entry> entries = new ArrayList();
    private final List<Consumer<List<Entry>>> listeners = new ArrayList();
    private String lastMessage = "";
    private User currentUser;
    private FileUserDataAccessObject dao;

    public synchronized void setEntries(List<Entry> newEntries) {
        this.entries.clear();
        if (newEntries != null) {
            this.entries.addAll(newEntries);
        }

        this.notifyListeners();
    }

    public void setDao(FileUserDataAccessObject dao) {
        this.dao = dao;
    }

    public void loadCurrentUser() {
        if (dao != null) {
            String username = dao.getCurrentUsername();
            if (username != null) {
                Household household = dao.get(username);
                if (household != null) {
                    List<Entry> allEntries = new ArrayList<>();
                    for (User u : household.getUsers()) {
                        allEntries.addAll(u.getEntries());
                    }
                    setEntries(allEntries);
                }
            }
        }
    }


    public synchronized void addEntry(Entry e) {
        this.entries.add(e);
        this.notifyListeners();
    }

    public synchronized List<Entry> getEntries() {
        return new ArrayList(this.entries);
    }

    public synchronized void addListener(Consumer<List<Entry>> listener) {
        this.listeners.add(listener);
    }

    private void notifyListeners() {
        List<Entry> snapshot = this.getEntries();

        for(Consumer<List<Entry>> l : this.listeners) {
            l.accept(snapshot);
        }

    }

    public synchronized String getLastMessage() {
        return this.lastMessage;
    }

    public synchronized void setLastMessage(String m) {
        this.lastMessage = m;
    }

    public synchronized User getCurrentUser() {
        return currentUser;
    }

    public synchronized void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void present(SelectUserOutputData outputData) {
    }

    public Map<Month, List<Entry>> getEntriesByMonth() {
        return entries.stream()
                .collect(Collectors.groupingBy(e -> e.getDate().getMonth()));
    }

}
