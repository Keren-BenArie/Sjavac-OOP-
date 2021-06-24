package oop.ex6.main.Varaibles;
import oop.ex6.main.MainExceptions.VariableException;

/** The class exception of Illegal Variable Declaration.*/
public class IllegalVariableDeclarationException extends VariableException {
    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public IllegalVariableDeclarationException(){
        this.informativeMessage = "One of the given variable's declaration is illegal";
    }
}
