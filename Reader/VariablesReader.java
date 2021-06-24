package oop.ex6.main.Reader;

/** The variable reader interface. */
public interface VariablesReader {

    /* This constant represents the symbol of equals */
     String EQUAL = "=";

     /** The function that cleans the given string by wanted parameters. */
    static String[] cleanNameAndValue(String line){
        int indexOfEquals;
        String untilAssignment;
        String value;
        if (line.contains(EQUAL)) {
            indexOfEquals = line.indexOf(EQUAL);
            untilAssignment = cleanString(line.substring(0, indexOfEquals));
            value = cleanString(line.substring(indexOfEquals + 1));
        }
        else {
            untilAssignment = cleanString(line);
            value = null;
        }
        String [] nameAndValue = {untilAssignment, value};
        return nameAndValue;
    }

    /* This method cleans string from whitespaces and semi-colons
     * @param toClean - the string to clean
     * @return the cleaner string
     */
    static String cleanString(String toClean){
        toClean = toClean.replaceAll("\\s", "");
        toClean = toClean.replaceAll(";","");
        return toClean;
    }

    /* This method cuts a string of one line by a given sub string of the line
     * @param toCutFrom - the substring to cut from
     * @param line - the line to cut
     * @return the relevant string after cutting
     */
    static String cutString(String toCutFrom, String line, int index) {
        return line.substring(index);
    }

}
