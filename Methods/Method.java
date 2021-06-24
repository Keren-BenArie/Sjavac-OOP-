package oop.ex6.main.Methods;
import oop.ex6.main.Scope.Scope;
import oop.ex6.main.Varaibles.Variable;
import oop.ex6.main.Varaibles.VariablesFactory;
import oop.ex6.main.MainExceptions.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Method object
 */
public class Method {

    /* The methods name. */
    private final String methodName;

    /* The 'final' prefix name. */
    private static final String FINAL = "final";

    /* The space String representation. */
    private static final String SPACE_STRING = " ";

    /* The method params. */
    private final ArrayList<Variable> methodsParams;

    /* The lines of the method as an arraylist. */
    private final ArrayList<String> lines;

    /* The Scope object this methods represents. */
    private final Scope scope;




    /**
     * Constructor of the method object
     * @param methodName the given name of the method
     * @param params the arrayList of strings defining params in the method
     * @param lines lines of the method as an array
     * @throws TypeException thrown exception - from the createVariables method.
     */
    public Method(String methodName, ArrayList<String> params, ArrayList<String> lines, Scope globalScope)
            throws TypeException {
        this.methodsParams = new ArrayList<>();
        this.methodName = methodName;
        this.lines = lines;
        this.createVariables(params);
        this.scope = new Scope(1, methodsParams, this, globalScope);

    }

    /**
     * getter for the current scope
     * @return the current scope
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * getter to the lines of the method (as an array)
     * @return the array of lines
     */
    public ArrayList<String> getArrayLines(){
        return this.lines;
    }

    /**
     * getter to the name of the method
     * @return method's name (String).
     */
    public String getName() {
        return this.methodName;
    }

    /**
     * The method creating variables from a simple string of description containing a prefix final if exists,
     * a type (describes by a string) and the name of the variable.
     * @param params The array of strings describing the parameters
     * @throws TypeException thrown if an illegal match between type and value is made.
     */
    public void createVariables(ArrayList<String> params) throws TypeException {
        if (params.size() > 0) {
            for (String param: params) {
                boolean finalPrefix = false;
                String declaration = param.trim();
                if (declaration.startsWith(FINAL)) {
                    finalPrefix = true;
                    declaration = declaration.substring(declaration.indexOf(SPACE_STRING) + 1).trim();
                }
                String typeOfVar = declaration.substring(0, declaration.indexOf(SPACE_STRING));
                String nameOfVar = declaration.substring(declaration.indexOf(SPACE_STRING) + 1);
                HashMap<String, String> varsOfType = new HashMap<>();
                varsOfType.put(nameOfVar, null);
                Variable variable = new Variable(typeOfVar, varsOfType, false, true,
                        finalPrefix);
                Variable newVar = VariablesFactory.createVariableWithType(variable).get(0);
                methodsParams.add(newVar);
            }
        }
    }

    /**
     * getter to the method's parameters as an array of variables.
     * @return The arrayList of variables.
     */
    public ArrayList<Variable> getMethodsParams() {
        return this.methodsParams;
    }


    /**
     * getter to the methods parameters names
     * @return The array list of names.
     */
    public ArrayList<String> getMethodsParamsNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Variable var: this.methodsParams){
            names.add(var.getType().getName());
        }
        return names;
    }
}