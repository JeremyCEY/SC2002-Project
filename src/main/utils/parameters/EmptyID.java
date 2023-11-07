package main.utils.parameters;

public class EmptyID {
    public static final String EMPTY_ID = "null";

    //returns true if the specifified ID is empty or null, otherwise false
    public static boolean isEmptyID(String ID){
        return ID == null || ID.isBlank() || ID.equalsIgnoreCase(EMPTY_ID);
    }
}
