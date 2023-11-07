package main.model;

public interface Displayable {
    //returns formatted string representation of the object
    String getDisplayableString();

    //returns the splitter used to separate different fields of the object in the formatted string representation
    String getSplitter();
}
