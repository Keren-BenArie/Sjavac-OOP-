package oop.ex6.main.Varaibles;
import oop.ex6.main.MainExceptions.VariableException;

/** The class exception of Illegal assignment.*/
public class VariableIllegalAssignmentException extends VariableException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public VariableIllegalAssignmentException(){
        this.informativeMessage = "An invalid assignment line";
    }
}
