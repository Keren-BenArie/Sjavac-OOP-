package oop.ex6.main.Varaibles;
import oop.ex6.main.MainExceptions.TypeException;
import oop.ex6.main.Types.*;
import java.util.ArrayList;
import java.util.HashMap;

/** This class, VariablesFactory, implements a factory design pattern for creating variables
 * @author Dana Adam and Keren Ben Arie*/
public class VariablesFactory {

    /* A string of int type */
    private static final String INTEGER = "int";

    /* A string of double type */
    private static final String DOUBLE = "double";

    /* A string of String type */
    private static final String STRING = "String";

    /* A string of boolean type */
    private static final String BOOLEAN = "boolean";

    /* A string of char type */
    private static final String CHAR = "char";

    /** This function is a factory function which creates the variables
     * @param var - the variable before editing it
     * @return An array of variables after editing the var input
     * @throws TypeException an exception thrown if type does not match pattern.
     */
    public static ArrayList<Variable> createVariableWithType(Variable var) throws TypeException {
        String type;
        if (var.getTypeName() == null)
            type = var.getType().getName();
        else type = var.getTypeName().trim();
        HashMap<String, String> varsOfType = var.getVarsOfType();
        ArrayList<Type> typesArray = new ArrayList<>();
        Type typeObject = null;
        if (varsOfType!= null) {
            for (String name : varsOfType.keySet()) {
                switch (type) {
                    case INTEGER:
                        typeObject = new IntType(name, varsOfType.get(name));
                        break;
                    case DOUBLE:
                        typeObject = new DoubleType(name, varsOfType.get(name));
                        break;
                    case STRING:
                        typeObject = new StringType(name, varsOfType.get(name));
                        break;
                    case BOOLEAN:
                        typeObject = new BooleanType(name, varsOfType.get(name));
                        break;
                    case CHAR:
                        typeObject = new CharType(name, varsOfType.get(name));
                        break;
                }
                typesArray.add(typeObject);
            }
        }
        ArrayList<Variable> variables = new ArrayList<>();
        for (Type typ: typesArray)
            variables.add(new Variable(typ, var.getIsGlobal(), var.getIsMethod(), var.getIsFinal(), type));

        return variables;
    }

}
