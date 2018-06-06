package components;

import java.util.*;

/** An abstract class representing various types of questions to answer. This class is implemented with generics of Comparable types,
    for comparing answers that have a natural ordering (Strings, ints, floats, ect.) This makes it so that Answers of varying question
    types can be easily and consistently compared with minimal margin for error. 
*/
public abstract class Question<T extends Comparable<T>> implements Answerable
{
    /* Enum identifying available question types. */
    public enum Type {
        MULTIPLE_CHOICE, MULTIPLE_SELECT, TRUE_OR_FALSE, FILL_IN_BLANK
    }

    protected Type questionType;
    protected List<Answer> answerOptions;
    protected String question;

    /** @param questionType - the type of question specified by the Enum of question types.
        @param question     - a String literal of type question to be displayed. 
    */
    public Question(Type questionType, String question) {
        this.questionType = questionType;
        this.question = question;
    }

    /** @param questionType  - the type of question specified by the Enum of question types.
        @param question      - a String literal of type question to be displayed. 
        @param answerOptions - a list of Answerable objects designated to this particular quesiton. 
    */
    public Question(Type questionType, String question, List<Answer<T>> answerOptions) {
        this(questionType, question);
        this.answerOptions = new ArrayList<>();
        this.answerOptions.addAll(answerOptions);
    }

    /** This static inner class can be used in conjunction with creation of Flashcard objects (done by the FlashcardBuilder utility class)
        for further specifying answerable comparable generic types. 
    */
    public static class Answer<T extends Comparable<T>> {
        private T answer;
        public Answer(T answerOption) { this.answer = answerOption; }
        public T getAnswer() { return this.answer; }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Answer)) return false;
            return answer.compareTo(((Answer<T>) o).getAnswer()) == 0;
        }
        @Override
        public int hashCode() {
            return (404+answer.toString().hashCode()/8);
        }
        @Override
        public String toString() {
            return answer.toString();
        }
    }

    /** @return a List of type Answerable options assigned to this particular quesiton.  */
    public List<Answer> getAnswerOptions() {
        return this.answerOptions;
    }

    protected abstract <T extends Comparable<T>> void setCorrectAnswer(List<Answer<T>> answer);
    protected abstract List<Answer<T>> getCorrectAnswer();

    /** @return a String literal of this particular question. */
    public String getQuestion() {
        return this.question;
    }
    
    /** @return this particular type of question as specified by the encapsulated Enum.*/
    public Type getQuestionType() {
        return this.questionType;
    }

    @Override
    public String toString() {
        return question;
    }
}
