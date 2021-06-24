package oop.ex6.main.MainExceptions;

/** The class exception of comments.*/
public class CommentException extends Exception{
    private static final long serialVersionUID = 1L;
    protected String informativeMessage;

    /**
     * Prints the informative error message.
     */
    public void printErrorMSG(){
        System.err.println("ERROR: " + informativeMessage);
    }

}