package br.com.juliogriebeler.pingapp.exception;

public class DatabaseConnectionException extends Exception {

    public DatabaseConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
