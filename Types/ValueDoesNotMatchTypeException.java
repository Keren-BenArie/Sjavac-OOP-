package oop.ex6.main.Types;
import oop.ex6.main.MainExceptions.TypeException;

/** The class exception of illegal match of type an value.*/
public class ValueDoesNotMatchTypeException extends TypeException {
    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public ValueDoesNotMatchTypeException(){
        this.informativeMessage = "one of the values does not match the given type";
    }

}
