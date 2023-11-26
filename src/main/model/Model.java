/*
 * @Author: tyt1130 tangyutong0306@gmail.com
 * @Date: 2023-11-08 19:38:58
 * @LastEditors: tyt1130 tangyutong0306@gmail.com
 * @LastEditTime: 2023-11-26 14:19:12
 * @FilePath: \SC2002-Project\src\main\model\Model.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/**
 * This package contains interfaces and classes related to the overall model structure
 * in the application. The Model interface defines a common structure for model
 * entities, ensuring they provide a unique identifier (ID) and support mapping to
 * facilitate data storage and retrieval.
 */
package main.model;

import main.utils.iocontrol.Mappable;

/**
 * An interface representing model entities in the application. Classes implementing
 * this interface should provide methods to retrieve a unique identifier (ID) and
 * support mapping for data storage and retrieval.
 */
public interface Model extends Mappable {
    /**
     * Returns the unique identifier (ID) of the model as a string.
     *
     * @return The string representing the unique identifier (ID) of the model.
     */
    String getID();
}
