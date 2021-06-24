package oop.ex6.main.Varaibles;
import oop.ex6.main.Types.Type;
import java.util.HashMap;

/** This class, Variable, represents a variable with all of it characteristic.
 * @author Keren Ben Arie and Dana Adam
 */
public class Variable {

    /* The type name (string) */
    private final String typeName;

    /* a hashmap of variables of the type*/
    private HashMap<String, String> varsOfType;

    /* a boolean variable if global.*/
    private final boolean isGlobal;

    /* a boolean variable if local.*/
    private final boolean isMethod;

    /* a boolean variable if final.*/
    private final boolean isFinal;

    /* the type object */
    private Type type;

    /** First constructor - before validity checks for type object insertion
     * @param typeName The type name
     * @param varsOfType the variables of this type
     * @param isGlobal boolean variable if global
     * @param isMethod boolean variable if local
     * @param isFinal boolean variable if final
     */
    public Variable(String typeName, HashMap<String, String> varsOfType,
                    boolean isGlobal, boolean isMethod, boolean isFinal){
        this.typeName = typeName;
        this.varsOfType = varsOfType;
        this.isGlobal = isGlobal;
        this.isMethod = isMethod;
        this.isFinal = isFinal;
    }

    /** Second Constructor - after validity checks, a type is now inserted
     * @param type The type of the variable.
     * @param isGlobal boolean variable if global
     * @param isMethod boolean variable if local
     * @param isFinal boolean variable if final
     */
    public Variable(Type type, boolean isGlobal, boolean isMethod, boolean isFinal,String typeName){
        this.type = type;
        this.typeName = typeName;
        this.isGlobal = isGlobal;
        this.isMethod = isMethod;
        this.isFinal = isFinal;
    }


    /**
     *  a getter to the type name
     * @return the type name
     */
    public String getTypeName(){
        return this.typeName;
    }

    /**
     * a getter to the type
     * @return the type of variable
     */
    public Type getType(){
        return this.type;
    }


    /**
     * a getter to the variables of this type
     * @return a hashmap of the variables
     */
    public HashMap<String, String> getVarsOfType(){
        return this.varsOfType;
    }

    /**
     * a getter to the boolean variable if local
     * @return true if local, false otherwise
     */
    public boolean getIsMethod() {
        return this.isMethod;
    }

    /**
     * a getter to the boolean variable if final
     * @return true if final, false otherwise
     */
    public boolean getIsFinal() {
        return this.isFinal;
    }

    /**
     * a getter to the boolean variable if global
     * @return true if global, false otherwise
     */
    public boolean getIsGlobal() {
        return this.isGlobal;
    }

    /**
     * a getter to the value of the variable
     * @return the string value
     */
    public String getValue() {
        return this.type.getValue();
    }
}
