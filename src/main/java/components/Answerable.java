package components;
import java.util.List;

/** Interface for standardizing String literal answers provided to question objects, for consistent comparisons when 
    verifying provided answers. 
*/
public interface Answerable {
    <T extends Comparable<T>> List<T> getAnswerOptions();
    boolean confirmAnswer(String answer);
}
