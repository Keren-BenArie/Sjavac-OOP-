package oop.ex6.main.Methods;
import oop.ex6.main.MainExceptions.MethodException;

/** The class exception of illegal method params.*/
public class IllegalMethodParamsException extends MethodException {
    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public IllegalMethodParamsException(){
        this.informativeMessage = "The given method params are illegal";

    }
}
