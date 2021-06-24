package oop.ex6.main.Methods;
import oop.ex6.main.MainExceptions.MethodException;

/** The class exception of not-existent method call.*/
public class NotExistingMethodException extends MethodException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public NotExistingMethodException() {
        this.informativeMessage = "One of the methods called does not exist";
    }
}
