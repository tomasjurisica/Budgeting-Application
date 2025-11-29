package interface_adapter.home_page;

import entity.Entry;
import interface_adapter.ViewModel;
import use_case.select_user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HomePageViewModel {
        private final List<Entry> entries = new ArrayList();
        private final List<Consumer<List<Entry>>> listeners = new ArrayList();
        private String lastMessage = "";

        public synchronized void setEntries(List<Entry> newEntries) {
            this.entries.clear();
            if (newEntries != null) {
                this.entries.addAll(newEntries);
            }

            this.notifyListeners();
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

    public void present(SelectUserOutputData outputData) {
    }
}
