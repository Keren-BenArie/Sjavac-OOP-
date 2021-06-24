package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.TypeException;
import oop.ex6.main.MainExceptions.VariableException;
import oop.ex6.main.Varaibles.*;
import oop.ex6.main.Scope.Scope;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class, OneDeclarationAnalyzer, represents one line in the file we get that represents a decleration line
 * @author Keren Ben Arie and Dana Adam
 */
public class DeclarationReader implements VariablesReader{

    /* This constant represents a regex of a single underscore */
    private static final String SINGLE_UNDERSCORE = "\\b_\\b";
    /* This constant is a pattern of the regex of a single underscore */
    private static final Pattern ILLEGAL_NAME = Pattern.compile(SINGLE_UNDERSCORE);
    /* This constant represents the symbol of equals */
    private static final String EQUAL = "=";
    /* This constant is a pattern of the symbol of equals */
    private static final Pattern EQUALS_PATTERN = Pattern.compile("=");
    /* This constant represents the keyword 'final' */
    private static final String FINAL = "final";
    /* This constant is a pattern of the keyword 'final' */
    private final static Pattern FINAL_FIRST = Pattern.compile("\\s*final\\s+");
    /* This constant is a pattern of the symbol of comma */
    private final static Pattern COMMA_PATTERN = Pattern.compile(",+");
    /* This constant represents the symbol of comma */
    private final static String COMMA= ",";
    /* This constant represents the symbol of type */
    private final static String[] TYPES = {"final","String","int","double","boolean","char"};
    /* This constant represents the symbol of type */
    private final static List<String> TYPES_LIST = Arrays.asList(TYPES);
    /* This constant is a pattern which represents the valid keywords of a variable declaration */
    private final static Pattern TYPE_PATTERN = Pattern.compile("\\s*(?:String|int|double|boolean|char)\\s*");
    /* This constant represents a string of semi-colon*/
    private static final String SEMI_COLON = ";";

    /** This method breaks one declaration into one variable before checking its validity
     * @param line - the line of the declaration to break
     * @return Variable with the data from the declaration line
     * @throws VariableException,TypeException The exceptions thrown
     */
    public static void breakDeclarationToVariables(String line, Scope scope) throws
            VariableException, TypeException {
    boolean isFinal = false;
    HashMap<String,String> multipleVars = new HashMap<>();
    String type;
     Matcher matcher = FINAL_FIRST.matcher(line);
    if (matcher.find()) {
        isFinal = true;
        line = VariablesReader.cutString(FINAL, line, matcher.end()); // now there's the type and the assignment
    }

    matcher = TYPE_PATTERN.matcher(line);
    if (matcher.find()) {
        int index = matcher.end();
        type = line.substring(0,index).trim();
        line = VariablesReader.cutString(type,line,index); // now there's only the assignment
    }
    else throw new IllegalVariableDeclarationException(); // not a valid type

    if (matcher.find()) throw new IllegalVariableDeclarationException();

    matcher = ILLEGAL_NAME.matcher(line);
    if (matcher.find())
        throw new IllegalVariableNameException(); // has _ has a name of a variable

    matcher = COMMA_PATTERN.matcher(line);
    if (matcher.find()) {
        if (!line.contains(EQUAL)) // without assignment
            splitToMultipleVariables(multipleVars, line, scope);
         else // // with assignment
            putNameAndValuesInSet(line, multipleVars, scope, isFinal);
    }

    else if(!line.contains(EQUAL) && line.replaceAll(SEMI_COLON,"").trim().contains(" ")){
        throw new IllegalVariableDeclarationException();
    }

    else if(line.contains(EQUAL) && line.substring(line.indexOf(EQUAL) + 1)
            .replaceAll(SEMI_COLON,"").trim().contains(" ")) {
        throw new IllegalVariableDeclarationException();
    }

    else { // Only one variable
        line = line.replaceAll(SEMI_COLON,"");
        String name;
        if (!line.contains(EQUAL)){  // without assignment
            if (isFinal)
                throw new FinalVariableWasNotAssigned();
            name = VariablesReader.cleanString(line);
            if (!scope.getVariablesNames().contains(name))
                multipleVars.put(name, null);
            else
                throw new VariableNameAlreadyAssignedException();
        }
        else{ // with assignment
            String[] nameAndValue = VariablesReader.cleanNameAndValue(line);
            name = nameAndValue[0].trim();
            String value = nameAndValue[1].trim().replaceAll(SEMI_COLON,"");
            if (scope.getVariablesNames() == null || !scope.getVariablesNames().contains(name)) {
                String prevValue = value;
                value = isAssignment(value,scope);
                if (scope.getMethod() != null) {
                    boolean isMethodParam = scope.getMethod().getMethodsParamsNames().contains(prevValue);
                    if (value == null && !isMethodParam) throw new IllegalVariableDeclarationException();
                }
                else if (value == null) throw new IllegalVariableDeclarationException();
                multipleVars.put(name, value);
            }
            else
                throw new VariableNameAlreadyAssignedException();
        }
    }
        ArrayList<Variable> variables = VariablesFactory.createVariableWithType
                (new Variable(type, multipleVars, false, false, isFinal));
    scope.addToScope(variables);
}


