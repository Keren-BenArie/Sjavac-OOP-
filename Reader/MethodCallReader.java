package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.TypeException;
import oop.ex6.main.Methods.*;
import oop.ex6.main.MainExceptions.MethodException;
import oop.ex6.main.Scope.Scope;
import oop.ex6.main.Varaibles.Variable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**This method is the reader of the method call */
public class MethodCallReader {

    /* A pattern of a legal method's name. */
    private static final Pattern LEGAL_NAME_PATTERN = Pattern.compile("\\s*[a-zA-Z]+[\\w]*\\s*\\(");

    /* A pattern of a legal integer number input. */
    private static final Pattern INT_NUMBER_PATTERN = Pattern.compile("-?\\d+");

    /* A pattern of a legal double number input. */
    private static final Pattern DOUBLE_NUMBER_PATTERN = Pattern.compile("-?\\d+.\\d+");

    /* A pattern of a legal char input. */
    private static final Pattern CHAR_PATTERN = Pattern.compile("^ *\'.\'");

    /* A pattern of a legal String input. */
    private static final Pattern STRING_PATTERN = Pattern.compile("^ *\".*\"");

    /* A pattern of a legal boolean input. */
    private static final Pattern BOOLEAN_PATTERN = Pattern.compile("true|false|-?\\d+(.\\d+)?");

    /* An empty String. */
    private static final String EMPTY = "";

    /* An "int" String. */
    private static final String INT = "int";

    /* A "double" String. */
    private static final String DOUBLE = "double";

    /* A "char" String. */
    private static final String CHAR = "char";

    /* A "String" String. */
    private static final String STRING = "String";

    /* A "Boolean" String. */
    private static final String BOOLEAN = "boolean";


    /**
     * This method reads the method call line and checks it's name validity and takes it's bracket input
     * in order to analyze it.
     * @param methodCall The method call line.
     * @param existingMethods All the existing methods Hashmap.
     * @param scope The current scope in which the method call is in.
     * @throws MethodException an exception that occurs if a method id defines in an illegal way.
     * @throws TypeException an exception that occurs if there is no match between variable type and value.
     */
    public static void readMethodCall(String methodCall, HashMap<String, Method> existingMethods, Scope scope)
            throws MethodException, TypeException {
        final Matcher matcherMethodName = LEGAL_NAME_PATTERN.matcher(methodCall);
        String nameOfMethod;
        if (matcherMethodName.find()) {
            nameOfMethod = methodCall.substring(matcherMethodName.start(), matcherMethodName.end() - 1).trim();
            if (!existingMethods.containsKey(nameOfMethod))
                throw new NotExistingMethodException();
            int openingBracket = methodCall.indexOf("(");
            int closingBracket = methodCall.indexOf(")");
            String paramsLine = methodCall.substring(openingBracket + 1, closingBracket).trim();
            if (paramsLine.equals(EMPTY)) {
                if (existingMethods.get(nameOfMethod).getMethodsParams().size() != 0)
                    throw new IllegalMethodParamsException();
                else
                    return;
            }
            checkParamsMatch(paramsLine, nameOfMethod, existingMethods, scope);
        } else {
            throw new IllegalMethodNameException();
        }
    }

