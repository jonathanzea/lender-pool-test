package exception;

public class InvalidCSVFileFormatException extends RuntimeException {
    public InvalidCSVFileFormatException() {
        super("Invalid CSV format, check the file and ensure a proper configuration");
    }
}
