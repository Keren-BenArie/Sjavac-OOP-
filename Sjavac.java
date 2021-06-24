package oop.ex6.main;
import oop.ex6.main.MainExceptions.*;
import oop.ex6.main.Reader.ReadLines;
import java.io.IOException;

/** The main class */
public class Sjavac {

    /** The main method running the program and calling the reader classes. */
    public static void main(String[] args) {
        try{
        if (args == null)
            throw new IOException();
        String path = args[0];
        ReadLines readLines = new ReadLines();
        readLines.primalScan(path);
        readLines.scanCode();
        System.out.println(0);
        }
        catch (IOException e) {
            System.out.println(2);
            System.err.println(e.getMessage());
        }
        catch (VariableException | ScopeException |
                MethodException | TypeException | 
                ConditionException | CommentException e) {
            System.out.println(1);
        }
    }
}

