package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.*;
import oop.ex6.main.Methods.IllegalMethodNameException;
import oop.ex6.main.Methods.IllegalMethodParamsException;
import oop.ex6.main.Methods.IllegalMethodStructureException;
import oop.ex6.main.Methods.Method;
import oop.ex6.main.Methods.NoFunctionBracketsException;
import oop.ex6.main.Scope.Scope;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** A class defining the method reader. */
public class MethodReader {

    /* The pattern of a legal method name */
    private static final Pattern LEGAL_NAME_PATTERN = Pattern.compile("\\s*[a-zA-Z]+[\\w]*\\s*\\(");

    /* The pattern of params (whats inside the bracket). */
    private static final Pattern PARAMS_PATTERN = Pattern.compile("\\(.*\\)");

    /* The pattern of empty params. */
    private static final Pattern EMPTY_PARAMS_PATTERN = Pattern.compile("\\s*");

    /* The pattern of something written (not blank). */
    private final static Pattern ANYTHING_OTHER_THAN_BLANK = Pattern.compile("\\S+");

    /* The pattern of spaces and closed bracket. */
    private static final String SPACES_AND_CLOSED_BRACKET = "\\s*|\\)|void";

    /* The String empty string. */
    private static final String EMPTY_STRING = "";

    /* The String space string. */
    private static final String SPACE_STRING = " ";

    /* The String start of scope. */
    private final static String START_OF_SCOPE= "{";

    /* The Pattern of 'final' prefix. */
    private static final Pattern FINAL_PREFIX = Pattern.compile("final");

    /* The existing legal types to define. */
    private final static ArrayList<String> ALL_TYPES = new ArrayList<>(Arrays.asList("int", "double",
            "String", "boolean", "char"));


    /**
     * The 'check method' method, checks the name and params of the given method. if they are legal, the
     * method-reader creates a method object
     * @param line The full line of a defined method
     * @param methodLines the method lines as an array of strings.
     * @param scope the scope in which the method is defined in.
     * @return a method object which is added to the current method object.
     * @throws MethodException an exception that occurs if a method id defines in an illegal way.
     * @throws TypeException an exception that occurs if there is no match between variable type and value.
     */
    public static Method checkMethod(String line, ArrayList<String> methodLines, Scope scope) throws MethodException,
            TypeException{
        Matcher matcherNotBlank = ANYTHING_OTHER_THAN_BLANK.matcher(line.substring(line.indexOf(START_OF_SCOPE) + 1));
        if (matcherNotBlank.matches())
            throw new IllegalMethodStructureException();
        String nameOfMethod = methodNameValidity(line);
        ArrayList<String> methodsParams = analyzeMethodParameters(line);
        return (new Method(nameOfMethod, methodsParams, methodLines, scope));
    }


    /* The method that checks if the name of the method is valid. */
    private static String methodNameValidity(String line) throws MethodException {
        Matcher nameMatch = LEGAL_NAME_PATTERN.matcher(line);
        if(!nameMatch.find()){
            throw new IllegalMethodNameException();
        }
        String methodName = line.substring(nameMatch.start(), nameMatch.end() - 1);
        return (cleanString(methodName));
    }

    /* The method that checks if the params of the method are valid. First in sends them to the
    * separateMethodParams method in order to split the different types of parameters and check
    * each one of them separately if legal.  */
    private static ArrayList<String> analyzeMethodParameters(String line) throws MethodException {
        Matcher paramsMatch = PARAMS_PATTERN.matcher(line);
        if (!paramsMatch.find()){
            throw new NoFunctionBracketsException();
        }else {
            String potentialParameters = line.substring(paramsMatch.start() + 1, paramsMatch.end() - 1);
            Matcher paramEmpty = EMPTY_PARAMS_PATTERN.matcher(potentialParameters);
            if (!paramEmpty.matches())
                return separateMethodParams(potentialParameters);
        }
        return new ArrayList<>();
    }

    /* This method splits the different types of parameters and checks each one of them separately if legal.*/
    private static ArrayList<String> separateMethodParams(String insideBrackets) throws
            IllegalMethodParamsException {
        String[] params = insideBrackets.split(",");
        HashMap<String, String> variables = new HashMap<>();
        ArrayList<String> vars = new ArrayList<>();
        for (String param : params) {
            Matcher finalMatcher = FINAL_PREFIX.matcher(param);
            String declaration = param.trim();
            String var = declaration;
            if (finalMatcher.find())
                var = declaration.substring(finalMatcher.end()).trim();
            if (!(ALL_TYPES.contains(var.substring(0, var.indexOf(SPACE_STRING)))))
                throw new IllegalMethodParamsException();
            String nameVar = (var.substring(var.indexOf(SPACE_STRING))).trim();
            if (nameVar.contains(" ")) throw new IllegalMethodParamsException();
            if (variables.containsKey(nameVar))
                throw new IllegalMethodParamsException();
            variables.put(nameVar, declaration);
            vars.add(declaration);
        }
        return vars;
    }

    /* This method 'cleans' the given string. */
    private static String cleanString(String toClean){
        return (toClean.replaceAll(MethodReader.SPACES_AND_CLOSED_BRACKET, EMPTY_STRING));

    }

}