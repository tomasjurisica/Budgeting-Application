package use_case.add_household_entry;

import data_access.FileUserDataAccessObject;
import entity.Household;
import entity.HouseholdEntryHistory;

/**
 * Use case for undoing the last household entry addition.
 * Demonstrates the Memento pattern in action.
 */
public class UndoHouseholdEntryInteractor {
    private final FileUserDataAccessObject dataAccess;
    private final HouseholdEntryHistory history;

    public UndoHouseholdEntryInteractor(FileUserDataAccessObject dataAccess,
                                        HouseholdEntryHistory history) {
        this.dataAccess = dataAccess;
        this.history = history;
    }

    /**
     * Undoes the last household entry addition by restoring the previous state.
     *
     * @return true if undo was successful, false if there's nothing to undo
     */
    public boolean execute() {
        if (!history.canUndo()) {
            return false;
        }

        Household currentHousehold = getCurrentHousehold();
        if (currentHousehold == null) {
            return false;
        }

        // Save current state to redo stack before undoing
        Household.HouseholdMemento currentMemento = currentHousehold.createMemento();
        history.saveRedoMemento(currentMemento);

        // Restore previous state
        Household.HouseholdMemento previousMemento = history.getUndoMemento();
        if (previousMemento != null) {
            currentHousehold.restoreFromMemento(previousMemento);
            // Save the restored state back to file
            dataAccess.save(currentHousehold);
            return true;
        }

        return false;
    }

    /**
     * Redoes the last undone household entry addition.
     *
     * @return true if redo was successful, false if there's nothing to redo
     */
    public boolean redo() {
        if (!history.canRedo()) {
            return false;
        }

        Household currentHousehold = getCurrentHousehold();
        if (currentHousehold == null) {
            return false;
        }

        // Save current state to undo stack before redoing
        Household.HouseholdMemento currentMemento = currentHousehold.createMemento();
        history.saveMemento(currentMemento);

        // Restore the state from redo stack
        Household.HouseholdMemento redoMemento = history.getRedoMemento();
        if (redoMemento != null) {
            currentHousehold.restoreFromMemento(redoMemento);
            // Save the restored state back to file
            dataAccess.save(currentHousehold);
            return true;
        }

        return false;
    }

    private Household getCurrentHousehold() {
        String currentUsername = dataAccess.getCurrentUsername();
        if (currentUsername != null) {
            return dataAccess.get(currentUsername);
        }
        return null;
    }
}

