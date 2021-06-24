package oop.ex6.main.Methods;
import oop.ex6.main.MainExceptions.MethodException;

/** The class exception of brackets.*/
public class NoFunctionBracketsException extends MethodException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public NoFunctionBracketsException(){
        this.informativeMessage = "There is a method without closing bracket";
    }

}
