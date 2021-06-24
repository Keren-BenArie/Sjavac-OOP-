package oop.ex6.main.Methods;
import oop.ex6.main.MainExceptions.MethodException;

/** The class exception of Illegal Method Name Exception.*/
public class IllegalMethodNameException extends MethodException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public IllegalMethodNameException(){
        this.informativeMessage = "The given method name is illegal";

    }
}
