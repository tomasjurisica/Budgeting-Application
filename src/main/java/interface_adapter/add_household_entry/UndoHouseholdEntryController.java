package interface_adapter.add_household_entry;
import use_case.add_household_entry.UndoHouseholdEntryInteractor;

/**
 * Controller for undoing household entry additions.
 * Part of the Memento pattern implementation.
 */
public class UndoHouseholdEntryController {
    private final UndoHouseholdEntryInteractor undoInteractor;

    public UndoHouseholdEntryController(UndoHouseholdEntryInteractor undoInteractor) {
        this.undoInteractor = undoInteractor;
    }

    /**
     * Executes the undo operation.
     * @return true if undo was successful, false otherwise
     */
    public boolean execute() {
        return undoInteractor.execute();
    }

    /**
     * Executes the redo operation.
     * @return true if redo was successful, false otherwise
     */
    public boolean redo() {
        return undoInteractor.redo();
    }
}

