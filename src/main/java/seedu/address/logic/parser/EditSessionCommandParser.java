package seedu.address.logic.parser;

import seedu.address.logic.commands.EditSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.household.HouseholdId;

/**
 * Parses input for the edit-session command.
 * Expected format: "id/H000006-2 d/DATE t/TIME [n/NOTE]"
 * Example: "edit-session id/H000006-2 d/2025-03-16 t/15:00"
 * Example with note: "edit-session id/H000006-2 d/2025-03-16 t/15:00 n/Follow-up on medical assistance application"
 */
public class EditSessionCommandParser implements Parser<EditSessionCommand> {

    public static final String MESSAGE_INVALID_FORMAT =
            "Invalid format! Usage: edit-session id/<HOUSEHOLD_ID-SESSION_INDEX> d/DATE t/TIME [n/NOTE]\n"
                    + "Example: edit-session id/H000006-2 d/2025-03-16 t/15:00\n"
                    + "Example with note: edit-session id/H000006-2 d/2025-03-16 t/15:00 n/Follow-up on"
                    + "medical assistance application";

    private static final Prefix PREFIX_ID = new Prefix("id/");
    private static final Prefix PREFIX_DATE = new Prefix("d/");
    private static final Prefix PREFIX_TIME = new Prefix("t/");
    private static final Prefix PREFIX_NOTE = new Prefix("n/");

    @Override
    public EditSessionCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_ID, PREFIX_DATE, PREFIX_TIME, PREFIX_NOTE);

        if (!argMultimap.arePrefixesPresent(PREFIX_ID, PREFIX_DATE, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        String sessionIdentifier = argMultimap.getValue(PREFIX_ID).get().trim();
        if (!sessionIdentifier.contains("-")) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
        String[] parts = sessionIdentifier.split("-", 2);
        if (parts.length < 2) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
        String householdIdStr = parts[0].trim();
        String sessionIndexStr = parts[1].trim();

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

        String datePart = argMultimap.getValue(PREFIX_DATE).get().trim();
        String timePart = argMultimap.getValue(PREFIX_TIME).get().trim();

        // Check if a note is provided
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            String notePart = argMultimap.getValue(PREFIX_NOTE).get().trim();
            if (notePart.isEmpty()) {
                throw new ParseException("Note cannot be empty");
            }
            return new EditSessionCommand(householdId, sessionIndex, datePart, timePart, notePart);
        } else {
            return new EditSessionCommand(householdId, sessionIndex, datePart, timePart);
        }
    }
}





