package oop.ex6.main.Scope;
import oop.ex6.main.MainExceptions.ConditionException;

/** The class exception of Illegal Condition Structure.*/
public class IllegalConditionStructureException extends ConditionException {
    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public IllegalConditionStructureException(){
        this.informativeMessage = "The structure of the given condition is illegal";

    }
}
