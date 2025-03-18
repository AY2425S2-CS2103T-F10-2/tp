package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.household.HouseholdId;

/**
 * Parses input arguments for the delete-session command.
 * Expected format: "H000002-2" where "H000002" is the household ID and "2" is the 1-based session index.
 */
public class DeleteSessionCommandParser implements Parser<DeleteSessionCommand> {

    @Override
    public DeleteSessionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Check that the input contains a hyphen.
        if (!trimmedArgs.contains("-")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("-", 2);
        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE));
        }

        String householdIdStr = parts[0].trim();
        String sessionIndexStr = parts[1].trim();

        // Validate householdId (assuming HouseholdId has a method isValidId).
        if (!HouseholdId.isValidId(householdIdStr)) {
            throw new ParseException("Invalid household ID: " + householdIdStr);
        }
        HouseholdId householdId = HouseholdId.fromString(householdIdStr);

        int sessionIndex;
        try {
            sessionIndex = Integer.parseInt(sessionIndexStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Session index must be an integer: " + sessionIndexStr);
        }

        return new DeleteSessionCommand(householdId, sessionIndex);
    }
}


