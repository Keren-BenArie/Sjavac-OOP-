package oop.ex6.main.Scope;
import oop.ex6.main.MainExceptions.ScopeException;

/** The class exception of Illegal Return Statement.*/
public class IllegalReturnStatementException extends ScopeException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public IllegalReturnStatementException(){
        this.informativeMessage = "There is a problem with return statements";

    }
}
