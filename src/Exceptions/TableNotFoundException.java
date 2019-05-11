package Exceptions;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String s) {
        super(s);
    }
}
