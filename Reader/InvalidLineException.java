package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.ScopeException;

/** The class exception of InValid LineException Exception.*/
public class InvalidLineException extends ScopeException {

    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public InvalidLineException(int lineCounter) {
            this.informativeMessage = "There is a problem with line " + lineCounter;
        }
}
