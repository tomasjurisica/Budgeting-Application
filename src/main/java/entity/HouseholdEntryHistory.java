package entity;

import java.util.Stack;

/**
 * Caretaker class for the Memento pattern.
 * Manages the history of household entry states, enabling undo/redo functionality.
 */
public class HouseholdEntryHistory {
    private final Stack<Household.HouseholdMemento> undoStack = new Stack<>();
    private final Stack<Household.HouseholdMemento> redoStack = new Stack<>();

    /**
     * Saves a memento to the history (for undo functionality).
     * This should be called before making changes to the household entries.
     *
     * @param memento The memento to save
     */
    public void saveMemento(Household.HouseholdMemento memento) {
        if (memento != null) {
            undoStack.push(memento);
            // Clear redo stack when a new action is performed
            redoStack.clear();
        }
    }

    /**
     * Retrieves the most recent memento for undo operation.
     *
     * @return The most recent memento, or null if there's nothing to undo
     */
    public Household.HouseholdMemento getUndoMemento() {
        if (undoStack.isEmpty()) {
            return null;
        }
        return undoStack.pop();
    }

    /**
     * Saves a memento to the redo stack (for redo functionality).
     *
     * @param memento The memento to save for redo
     */
    public void saveRedoMemento(Household.HouseholdMemento memento) {
        if (memento != null) {
            redoStack.push(memento);
        }
    }

    /**
     * Retrieves the most recent memento for redo operation.
     *
     * @return The most recent redo memento, or null if there's nothing to redo
     */
    public Household.HouseholdMemento getRedoMemento() {
        if (redoStack.isEmpty()) {
            return null;
        }
        return redoStack.pop();
    }

    /**
     * Checks if there are any mementos available for undo.
     *
     * @return true if undo is available, false otherwise
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Checks if there are any mementos available for redo.
     *
     * @return true if redo is available, false otherwise
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Clears all history (both undo and redo stacks).
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}

