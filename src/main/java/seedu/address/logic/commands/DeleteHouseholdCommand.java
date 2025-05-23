package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.household.Household;
import seedu.address.model.household.HouseholdId;
import seedu.address.ui.MainWindow;

/**
 * Deletes a household identified using its household ID from the household book.
 */
public class DeleteHouseholdCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the household identified by the household ID.\n"
            + "Parameters: "
            + PREFIX_ID + "HOUSEHOLD_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "H000001";

    public static final String MESSAGE_DELETE_HOUSEHOLD_SUCCESS = "Deleted Household: %1$s";
    public static final String MESSAGE_HOUSEHOLD_NOT_FOUND = "No household found with ID: %1$s";
    public static final String MESSAGE_CANCELLED = "Deletion cancelled.";

    private final HouseholdId targetId;

    /**
     * Creates a {@code DeleteHouseholdCommand} to delete a household with the specified ID.
     *
     * @param targetId The unique identifier of the household to be deleted.
     * @throws NullPointerException if {@code targetId} is null.
     */
    public DeleteHouseholdCommand(HouseholdId targetId) {
        requireNonNull(targetId);
        this.targetId = targetId;
    }

    /**
     * Shows a confirmation dialog for deleting the specified {@code Household}.
     *
     * <p>This is an instance method that delegates to the static UI method
     * {@link MainWindow#showDeleteConfirmationDialog(Household)}. It is kept as an instance
     * method so that tests can conveniently spy on it and override its return value.</p>
     *
     * @param household The {@code Household} to be deleted.
     * @return {@code true} if the user confirms the deletion, or {@code false} otherwise.
     */
    public boolean confirmDeletion(Household household) {
        return MainWindow.showDeleteConfirmationDialog(household);
    }
    /**
     * Executes the command to delete the specified household from the model.
     *
     * @param model The model from which the household will be deleted. Must not be null.
     * @return A {@code CommandResult} indicating the result of the deletion operation.
     *         Returns a cancellation message if the user declines the confirmation.
     * @throws CommandException If the household with the specified ID does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Household householdToDelete = model.getHouseholdBook().getHouseholdById(targetId)
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_HOUSEHOLD_NOT_FOUND, targetId)));

        if (!confirmDeletion(householdToDelete)) {
            return new CommandResult(MESSAGE_CANCELLED);
        }

        model.getHouseholdBook().removeHousehold(householdToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_HOUSEHOLD_SUCCESS, householdToDelete));
    }
    /**
     * Checks if this {@code DeleteHouseholdCommand} is equal to another object.
     *
     * @param other The other object to compare against.
     * @return {@code true} if the other object is the same instance or an equivalent
     *         {@code DeleteHouseholdCommand} with the same target ID, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteHouseholdCommand
                && targetId.equals(((DeleteHouseholdCommand) other).targetId));
    }
}
