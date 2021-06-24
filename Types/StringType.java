package oop.ex6.main.Types;
import java.util.regex.Pattern;

/** A class defining a string type*/
public class StringType extends Type {

    /* A String of a valid String type */
    private final static String STRING_ASSIGNMENT_SYNTAX = "^ *\".*\"";

    /* A Pattern of a valid String type */
    private final static Pattern STRING_PATTERN = Pattern.compile(STRING_ASSIGNMENT_SYNTAX);

    /**
     * A String type constructor.
     * @param name the name of the given type
     * @param value the value of given type
     * @throws ValueDoesNotMatchTypeException is thrown if the value does not match the type.
     */
    public StringType(String name, String value) throws ValueDoesNotMatchTypeException {
        super();
        if (super.matchValueWithTypeIncludePattern(value, STRING_PATTERN)) {
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
        if (super.matchValueWithTypeIncludePattern(value, STRING_PATTERN))
            this.value = value;
    }
        
}
