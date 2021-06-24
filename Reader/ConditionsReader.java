package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.ConditionException;
import oop.ex6.main.Methods.Method;
import oop.ex6.main.Scope.EmptyConditionException;
import oop.ex6.main.Scope.IllegalConditionStructureException;
import oop.ex6.main.Scope.Scope;
import oop.ex6.main.Varaibles.Variable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** The class which reads and analyzes the conditions.*/
public class ConditionsReader {

    /* The empty condition pattern. */
    private final static Pattern EMPTY_CONDITION = Pattern.compile("[\\s]*");

    /* The boolean variables String (int double and boolean). */
    private final static String BOOLEAN = "^ *(?:true|false)|(?:-?\\d+(?:\\.\\d*)?)";

    /* The boolean variables Pattern (int double and boolean). */
    private final static Pattern BOOLEAN_PATTERN = Pattern.compile(BOOLEAN);

    /* The int or double pattern */
    private final static Pattern INT_OR_DOUBLE_PATTERN = Pattern.compile("-?\\d+(.\\d+)?");

    /* if the line has anything other the the different spaces and tabs. */
    private final static Pattern ANYTHING_OTHER_THAN_BLANK = Pattern.compile("\\S+");

    /* 'true' String. */
    private final static String TRUE = "true";

    /* 'false' String. */
    private final static String FALSE = "false";

    /* The end of scope String. */
    private final static String START_OF_SCOPE= "{";

    /* The empty String. */
    private final static String EMPTY_STRING = "";
    
    private final static String[] BOOLEAN_ARRAY = {"int","double","boolean"};

    private final static List<String> BOOLEAN_TYPES = Arrays.asList(BOOLEAN_ARRAY);


    /**
     * This method reads the given condition (the inside of the brackets) and checks if the condition is
     * an empty condition and sends the condition to be checked if a boolean expression is made inside.
     * @param line The entire line of condition
     * @param scope The current scope of the given condition
     * @throws ConditionException is thrown if a none boolean expression is given as a condition.
     */
    public static void readCondition(String line, Scope scope, HashMap<String, Method> allMethods) throws ConditionException {
        Matcher matcherNotBlank = ANYTHING_OTHER_THAN_BLANK.matcher(line.substring(line.indexOf(START_OF_SCOPE) + 1));
        if (matcherNotBlank.matches())
            throw new IllegalConditionStructureException();
        int openingBracket = line.indexOf("(");
        int closingBracket = line.indexOf(")");
        String strippedLine = line.substring(openingBracket + 1, closingBracket);
        Matcher emptyCondition = EMPTY_CONDITION.matcher(strippedLine);
        if (emptyCondition.matches()){
            throw new IllegalConditionStructureException();
        }
        checkMultipleConditions(strippedLine, scope);
    }


    /**
     * This method is given multiple conditions (with || or && expressions) , splits the different conditions
     * and determines if each one of them are boolean.
     * @param conditionLine the entire condition line.
     * @param scope the current scope in which the condition is made.
     * @throws ConditionException is thrown if a none boolean expression is given as a condition.
     */
    private static void checkMultipleConditions(String conditionLine, Scope scope) throws
            ConditionException {
        String[] conditions = conditionLine.split("[|&]{2}");
        for (String s : conditions) {
            String condition = s.trim();
            if (condition.equals(EMPTY_STRING)) {
                throw new EmptyConditionException();
            } else {
                if (!checkReservedBoolean(condition) && !checkInitializedType(condition, scope)
                        && !checkDoubleOrIntConstant(condition)) {
                    throw new ConditionException();
                }
            }
        }
    }

    /* This method checks if the given constant is double or integer. */
    private static boolean checkDoubleOrIntConstant(String condition) {
        Matcher intOrDouble = INT_OR_DOUBLE_PATTERN.matcher(condition);
        return intOrDouble.matches();
    }

    /* This method checks if the given variable is initialized as one of the boolean types. */
    private static boolean checkInitializedType(String condition, Scope scope) {
        ArrayList<Variable> variables = scope.getVariables();
        if (searchForVariable(condition, variables))
            return true;
        variables = scope.getUpperScopeVariables();
        return searchForVariable(condition, variables);
    }


    /* This method searches for the variable inside the scope and also it's legal inherited scopes.*/
    private static boolean searchForVariable(String condition, ArrayList<Variable> variables){
        String varName;
        String varValue;
        Matcher matcher;

        for (Variable var: variables) {
            varName = var.getType().getName();
            varValue = var.getValue();
            if (varValue == null) {
                if (!varName.equals(condition)) continue;
                if (!var.getIsMethod()) return false;
                else if (BOOLEAN_TYPES.contains(var.getTypeName())) return true;
            }
            matcher = BOOLEAN_PATTERN.matcher(varValue);
            if (varName.equals(condition) && matcher.matches())
                return true;
        }
        return false;
    }


    /* This method checks for the boolean type words true\false.*/
    private static boolean checkReservedBoolean(String condition) {
        return condition.equals(TRUE) || condition.equals(FALSE);
    }


}