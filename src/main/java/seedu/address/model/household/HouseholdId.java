package seedu.address.model.household;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Household's ID in the household book.
 * Guarantees: immutable; ID is valid as declared in {@link #isValidId(String)}
 */
public class HouseholdId {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid Household ID. It should start with 'H' followed by a positive number.";
    private static long idCounter = 0;

    public final String value;

    /**
     * Constructs a new {@code HouseholdId}.
     */
    private HouseholdId(long value) {
        this.value = "H" + value;
    }

    /**
     * Creates a new HouseholdId with an auto-generated ID.
     */
    public static HouseholdId generateNewId() {
        long newId = idCounter + 1;
        HouseholdId householdId = new HouseholdId(newId);
        idCounter = newId; // Only increment if successfully created
        return householdId;
    }

    /**
     * Creates a HouseholdId from an existing ID string.
     */
    public static HouseholdId fromString(String id) {
        requireNonNull(id);
        if (!isValidId(id)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        long storedId = Long.parseLong(id.substring(1));
        idCounter = Math.max(idCounter, storedId);
        return new HouseholdId(storedId);
    }

    /**
     * Returns true if a given string is a valid household ID.
     */
    public static boolean isValidId(String test) {
        if (test == null || test.length() < 2 || !test.toUpperCase().startsWith("H")) {
            return false;
        }
        try {
            long parsedId = Long.parseLong(test.substring(1));
            return parsedId > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof HouseholdId
                && value.equals(((HouseholdId) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
