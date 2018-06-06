package components;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MultipleSelect extends Question
{
    private Map<Character, Answer<String>> multSelectOptions;
    private List<Answer<String>> correctAnswers;

    public MultipleSelect(String question, List<Answer<String>> selections,
                          List<Answer<String>> answers)
    {
        super(Question.Type.MULTIPLE_SELECT, question, selections);
        this.correctAnswers = new ArrayList<>(answers.size());
        setCorrectAnswer(answers);
        initSelections();
    }

    private void initSelections() {
        char opt = 'a';
        multSelectOptions = new HashMap<>(answerOptions.size());
        for (int i = 0; i < answerOptions.size() ; i++, opt++) {
            multSelectOptions.put(opt, ((Answer) answerOptions.get(i)));
        }
    }

    @Override
    public List<Answer<String>> getCorrectAnswer() {
        return this.correctAnswers;
    }

    @Override
    public void setCorrectAnswer(List correctAns) {
        this.correctAnswers.clear();
        this.correctAnswers.addAll(correctAns);
    }

    @Override
    public boolean confirmAnswer(String answer) {
        int numAnswers = correctAnswers.size();
        List<Answer<String>> selectedAns = new ArrayList<>(numAnswers);
        for (int i = 0 ; i < numAnswers ; i++) {
            char key = Character.toLowerCase(answer.charAt(i));
            if (multSelectOptions.containsKey(key))
                selectedAns.add(multSelectOptions.get(key));
        }
        return selectedAns.containsAll(correctAnswers);
    }
}