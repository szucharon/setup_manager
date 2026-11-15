package hu.setup_manager.model;

public class InvalidSetupException extends RuntimeException {

    public InvalidSetupException(String message) {
        super(message);
    }
}