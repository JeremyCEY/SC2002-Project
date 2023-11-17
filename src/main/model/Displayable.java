package main.model;

import java.util.List;

public interface Displayable {
    //returns formatted string representation of the object
    String getDisplayableString();

    String getDisplayableStringWithType(List<String> type);

    //returns the splitter used to separate different fields of the object in the formatted string representation
    String getSplitter();
}
