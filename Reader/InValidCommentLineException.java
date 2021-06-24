package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.CommentException;

/** The class exception of InValid CommentLine Exception.*/
public class InValidCommentLineException extends CommentException {
    private static final long serialVersionUID = 1L;

    /**
     * defines the informative error message to it's father class(from which it inherits).
     */
    public InValidCommentLineException(){
        this.informativeMessage = "An invalid comment line";
    }
}
