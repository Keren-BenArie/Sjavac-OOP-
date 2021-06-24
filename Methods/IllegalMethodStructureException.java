package oop.ex6.main.Methods;
import oop.ex6.main.MainExceptions.MethodException;

/** The class exception of illegal method structure.*/
public class IllegalMethodStructureException extends MethodException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public IllegalMethodStructureException(){
        this.informativeMessage = "The given method structure is illegal";

    }
}
