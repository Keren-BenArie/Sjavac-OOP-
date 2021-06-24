package oop.ex6.main.Types;
import java.util.regex.*;


/** This class, Type,
 * @author Dana Adam and Keren Ben Arie
 */
public abstract class Type {

    /**The string name. */
    protected String name;

    /**The string value. */
    protected String value;


    /**
     * This method matches the given value with the type and checks if if the match is legal or not.
     * @param value the given value
     * @param typePattern the type pattern
     * @return true if match is legal, false otherwise.
     * @throws ValueDoesNotMatchTypeException is thrown if the value does not match the type.
     */
    public boolean matchValueWithTypeIncludePattern(String value, Pattern typePattern)
            throws ValueDoesNotMatchTypeException{
        if (value == null) return true;
        Matcher matcher = typePattern.matcher(value);
        if (matcher.matches()){
            return true;
        }else{
            throw new ValueDoesNotMatchTypeException();
        }
    }

    /**
     * A getter to the name of type (string)
     * @return the name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * A getter to the value of type (string).
     * @return the value.
     */
    public String getValue(){
        return this.value;
    }

    /**
     * An abstract method used to set the values given to the different variables.
     * @param value the value
     * @throws ValueDoesNotMatchTypeException
     */
    public abstract void setValue(String value) throws ValueDoesNotMatchTypeException;


}


