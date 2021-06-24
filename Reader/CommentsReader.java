package oop.ex6.main.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class is the comments- reader class. */
public class CommentsReader {

    /** The pattern of valid comment line */
    private static final Pattern VALID_COMMENT_LINE = Pattern.compile("^//");


    /**
     * This method is called when a comment line is recognised in the main reader.
     * @param line The line in which the comment line was found.
     * @throws InValidCommentLineException if two lines '//' were recognised but not in the beginning of the
     * line, the comment line is invalid and thus an exception is thrown.
     */
    public static void readCommentLine(String line) throws InValidCommentLineException {
        Matcher validComment = VALID_COMMENT_LINE.matcher(line);
        if (!validComment.find()) {
            throw new InValidCommentLineException();
        }
    }
}
