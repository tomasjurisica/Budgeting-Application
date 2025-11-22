import BudgetingObjects.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Entry e1 = new Entry("1", "TEST", 3, LocalDate.of(2020, 1, 1));
        Entry e2 = new Entry("2", "TEST", 3, LocalDate.of(2020, 1, 2));
        Entry e3 = new Entry("3", "TEST", 3, LocalDate.of(2020, 1, 3));
        Entry e4 = new Entry("4", "TEST", 3, LocalDate.of(2020, 1, 2));
        Entry e5 = new Entry("5", "TEST", 3, LocalDate.of(2020, 1, 4));

        User u = new User("Name");

        u.addEntry(e1);
        u.addEntry(e2);
        u.addEntry(e3);
        u.addEntry(e4);
        u.addEntry(e5);

        ArrayList<Entry> e = u.getEntries();

        for (int i = 0; i < e.size(); i++) {
            System.out.print(e.get(i).getName());
        }

        ArrayList<Entry> testEntries = new ArrayList<>();

        Entry e6 = new Entry("6", "TEST", 3, LocalDate.of(2020, 1, 1));
        Entry e7 = new Entry("7", "TEST", 3, LocalDate.of(2020, 1, 2));
        Entry e8 = new Entry("8", "TEST", 3, LocalDate.of(2020, 1, 3));

        testEntries.add(e6);
        testEntries.add(e7);
        testEntries.add(e8);

        u.addEntry(testEntries);

        System.out.println();

        e = u.getEntries();

        for (int i = 0; i < e.size(); i++) {
            System.out.print(e.get(i).getName());
        }

        ArrayList<Entry> testGet = u.getEntries(LocalDate.of(2020, 1, 1));

        System.out.println();

        for (int i = 0; i < testGet.size(); i++) {
            System.out.print(testGet.get(i).getName());
        }
    }
}