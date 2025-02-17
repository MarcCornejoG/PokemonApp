package practica10.exceptions;

public class ExcepcionGestorPokedex extends Exception {
    public ExcepcionGestorPokedex(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcepcionGestorPokedex(String message) {
        super(message);
    }

}
