package main.utils.parameters;

/**
 * The {@code EmptyID} class provides utility methods for working with empty or null IDs.
 */
public class EmptyID {
    /**
     * A constant representing an empty ID, with the value "null".
     */
    public static final String EMPTY_ID = "null";

    /**
     * Checks if the specified ID is empty or null.
     *
     * @param ID The ID to be checked.
     * @return {@code true} if the specified ID is empty or null, otherwise {@code false}.
     */
    public static boolean isEmptyID(String ID) {
        return ID == null || ID.isBlank() || ID.equalsIgnoreCase(EMPTY_ID);
    }
}
