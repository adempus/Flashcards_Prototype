package components;

import java.util.ArrayList;
import java.util.List;

public class TrueOrFalse extends Question {
    private final List<Answer<Boolean>> correctAns;

    public TrueOrFalse(String question, List<Answer<Boolean>> options, Answer<Boolean> correctAns) {
        super(Question.Type.TRUE_OR_FALSE, question, options);
        this.correctAns = new ArrayList<>(1);
        this.correctAns.add(correctAns);
    }

    @Override
    public List<Answer<Boolean>> getCorrectAnswer() {
        return this.correctAns;
    }

    @Override
    public void setCorrectAnswer(List correctAns) {
        if (correctAns == null) return;
        Answer<Boolean> ans = (Answer<Boolean>) correctAns.get(0);
        this.correctAns.add(0, ans != null ? ans : this.correctAns.get(0));
    }

    @Override
    public boolean confirmAnswer(String key) {
        Answer<Boolean> answer = correctAns.get(0);
        key = Character.toString(key.charAt(0));
        switch (key) {
            case "T" : return answer.equals(new Answer<>(true));
            case "F" : return answer.equals(new Answer<>(false));
            default  : return false;
        }
    }
}
