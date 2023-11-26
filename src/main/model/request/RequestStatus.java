/**
 * The main.model.request package contains an enumeration representing different
 * status values for requests within the application.
 */
package main.model.request;

/**
 * The RequestStatus enum represents the possible status values for a request.
 * Status values include APPROVED, DENIED, REPLIED, and PENDING.
 */
public enum RequestStatus {

    /**
     * The request has been approved.
     */
    APPROVED,

    /**
     * The request has been denied.
     */
    DENIED,

    /**
     * The request has been replied to.
     */
    REPLIED,

    /**
     * The request is pending and has not been processed yet.
     */
    PENDING;

    /**
     * Returns a colorful representation of the request status for display
     * purposes. Uses ANSI escape codes for color.
     *
     * @return A colorful representation of the request status.
     */
    public String showColorfulStatus() {
        return switch (this) {
            case REPLIED -> "\u001B[32m" + this + "\u001B[0m"; // green
            case APPROVED -> "\u001B[32m" + this + "\u001B[0m"; // green
            case DENIED -> "\u001B[31m" + this + "\u001B[0m"; // red
            case PENDING -> "\u001B[34m" + this + "\u001B[0m"; // blue
        };
    }
}
