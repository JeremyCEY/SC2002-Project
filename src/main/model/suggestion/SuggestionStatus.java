package main.model.suggestion;

public enum SuggestionStatus {
    PENDING,
    APPROVED,
    REJECTED;

    public String showColorfulStatus(){
        return switch (this) {
            case APPROVED -> "\u001B[32m" + this + "\u001B[0m"; // green
            case REJECTED -> "\u001B[31m" + this + "\u001B[0m"; // red
            case PENDING -> "\u001B[34m" + this + "\u001B[0m"; // blue
        };
    }
}
