/**
 * The main.model.request package contains interfaces and classes related to request
 * functionalities within the application.
 */
package main.model.request;

import main.model.Displayable;
import main.model.Model;

/**
 * The Request interface represents a generic request in the application. It extends
 * the Model and Displayable interfaces, providing methods for accessing and
 * managing information related to requests.
 */
public interface Request extends Model, Displayable {

    /**
     * Gets the unique identifier of the request.
     *
     * @return The ID of the request.
     */
    String getID();

    /**
     * Gets the ID of the camp associated with the request.
     *
     * @return The camp ID.
     */
    String getCampID();

    /**
     * Gets the ID of the sender of the request.
     *
     * @return The sender's ID.
     */
    String getSenderID();

    /**
     * Gets the ID of the replier (if any) for the request.
     *
     * @return The replier's ID.
     */
    String getReplierID();

    /**
     * Gets the status of the request (e.g., PENDING, REPLIED).
     *
     * @return The status of the request.
     */
    RequestStatus getRequestStatus();

    /**
     * Sets the unique identifier of the request.
     *
     * @param id The new ID to set.
     */
    void setID(String id);

    /**
     * Sets the camp ID associated with the request.
     *
     * @param campID The new camp ID to set.
     */
    void setCampID(String campID);

    /**
     * Sets the sender's ID for the request.
     *
     * @param senderID The new sender's ID to set.
     */
    void setSenderID(String senderID);

    /**
     * Sets the replier's ID for the request.
     *
     * @param replierID The new replier's ID to set.
     */
    void setReplierID(String replierID);

    /**
     * Sets the status of the request (e.g., PENDING, REPLIED).
     *
     * @param status The new status to set.
     */
    void setRequestStatus(RequestStatus status);

    /**
     * Default method to provide a string that can be used as a splitter or separator
     * in displaying information related to requests.
     *
     * @return A string representing a separator.
     */
    default String getSplitter() {
        return "====================================================";
    }
}
