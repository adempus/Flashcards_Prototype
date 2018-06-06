package components;
import  components.Question.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Utility class for creating Flashcard objects. Flashcard objs are composed of multiple other classes. The FlashcardBuilder 
class handles the tedium of constructing Flashcards of different question types. */

public class FlashcardBuilder
{
    private FlashcardBuilder() { }
    
    /** This static method returns a Flashcard of a true or false type question. 
    
        @param subject - the subject of a flashcard object. 
        @param statement - true or false questions are statements rather than actual questions. 
        @param correctAns - a boolean variable representing either a true or false answer to the particular question. 
        
        @return a Flashcard object of a True or False Question type.
    */
    public static Flashcard buildTrueOrFalseCard(String subject, String statement, boolean correctAns)
    {
        Answer[] options = { new Answer<>(true),
                             new Answer<>(false)
        };
        Question<Boolean> trueOrFalseQuestion = new TrueOrFalse(
                statement, Arrays.asList(options), new Answer<>(correctAns));
        return new Flashcard(subject, trueOrFalseQuestion);
    }
    
    
    /** This static method constructs and returns a Flashcard object of Multiple Choice questions. 
        
        @param subject  - the subject (topic) of the question specified.
        @param question - the multiple choice question to answer.
        @param choices  - a List of String objects representing the individual answer choices the user has to choose from.
        @param correctAnswer - a single answer of type String designated to this particular Question. A multiple choice 
                               correctAnswer String must match one of the answer Strings included in the List. 
                               
        @return - a Flashcard object of Multiple Choice question type.
    */
    public static Flashcard buildMultipleChoiceCard(String subject, String question,
                                                    List<String> choices, String correctAnswer)
    {
        List<Answer<String>> ansChoices = new ArrayList<>();
        Answer<String> correctAns = new Answer<>(correctAnswer);
        choices.forEach(choice -> ansChoices.add(new Answer<>(choice)));
        Question<String> multiChoiceQuestion = new MultipleChoice(question, ansChoices, correctAns);
        return new Flashcard(subject, multiChoiceQuestion);
    }

    /** This static method constructs and returns a Flashcard object of Multiple Select type question.
        
        @param subject  - the subject (topic) of the question specified.
        @param question - the multiple select question to answer.
        @param selections - a List of String objects representing the individual answer selections the user has to choose from.
        @param correctAnswers - a list of designated answer selections designated to this particular Question. Multiple selection
                            correctAnswer Strings must be included in the original list of selections provided.
        
        @return - a Flashcard object of Multiple Select question type.
    */
    public static Flashcard buildMultipleSelectCard(String subject, String question,
                                                    List<String> selections, List<String> correctAnswers)
    {
        List<Answer<String>> ansSelections = new ArrayList<>(selections.size()),
                             correctAns = new ArrayList<>(correctAnswers.size());
        selections.forEach(
                selection -> ansSelections.add(new Answer<>(selection))
        );
        correctAnswers.forEach(
                answer -> correctAns.add(ansSelections.stream().filter(
                        ans -> ans.getAnswer().equals(answer)).findFirst().get())
        );
        Question<String> multSelectQuestion = new MultipleSelect(
                question, ansSelections, correctAns);
        return new Flashcard(subject, multSelectQuestion);
    }

    /**  This static method constructs and returns a Flashcard object of fill in the blank question types.
    
        @param subject - the subject (topic) of the question specified.
        @param question - the fill in the blank question to answer.
        @param hiddenAnswers - a List of String objects representing the answer(s) of this particular quesiton. 
            These answers are initially hidden from the user's view.
            
        @return - a Flashcard object of Fill in the Blank question types; returns null if no answers are provided 
                in the list of answers.
    */
    public static Flashcard buildFillInBlankCard(String subject, String question, List<String> hiddenAnswers) {
        if (!answersArePresent(question, hiddenAnswers)) return null;

        List<Answer<String>> answers = new ArrayList<>(hiddenAnswers.size());
        hiddenAnswers.forEach(ans -> answers.add(new Answer<>(ans.toLowerCase())));
        Question<String> fillInBlankQuestion = new FillInBlank(question, answers);
        return new Flashcard(subject, fillInBlankQuestion);
    }

    /** Checks to see if the List of answers provided for a FillInBlank question type Flashcard isn't empty.
        @return - true if the list of answers are contained in the list of answers to be hidden for question; false otherwise.
    */
    private static boolean answersArePresent(String question, List<String> hiddenAnswers) {
        for (String ans : hiddenAnswers) {
            if (!question.contains(ans)) return false;
        }
        return true;
    }
}
