package oop.ex6.main.Types;
import java.util.regex.Pattern;

/** A class defining a char type*/
public class CharType extends Type {

    /* A String of a valid char type */
    private final static String CHAR_ASSIGNMENT_SYNTAX = "^ *\'.\'";

    /* A String of a valid char type */
    private final static Pattern CHAR_PATTERN = Pattern.compile(CHAR_ASSIGNMENT_SYNTAX);

    /**
     * A char type constructor.
     * @param name the name of the given type
     * @param value the value of given type
     * @throws ValueDoesNotMatchTypeException is thrown if the value does not match the type.
     */
    public CharType(String name, String value) throws ValueDoesNotMatchTypeException {
        super();
        if (super.matchValueWithTypeIncludePattern(value, CHAR_PATTERN)) {
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
        if (super.matchValueWithTypeIncludePattern(value, CHAR_PATTERN))
            this.value = value;
    }

}

