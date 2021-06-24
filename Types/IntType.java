package oop.ex6.main.Types;
import java.util.regex.Pattern;

/** A class defining an int type*/
public class IntType extends Type {

    /* A String of a valid int type */
    private final static String INT_ASSIGNMENT_SYNTAX = "^ *-?\\d+";

    /* A Pattern of a valid int type */
    private final static Pattern INT_PATTERN = Pattern.compile(INT_ASSIGNMENT_SYNTAX);


    /**
     * An int type constructor.
     * @param name the name of the given type
     * @param value the value of given type
     * @throws ValueDoesNotMatchTypeException is thrown if the value does not match the type.
     */
    public IntType(String name, String value) throws ValueDoesNotMatchTypeException {
        super();
        if (super.matchValueWithTypeIncludePattern(value, INT_PATTERN)) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * A method that sets the value of a given variable.
     * @param value the given value.
     * @throws ValueDoesNotMatchTypeException is thrown if the value does not match the type.
     */
    public void setValue(String value) throws ValueDoesNotMatchTypeException {
        if (super.matchValueWithTypeIncludePattern(value, INT_PATTERN))
            this.value = value;
    }
}
