package oop.ex6.main.Scope;
import oop.ex6.main.Methods.Method;
import oop.ex6.main.Varaibles.Variable;

import java.util.ArrayList;

/** This class, Scope, represents a scope (global, method, condition) in the code.
 * @author Dana Adam and Keren Ben Arie
 */
public class Scope {

    private final int level; // 0 - global
    private boolean isMethod = false;
    private final ArrayList<Variable> variables;
    private Method method = null;
    private final Scope parentScope;
    private final ArrayList<Variable> upperScopeVariables = new ArrayList<>();


    /**
     * This is the constructor to the scope object
     * @param level The level (depth of nesting) of the scope
     * @param variables the arrayList of variables related to the scope
     * @param method the current method related to the scope
     * @param parentScope The parent scope of the current scope
     */
    public Scope(int level, ArrayList<Variable> variables, Method method, Scope parentScope){
        if (method != null) {
            this.isMethod = true;
            this.method = method;
        }
        this.parentScope = parentScope;
        this.level = level;
        this.variables = variables;
        if (this.parentScope != null) {
            this.updateParentScopeVariables();
            updateUpper();


        }
    }

    public void updateUpper(){
        this.upperScopeVariables.addAll(this.parentScope.getVariables());

    }
    /**
     * A getter to the method
     * @return the current method of the scope
     */
    public Method getMethod() {
        return method;
    }

    /**
     * A getter to the parent scope
     * @return the parent scope of the current scope
     */
    public Scope getParentScope() {
        return parentScope;
    }

    /**
     * The boolean variable isMethod checks if the current scope is a method.
     * @return true or false.
     */
    public boolean isMethod() {
        return isMethod;
    }

    /**
     * A getter to the level of current scope.
     * @return the level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * This method adds the variables to the current scope
     * @param newVariables the variables given
     */
    public void addToScope(ArrayList<Variable> newVariables){
        this.variables.addAll(newVariables);
    }

    /**
     * This method is a getter to the upper (parent) scope variables- to see what is reachable.
     * @return arrayList of those variables.
     */
    public ArrayList<Variable> getUpperScopeVariables() {
        return upperScopeVariables;
    }

    /**
     * A getter to the upper scope variables names.
     * @return An arrayList of the names.
     */
    public ArrayList<String> getUpperScopeVariablesNames(){
        ArrayList<String> names = new ArrayList<>();
        for (Variable var: upperScopeVariables){
            names.add(var.getType().getName());
        }
        return names;
    }

    /**
     * A getter to the variables of the current scope
     * @return an arrayList of variables of the current scope
     */
    public ArrayList<Variable> getVariables(){
        return this.variables;
    }

    /**
     * A getter to this scope's variables names.
     * @return An arrayList of the names.
     */
    public ArrayList<String> getVariablesNames(){
        ArrayList<String> names = new ArrayList<>();
        for (Variable var: variables){
            names.add(var.getType().getName());
        }
        return names;
    }

    /**
     * A method that updates the parents scope variables.
     */
    private void updateParentScopeVariables(){
        Scope parent = this.parentScope.getParentScope();
        while(parent !=null && parent.getVariables()!= null){
            this.upperScopeVariables.addAll(parent.getVariables());
            parent = parent.getParentScope();
        }
    }

}
