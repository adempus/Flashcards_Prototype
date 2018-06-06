package components;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MultipleChoice extends Question {
    private HashMap<Character, Answer<String>> multChoiceOptions;
    private List<Answer<String>> correctAnswer;

    public MultipleChoice(String question, List<Answer<String>> ansChoices, Answer<String> correctAns) {
        super(Question.Type.MULTIPLE_CHOICE, question, ansChoices);
        this.correctAnswer = new ArrayList<>(1);
        setCorrectAnswer(correctAns);
        initChoices();
    }

    private void setCorrectAnswer(Answer<String> ans) {
        List<Answer> answer = new ArrayList<>(1);
        answer.add(ans);
        setCorrectAnswer(answer);
    }

    private void initChoices() {
        char opt = 'A';
        multChoiceOptions = new HashMap<>(answerOptions.size());
        for (int i = 0; i < answerOptions.size() ; i++, opt++) {
            multChoiceOptions.put(opt, ((Answer) answerOptions.get(i)));
        }
    }

    @Override
    protected void setCorrectAnswer(List answer) {
        this.correctAnswer.addAll(answer);
    }

    @Override
    protected List<Answer<String>> getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean confirmAnswer(String answer) {
        char key = answer.charAt(0);
        if (multChoiceOptions.containsKey(Character.toUpperCase(key))) {
            Answer chosenAns = multChoiceOptions.get(key);
            return correctAnswer.stream().allMatch(ans -> ans.equals(chosenAns));
        }
        return false;
    }
}
