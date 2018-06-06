package components;

import java.util.ArrayList;
import java.util.List;

/** A Question type of Fill in the Blank for answering questions requiring a full String literal response form the user. */

public class FillInBlank extends Question {
    private List<Answer<String>> correctAns;
    private List<String> answerOptions;
    private String statement;

    /** @param statement - a question or statement of type String.
        @param correctAns - a List of Answerable objects representing a correct answer(s) for this particular quesiton. 
    */
   public FillInBlank(String statement, List<Answer<String>> correctAns) {
        super(Question.Type.FILL_IN_BLANK, statement);
        this.statement = statement;
        this.correctAns = new ArrayList<>(correctAns.size());
        this.answerOptions = new ArrayList<>(correctAns.size());
        setCorrectAnswer(correctAns);
        hideCorrectAnswer();
    }

    /** Utility method for hiding the correct answers from the user's view. */
    private void hideCorrectAnswer() {
       this.correctAns.forEach(answer -> {
            String hiddenAns = answer.getAnswer();
            question = question.replaceFirst(
                    hiddenAns, hiddenAns.replaceAll("\\w", "_"));
        });
    }

    /** @return a List of String literals representing various answer options for the user to choose from. */
    public List<String> getAnswerOptions() {
        return this.answerOptions;
    }
    
    @Override
    protected void setCorrectAnswer(List answer) {
        this.correctAns.addAll(answer);
    }

    @Override
    protected List<Answer<String>> getCorrectAnswer() {
        return this.correctAns;
    }

    @Override
    public boolean confirmAnswer(String answer) {
        confirmAnswer(answer);
        return false;
    }

    @Override
    public String getQuestion() {
       return this.statement;
    }

    public boolean confirmAnswer(String ... answers) {
        if (answers.length > correctAns.size() || answers.length < correctAns.size())
            return false;

        for (int i = 0 ; i < correctAns.size() ; i++) {
            Answer<String> ans = new Answer<>(answers[i].toLowerCase());
            if (!correctAns.get(i).equals(ans)) return false;
        }
        return true;
    }
}
