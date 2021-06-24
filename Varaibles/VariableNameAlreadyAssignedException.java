package oop.ex6.main.Varaibles;
import oop.ex6.main.MainExceptions.VariableException;

/** The class exception of repeated variable name (in the same scope).*/
public class VariableNameAlreadyAssignedException extends VariableException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public VariableNameAlreadyAssignedException() {
        this.informativeMessage = "There are 2 variables which are  in the same scope, with the same name";
    }
}