    /** This method put in the hash map the values according to its declaration.
     * @param line The full string line of the declaration.
     * @return The hash-map of names and values (by declaration).
     */
    private static HashMap<String,String> putNameAndValuesInSet(String line, HashMap<String,String> multipleVars,
                                                                Scope scope, boolean isFinal)
            throws VariableException {
        String fixedLine = line.replaceAll(COMMA, " " + COMMA +" ");
        fixedLine = fixedLine.replaceAll(SEMI_COLON, " " + SEMI_COLON +" ");
        fixedLine = fixedLine.replaceAll(EQUAL, " " + EQUAL +" ");
        String[] array = fixedLine.split(" +");
        String prevStr = null;
        String varWithValue = null;
        boolean justAssigned = false;
        for (String str: array){
            if (TYPES_LIST.contains(str))
                throw new IllegalVariableDeclarationException();
            switch (str){
                case SEMI_COLON:
                case COMMA:
                    if (!justAssigned) {
                        if (scope.getVariablesNames() == null || !scope.getVariablesNames().contains(prevStr)) {
                            if (!isFinal)
                                multipleVars.put(prevStr, null);
                            else
                                throw new FinalVariableWasNotAssigned();
                        }
                        else throw new VariableNameAlreadyAssignedException();
                            justAssigned = false;
                    }
                    break;
                case EQUAL:
                    varWithValue = prevStr;
                    break;
                default:
                    justAssigned = false;
                    break;
            }
            if (prevStr != null && prevStr.equals(EQUAL)) {
                if (scope.getVariablesNames() == null || !scope.getVariablesNames().contains(varWithValue)) {
                    String checkForMethodStr = str;
                    str = isAssignment(str,scope);
                    if (scope.getMethod() != null) {
                        boolean isMethodParam = scope.getMethod().getMethodsParamsNames().contains(checkForMethodStr);
                        if (str == null && !isMethodParam) throw new IllegalVariableDeclarationException();
                    }
                    else if (str == null) throw new IllegalVariableDeclarationException();
                    multipleVars.put(varWithValue, VariablesReader.cleanString(str));
                    justAssigned = true;
                }
                else throw new VariableNameAlreadyAssignedException();
            }
            prevStr = str;
        }
        return multipleVars;
    }

    /* This method gets multiple vars hashmap and the line, and put null into its value
     * @param multipleVars - an empty HashMap
     * @param line - the line to get the variables from
     * @return the updated HashMap of the names of the variable and null value
     */
    private static HashMap<String,String> splitToMultipleVariables
            (HashMap<String,String> multipleVars, String line, Scope scope)
            throws VariableNameAlreadyAssignedException, IllegalVariableDeclarationException {
        line = VariablesReader.cleanString(line);
        String [] multipleVarsArray = line.split(COMMA);
        for (String var: multipleVarsArray) {
            if (!scope.getVariablesNames().contains(var))
                multipleVars.put(var, null);
            else throw new VariableNameAlreadyAssignedException();
        }
        return multipleVars;
    }

    /*
    This method checks if an assignment is made to the declared value or not, and return the value.
     */
    private static String isAssignment(String value, Scope scope){
        int index;
        if (scope.getVariablesNames().contains(value)){
            index = scope.getVariablesNames().indexOf(value);
            value = scope.getVariables().get(index).getValue();
        }
        else if (scope.getUpperScopeVariablesNames().contains(value)){
            index = scope.getUpperScopeVariablesNames().indexOf(value);
            value = scope.getUpperScopeVariables().get(index).getValue();
        }
        return value;
    }



}