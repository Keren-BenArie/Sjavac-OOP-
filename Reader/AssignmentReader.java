package oop.ex6.main.Reader;
import oop.ex6.main.MainExceptions.TypeException;
import oop.ex6.main.MainExceptions.VariableException;
import oop.ex6.main.Scope.Scope;
import oop.ex6.main.Varaibles.Variable;
import oop.ex6.main.Varaibles.VariableIllegalAssignmentException;
import oop.ex6.main.Varaibles.VariablesFactory;
import java.util.ArrayList;

/** This class, AssignmentAnalyzer, is a class which handles a line which indicates an assignment
 * @author Dana Adam and Keren Ben Arie
 */
public class AssignmentReader implements VariablesReader{

    private static final String COMMA = ",";
    private static final String SEMI_COLON = ";";

    /**
     * This method breaks the line assignment of variables to different cases and by it determines if only one
     * or a few assignments were made and also if the assignments were legal and match value with type.
     * @param line the line of assignment (string).
     * @param scope the current scope in which the assignment is made.
     * @throws VariableIllegalAssignmentException an exception that occurs when an illegal match between type and value is
     * made.
     * @throws TypeException an exception that occurs when an illegal pattern was given to the specific type.
     */
    public static void breakAssignmentToVariables(String line, Scope scope) throws
            VariableIllegalAssignmentException, TypeException {
        int index;
        Variable v;
        Variable local;
        String valueOfLocal = null;
        String[] params = line.split(COMMA);
        if (params.length < countOccurrences(EQUAL, line))
            throw new VariableIllegalAssignmentException();
        for (String param : params) {
            boolean conditionLocalVars = scope.getVariablesNames().contains(VariablesReader.cleanString(param));
            if (conditionLocalVars){
                int indexOf = scope.getVariablesNames().indexOf(VariablesReader.cleanString(param));
                local = scope.getVariables().get(indexOf);
                valueOfLocal = local.getValue();
            }
            if (param.equals(VariablesReader.cleanString(param))) {
                if (valueOfLocal != null)
                    throw new VariableIllegalAssignmentException();
                if (!conditionLocalVars)
                param += SEMI_COLON; // change it to definition
            }
            else if (!param.contains(EQUAL) && !param.contains(SEMI_COLON))
                throw new VariableIllegalAssignmentException();
            String[] nameAndValue = VariablesReader.cleanNameAndValue(param);
            if (!scope.getVariablesNames().contains(nameAndValue[0])) {
                if (!scope.getUpperScopeVariablesNames().contains(nameAndValue[0]))
                    throw new VariableIllegalAssignmentException();
                else {
                    index = scope.getUpperScopeVariablesNames().indexOf(nameAndValue[0]);
                    v = scope.getUpperScopeVariables().get(index);
                }
            } else {
                index = scope.getVariablesNames().indexOf(nameAndValue[0]);
                v = scope.getVariables().get(index);
            }
            if (v.getIsFinal())
                throw new VariableIllegalAssignmentException();
            String value = nameAndValue[1];
            if (scope.getVariablesNames().contains(value)) {
                int indexOfValue = scope.getVariablesNames().indexOf(value);
                Variable v1 = scope.getVariables().get(indexOfValue);
                value = v1.getValue();
            } else if (scope.getUpperScopeVariablesNames().contains(value)) {
                int indexOfValue = scope.getUpperScopeVariablesNames().indexOf(value);
                Variable v1 = scope.getUpperScopeVariables().get(indexOfValue);
                value = v1.getValue();
            }
            v.getType().setValue(value);
            ArrayList<Variable> variables = VariablesFactory.createVariableWithType(v);
            scope.addToScope(variables);

        }
    }


    /**
     * This method counts the number of occurrences in an assignment line.
     * @param str The string separating the occurrences.
     * @param line The full line needed to be separated.
     * @return The number of occurrences.
     */
    private static int countOccurrences(String str, String line){
        int counter = 0;
        while (line.contains(str)) {
            counter++;
            line = line.replaceFirst(str,"");
        }
        return counter;



    }

}