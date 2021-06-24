package oop.ex6.main.Scope;
import oop.ex6.main.MainExceptions.ConditionException;

/** The class exception of Empty Condition.*/
public class EmptyConditionException extends ConditionException {
    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public EmptyConditionException(){
        this.informativeMessage = "The condition given is empty.";
    }
}