    /**
     * The first examination to check if the params of the called method match the params of the
     * declared method (by name). This check is by absolute value and not by variables in scope.
     * @param paramsOfMethod The parameters of the called method.
     * @param methodsName The name of the method.
     * @param existingMethods All existing methods in order to check if the called one exists.
     * @param scope The current scope in which the call was made.
     * @throws TypeException an exception that occurs if there is no match between variable type and value.
     * @throws MethodException an exception that occurs if a method id defines in an illegal way.
     */
    private static void checkParamsMatch(String paramsOfMethod, String methodsName,
                                         HashMap<String, Method> existingMethods, Scope scope)
            throws TypeException, MethodException {
        boolean matchIt = true;
        ArrayList<Variable> originParams = existingMethods.get(methodsName).getMethodsParams();
        ArrayList<String> actualValues = new ArrayList<>();
        String[] params = paramsOfMethod.split(",");
        if (originParams.size() != params.length)
            throw new IllegalMethodParamsException();
        for (int i = 0; i < params.length; i++) {
            String strippedParam = params[i].trim();
            Matcher intNumberMatcher = INT_NUMBER_PATTERN.matcher(strippedParam);
            Matcher doubleNumberMatcher = DOUBLE_NUMBER_PATTERN.matcher(strippedParam);
            Matcher charMatcher = CHAR_PATTERN.matcher(strippedParam);
            Matcher stringMatcher = STRING_PATTERN.matcher(strippedParam);
            Matcher booleanMatcher = BOOLEAN_PATTERN.matcher(strippedParam);
            if (intNumberMatcher.matches() && originParams.get(i).getTypeName().equals(INT))
                actualValues.add(strippedParam);
            else if (doubleNumberMatcher.matches() && originParams.get(i).getTypeName().equals(DOUBLE))
                actualValues.add(strippedParam);
            else if (charMatcher.matches() && originParams.get(i).getTypeName().equals(CHAR))
                actualValues.add(strippedParam);
            else if (stringMatcher.matches() && originParams.get(i).getTypeName().equals(STRING))
                actualValues.add(strippedParam);
            else if (booleanMatcher.matches() && originParams.get(i).getTypeName().equals(BOOLEAN))
                actualValues.add(strippedParam);
            else {
                matchIt = false;
                break;
            }
        }if (matchIt) {
            for (int i = 0; i < originParams.size(); i++) {
                originParams.get(i).getType().setValue(actualValues.get(i));
            }
        } else
            checkParamsMatchSecondWay(params, scope, originParams);

    }

    /*
    The second examination to check if the params of the called method match the params of the
     * declared method (by name). This check is by variables in scope if they exist.
     */
    private static void checkParamsMatchSecondWay(String[] params,Scope scope,
                                                  ArrayList<Variable> originParams)
            throws TypeException, IllegalMethodParamsException {
        ArrayList<Variable> scopeParams = scope.getVariables();
        for (int i = 0; i < params.length; i++) {
            ArrayList<String> saveVariable = new ArrayList<>();
            String typeString = originParams.get(i).getTypeName();
            String strippedParam = params[i].trim();
            if (findVarInMethodsVars(scopeParams, strippedParam, INT, scope, saveVariable) && typeString.equals(INT))
                originParams.get(i).getType().setValue(saveVariable.get(0));
            else if (findVarInMethodsVars(scopeParams, strippedParam, DOUBLE, scope, saveVariable) && typeString.equals(DOUBLE))
                originParams.get(i).getType().setValue(saveVariable.get(0));
            else if (findVarInMethodsVars(scopeParams, strippedParam, CHAR, scope, saveVariable) && typeString.equals(CHAR))
                originParams.get(i).getType().setValue(saveVariable.get(0));
            else if (findVarInMethodsVars(scopeParams, strippedParam, STRING, scope, saveVariable) && typeString.equals(STRING))
                originParams.get(i).getType().setValue(saveVariable.get(0));
            else if (findVarInMethodsVars(scopeParams, strippedParam, BOOLEAN, scope, saveVariable) && typeString.equals(BOOLEAN))
                originParams.get(i).getType().setValue(saveVariable.get(0));
            else {
                throw new IllegalMethodParamsException();
            }

        }
    }

    /* A helper method to the previous method, checking in the current scope and it's parents scopes if the
    given variables are defined and have a matching value.
     */
    private static boolean findVarInMethodsVars (ArrayList <Variable> scopeParams, String
    paramName, String type, Scope scope, ArrayList<String> saveVariable){
        String varName, varTypeString;
        for (Variable var : scopeParams) {
            varName = var.getType().getName();
            varTypeString = var.getTypeName();
            if (varName.equals(paramName) && varTypeString.equals(type))
                if (var.getValue() == null && !var.getIsMethod()) return false;
                saveVariable.add(var.getValue());
                return true;
        }
        scopeParams = scope.getUpperScopeVariables();
        for (Variable var : scopeParams) {
            varName = var.getType().getName();
            varTypeString = var.getTypeName();
            if (varName.equals(paramName) && varTypeString.equals(type))
                if (var.getValue() == null && !var.getIsMethod()) return false;
                saveVariable.add(var.getValue());
                return true;
        }
        return false;
    }

}