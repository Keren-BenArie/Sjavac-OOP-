package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.*;
import oop.ex6.main.Methods.IllegalMethodNameException;
import oop.ex6.main.Methods.IllegalMethodStructureException;
import oop.ex6.main.Methods.Method;
import oop.ex6.main.Scope.Scope;
import oop.ex6.main.Varaibles.Variable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class, ReadLines is the managing class for scanning the given code
 * @author Dana Adam and Keren Ben Arie
 */
public class ReadLines {

    /* The hashmap of all methods read in the file */
    private final HashMap<String, Method> allMethods;

    /* The arrayList of all global variables */
    private ArrayList<Variable> globalVariables;

    /* The GLOBAL SCOPE scope */
    private final Scope globalScope;

    /* The line counter initialization. */
    private static int lineCounter = 0;

    /* The Pattern of valid method. */
    private final static Pattern METHOD = Pattern.compile("\\s*void\\s+[a-zA-Z]+\\w*\\s*\\(.*\\)\\s*\\{\\s*");

    /* The Pattern of valid comment. */
    private final static Pattern COMMENT = Pattern.compile(".*//.*");

    /* The Pattern of valid variable declaration. */
    private final static Pattern VARIABLE_DECLARATION = Pattern.compile("\\s*((?:(?:final)\\s+)?\\s*(?:" +
            "String|int|double|boolean|char)\\s+((([a-zA-Z_]+\\w*\\s*),?\\s*)+\\s*(=\\s*.+)?\\s*)+;\\s*)");

    /* The Pattern of variable assignment. */
    private final static Pattern VARIABLE_ASSIGNMENT = Pattern.compile
            ("\\s*((([a-zA-Z_]+\\w*\\s*),?\\s*)+\\s*(=\\s*.+)\\s*)+;\\s*");

    /* The Pattern of blank line. */
    private final static Pattern BLANK_LINE = Pattern.compile("\\s*");

    /* The brackets counter initialization. */
    private final static int BRACKET_COUNTER = 1;

    /* The open bracket string. */
    private final static String START_OF_SCOPE= "{";

    /* The closed bracket string. */
    private final static String END_OF_SCOPE= "}";

    /**
     * The readLines constructor- initializes the reader with all methods, global variables and global scope.
     */
    public ReadLines(){
        this.allMethods = new HashMap<>();
        this.globalVariables = new ArrayList<>();
        this.globalScope = new Scope(0, globalVariables, null, null);
    }


    /**
     * The primal scan method- this is the first scan that is made on the String file.
     * Thus scan only goes over the global scope thus if it runs into a method, it saves its content in the
     * method object and goes over ot only afterwards in the scopeReader method, after scanning it's basic
     * parameters at the signature in order to add it to the "all methods" array and scan for those in the
     * method calls. if this scan runs in an illegal expression for the global scope, it immediately throws
     * an exception.
     * @param entireFile The entire file as a string.
     * @throws CommentException An exception that occurs when an illegal comment is made in the file.
     * @throws MethodException an exception that occurs if a method id defines in an illegal way.
     * @throws VariableException an exception that occurs if there is no match between variable type and value.
     * @throws TypeException an exception that occurs if there is no match between a pattern with a given type.
     * @throws IOException a run time exception that can occur when reading the file.
     * @throws ScopeException an exception that occurs when an illegal scope is defined.
     */
    public void primalScan(String entireFile) throws CommentException, MethodException,
            VariableException, TypeException, IOException, ScopeException {
        BufferedReader reader = new BufferedReader(new FileReader(entireFile));
        String line = reader.readLine();
        while (line != null) {
            lineCounter ++;
            Matcher methodMatch = METHOD.matcher(line);
            Matcher commentMatch = COMMENT.matcher(line);
            Matcher blankMatch = BLANK_LINE.matcher(line);
            Matcher matcherVariableDeclaration = VARIABLE_DECLARATION.matcher(line);
            Matcher matcherVariableAssignment = VARIABLE_ASSIGNMENT.matcher(line);
            if (matcherVariableDeclaration.matches())
                DeclarationReader.breakDeclarationToVariables(line, this.globalScope);
            else if (matcherVariableAssignment.matches())
                AssignmentReader.breakAssignmentToVariables(line, this.globalScope);
            else if (methodMatch.matches()){
                ArrayList<String> methodLines = new ArrayList<>();
                reader = createScopeLines(methodLines,reader);
                Method method = MethodReader.checkMethod(line, methodLines, this.globalScope);
                if (allMethods.containsKey(method.getName()))
                    throw new IllegalMethodNameException();
                this.allMethods.put(method.getName(), method);
            }else if (commentMatch.matches())
                CommentsReader.readCommentLine(line);
            else if (blankMatch.matches()) {
            }else {
                throw new InvalidLineException(lineCounter);
            }line = reader.readLine();
        }
    }


    /**
     * The scanCode method, scans the code from inside a method (scope) after creating then in the primalScan.
     * @throws ScopeException an exception that occurs when an illegal scope is defined.
     * @throws TypeException an exception that occurs if there is no match between a pattern with a given type.
     * @throws MethodException an exception that occurs if a method id defines in an illegal way.
     * @throws VariableException an exception that occurs if there is no match between variable type and value.
     * @throws CommentException An exception that occurs when an illegal comment is made in the file.
     * @throws ConditionException An exception that occurs when an illegal condition is made.
     */
    public void scanCode() throws ScopeException, TypeException, MethodException, VariableException,
            CommentException, ConditionException{
        for (Map.Entry<String, Method> method: allMethods.entrySet()){
            ScopeReader.scanScopeContent(method.getValue().getArrayLines(), method.getValue(),
                    method.getValue().getScope(), allMethods);
        }

    }

    /* This method creates an array of scope lines (of a method) with the same bufferedReader and also returns
    * the same reader so it stops and continues the reading at the same point. The method knows how to
    * identify the beginning and the end of a scope by it's brackets. */
    private BufferedReader createScopeLines(ArrayList<String> linesAsList, BufferedReader reader) throws IOException,
            IllegalMethodStructureException {
        int bracketsCounter = BRACKET_COUNTER;
        String line = reader.readLine();
        while (line != null) {
            if (line.contains(START_OF_SCOPE))
                bracketsCounter ++;
            if (line.contains(END_OF_SCOPE))
                bracketsCounter --;
            linesAsList.add(line);
            if (bracketsCounter == 0)
                break;
            line = reader.readLine();
        }if (bracketsCounter != 0)
            throw new IllegalMethodStructureException();
        return reader;
    }

}