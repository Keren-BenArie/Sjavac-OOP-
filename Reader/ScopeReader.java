package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.*;
import oop.ex6.main.Scope.IllegalConditionStructureException;
import oop.ex6.main.Scope.IllegalReturnStatementException;
import oop.ex6.main.Methods.Method;
import oop.ex6.main.Scope.Scope;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** The scope reader class.*/
public class ScopeReader {

    /* The Pattern of valid method call. */
    private final static Pattern METHOD_CALL = Pattern.compile("\\s*[a-zA-Z]+[\\w]*\\s*\\(.*\\)\\s*;");

    /* The Pattern of valid condition. */
    private final static Pattern IF_WHILE = Pattern.compile("\\s*(if|while)\\s*\\(.*\\)\\s*\\{\\s*");

    /* The Pattern of valid commentLine. */
    private final static Pattern COMMENT = Pattern.compile(".//.");

    /* The Pattern of possible variable declaration line. */
    private final static Pattern VARIABLE_DECLARATION = Pattern.compile("\\s*(?:(?:final)\\s+)?\\s*(?:" +
            "String|int|double|boolean|char)\\s+((([a-zA-Z_]+\\w*\\s*),?\\s*)+\\s*(=\\s*.+)?\\s*)+;" +
            "\\s*");

    /* The Pattern of possible variable assignment line. */
    private final static Pattern VARIABLE_ASSIGNMENT = Pattern.compile
            ("\\s*((([a-zA-Z_]+\\w*\\s*),?\\s*)+\\s*(=\\s*.+)\\s*)+;\\s*");

    /* The Pattern of the return statement. */
    private final static Pattern RETURN = Pattern.compile("\\s*return\\s*;");

    /* The Pattern of the an end of scope. */
    private final static Pattern END_SCOPE = Pattern.compile("\\s*}\\s*");

    /* The Pattern of a blank line. */
    private final static Pattern BLANK_LINE = Pattern.compile("\\s*");

    /* A line counter. */
    private static int lineCounter;

    /**
     * This method scans the scopes content (methods and conditions) by recognizing each of the possible
     * line options as defines in the function (comments, variable declaration and assignments, method calls
     * conditions, return statements and brackets are possible).
     * @param scopeLines the entire scope lines.
     * @param method the specific method in which we are in at the current moment.
     * @param scope the specific scope in which we are in at the current moment.
     * @param existingMethods all the methods from the primary scan.
     * @throws MethodException an exception that occurs if a method id defines in an illegal way.
     * @throws ConditionException is thrown if a none boolean expression is given as a condition.
     * @throws VariableException an exception that occurs if there is no match between variable type and value.
     * @throws TypeException an exception that occurs if there is no match between a pattern with a given type.
     * @throws CommentException An exception that occurs when an illegal comment is made in the file.
     * @throws ScopeException an exception that occurs when an illegal scope is defined.
     */
    public static void scanScopeContent(ArrayList<String> scopeLines,
                                        Method method, Scope scope, HashMap<String, Method> existingMethods)
            throws MethodException, ConditionException, VariableException,
            TypeException, CommentException, ScopeException {
        Scope currentScope = scope;
        Matcher matcherMethodCall, matcherVariableDeclaration, matcherCondition, matcherComment,
                matcherVariableAssignment, matcherReturn, matcherEndScope,matcherBlank;
        boolean lastLineIsReturn = false;
        boolean isMethod = true;
        int bracketCounter = 0;
        for (String line : scopeLines) {
            currentScope.updateUpper();
            matcherMethodCall = METHOD_CALL.matcher(line);
            matcherVariableDeclaration = VARIABLE_DECLARATION.matcher(line);
            matcherCondition = IF_WHILE.matcher(line);
            matcherComment = COMMENT.matcher(line);
            matcherVariableAssignment = VARIABLE_ASSIGNMENT.matcher(line);
            matcherReturn = RETURN.matcher(line);
            matcherEndScope = END_SCOPE.matcher(line);
            matcherBlank = BLANK_LINE.matcher(line);
            lineCounter++;
            // method call
            if (matcherMethodCall.matches()) {
                lastLineIsReturn = false;
                MethodCallReader.readMethodCall(line, existingMethods, currentScope);
                // condition
            } else if (matcherCondition.matches()) {
                if (method == null)
                    throw new IllegalConditionStructureException();
                isMethod = false;
                currentScope = new Scope(currentScope.getLevel()+1,
                        new ArrayList<>(), method, currentScope);
                lastLineIsReturn = false;
                ConditionsReader.readCondition(line, currentScope, existingMethods);
                bracketCounter++;
                // comment
            } else if (matcherComment.matches()) {
                lastLineIsReturn = false;
                CommentsReader.readCommentLine(line);
                // variable-declaration
            } else if (matcherVariableDeclaration.matches()) {
                lastLineIsReturn = false;
                DeclarationReader.breakDeclarationToVariables(line, currentScope);
                // variable-assignment
            } else if (matcherVariableAssignment.matches()) {
                lastLineIsReturn = false;
                AssignmentReader.breakAssignmentToVariables(line, currentScope);
                // return-statement
            } else if (matcherReturn.matches()) {
                lastLineIsReturn = true;
                // end of scope
            } else if (matcherEndScope.matches()) {
                if (isMethod && !lastLineIsReturn && bracketCounter <= 0)
                    throw new IllegalReturnStatementException();
                if (lineCounter < scopeLines.size()) {
                    currentScope = currentScope.getParentScope();
                    isMethod = true;
                }lastLineIsReturn = false;
                bracketCounter--;
                // blank line
            } else if (matcherBlank.matches()){
                // something un-defined
            } else {
                throw new InvalidLineException(lineCounter);
            }
        }
    }


}