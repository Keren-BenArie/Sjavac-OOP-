package oop.ex6.main.Varaibles;
import oop.ex6.main.MainExceptions.VariableException;

/** The class exception of un-assignment to an final variable.*/
public class FinalVariableWasNotAssigned extends VariableException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public FinalVariableWasNotAssigned(){
        this.informativeMessage = "There are final variable without assignment";
    }
}
