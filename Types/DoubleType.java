package oop.ex6.main.Types;
import java.util.regex.Pattern;

/** A class defining a double type*/
public class DoubleType extends Type {

    /* A String of a valid double type */
    private final static String DOUBLE_ASSIGNMENT_SYNTAX = "^ *-?\\d+(?:\\.\\d*)?";

    /* A Pattern of a valid double type */
    private final static Pattern DOUBLE_PATTERN = Pattern.compile(DOUBLE_ASSIGNMENT_SYNTAX);

    /**
     * A double type constructor.
     * @param name the name of the given type
     * @param value the value of given type
     * @throws ValueDoesNotMatchTypeException is thrown if the value does not match the type.
     */
    public DoubleType(String name, String value) throws ValueDoesNotMatchTypeException {
        super();
        if (super.matchValueWithTypeIncludePattern(value, DOUBLE_PATTERN)) {
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
        if (super.matchValueWithTypeIncludePattern(value, DOUBLE_PATTERN))
            this.value = value;
    }
}
