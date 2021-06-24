package oop.ex6.main.Types;
import java.util.regex.Pattern;

/** A class defining a boolean type*/
public class BooleanType extends Type{

    /* A String of a valid boolean type */
    private final static String BOOLEAN_ASSIGNMENT_SYNTAX = "^ *(?:true|false)|(?:-?\\d+(?:\\.\\d*)?)";

    /* A pattern of a valid boolean type */
    private final static Pattern BOOLEAN_PATTERN = Pattern.compile(BOOLEAN_ASSIGNMENT_SYNTAX);

    /**
     * A boolean type constructor.
     * @param name the name of the given type
     * @param value the value of given type
     * @throws ValueDoesNotMatchTypeException is thrown if the value does not match the type.
     */
    public BooleanType(String name, String value) throws ValueDoesNotMatchTypeException {
        super();
        if (super.matchValueWithTypeIncludePattern(value, BOOLEAN_PATTERN)) {
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
        if (super.matchValueWithTypeIncludePattern(value, BOOLEAN_PATTERN))
            this.value = value;
    }

}
