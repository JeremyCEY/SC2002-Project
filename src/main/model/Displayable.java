/*
 * @Author: tyt1130 tangyutong0306@gmail.com
 * @Date: 2023-11-08 19:38:58
 * @LastEditors: tyt1130 tangyutong0306@gmail.com
 * @LastEditTime: 2023-11-26 14:18:09
 * @FilePath: \SC2002-Project\src\main\model\Displayable.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/**
 * This package contains interfaces and classes related to the overall model and display
 * functionality in the application. The Displayable interface defines methods for
 * generating formatted string representations of objects, allowing them to be easily
 * displayed in the user interface. Classes implementing this interface provide
 * customized display output for various model entities.
 */
package main.model;

/**
 * An interface representing objects that can be displayed in a formatted manner.
 * Classes implementing this interface provide methods to generate string representations
 * suitable for display in the user interface. The methods include variations for
 * displaying objects with or without a specified type.
 */
public interface Displayable {
    /**
     * Returns a formatted string representation of the object.
     *
     * @return The formatted string representation of the object.
     */
    String getDisplayableString();

    /**
     * Returns a formatted string representation of the object with the specified type.
     *
     * @param type The type of the object to be included in the display.
     * @return The formatted string representation of the object with the specified type.
     */
    String getDisplayableStringWithType(String type);

    /**
     * Returns the splitter used to separate different fields of the object in the
     * formatted string representation.
     *
     * @return The string used as a splitter in the formatted representation.
     */
    String getSplitter();
}

