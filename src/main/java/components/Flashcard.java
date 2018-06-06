package components;

import java.util.List;

/** The Flashcard class encapsulates a Question object with a corresponding boolean flag for indicating whether or not the question
    was answered correctly. NOTE: Flashcard objects are a bit complicated to construct, and shouldn't be instantiated directly! 
    Therefore a FlashcardBuilder utility class is provided via the Builder design pattern for conveniently constructing Flashcard
    objects of various question types.
*/

public class Flashcard
{
    private String subject;
    private Question question;
    private boolean isAnswerCorrect = false;
    private ProficiencyLevel proficiencyLevel = ProficiencyLevel.F;

    /** The level at which the user is most competent at correctly answering this question. Questions answered correct
        most of the time results in a higher proficiency level, for this card's question. Incorrectly answered questions
        result in a lower level of proficiency.
     */
    public enum ProficiencyLevel { A, B, C, D, F }

    /** @param subject - a String representing the topic of the question. 
        @param question - A question designated to this particular Flashcard object. 
    */
    public Flashcard(String subject, Question question) {
        this.subject = subject;
        this.question = question;
    }

    /** @return true if a question was answered correctly; false otherwise. */
    public boolean isCorrectAns() {
        return isAnswerCorrect;
    }

    /** @return - a List of answer options privided for a particular question type; most notable Multiple Choice and Multiple Select
                  quesiton types.
    */
    public List getAnswerOptions() {
        return question.getAnswerOptions();
    }

    /** Accepts a String(s) as a response to a question, and sets the string as a response to the question to be checked for correctness.
        @param response - a response, or set of responses (for either multiple choice, multiple select, or fill in blank questions.)
    */
    public void setResponse(String ... response) {
        checkResponse(response);
    }

    /** Verifies String answer responses for the question.
        @param response - a String(s) provided as a response to the question, to be verified for correction. 
    */
    protected void checkResponse(String ... response) {
        char charVal = Character.toUpperCase(response[0].charAt(0));
        switch (question.getQuestionType())
        {
            case MULTIPLE_CHOICE : verifyMultiChoiceResponse(charVal);     break;
            case TRUE_OR_FALSE   : verifyTrueOrFalseResponse(charVal);     break;
            case FILL_IN_BLANK   : verifyFillInBlankResponse(response);    break;
            case MULTIPLE_SELECT : verifyMultiSelectResponse(response[0]); break;
            default              :                                         break;
        }
    }

    /** verifies whether or not a multiple choice question was answered correctly.
        @param  choice - a character representing an answer choice from a list of designated answers for a multiple choice question. 
    */    
    private void verifyMultiChoiceResponse(char choice) {
        final int optSize = question.getAnswerOptions().size();
        if ((choice <= 'A'+(optSize-1))) {
          isAnswerCorrect = question.confirmAnswer(Character.toString(choice));
        }
    }

     /** verifies whether or not a multiple select question was answered correctly.
        @param  response - a string representing an answer selection from a list of designated answers for a multiple select question. 
    */
    private void verifyMultiSelectResponse(String response) {
        final int numAns = question.getCorrectAnswer().size();
        if (response.length() < numAns) return;
        response = response.substring(0, numAns);
        isAnswerCorrect = question.confirmAnswer(response);
    }

    /** verifies whether or not a fill in the blank question was answered correctly.
        @param  answers - a string array representing answers for a fill in the blank question. 
    */
    private void verifyFillInBlankResponse(String[] answers) {
        isAnswerCorrect = ((FillInBlank)  question).confirmAnswer(answers);
    }

    /** verifies whether or not a true or false question was answered correctly.
        @param  answer - a character of 'T' or 'F' representing either a true or false answer for a
                true or false question. 
    */
    private void verifyTrueOrFalseResponse(char answer) {
        isAnswerCorrect = question.confirmAnswer(Character.toString(answer));
    }

    /** @return the String literal question assigned to this Flashcard object.
     */
    public Question getQuestion() { return question; }

    /** @return the level of competence the user has in answering this card's question.
     */
    public ProficiencyLevel getProficiencyLvl() { return this.proficiencyLevel; }

    /** @param proficiency - the level at which the user is competent or incompetent with this card's question.
     */
    public void setProficiencyLevel(ProficiencyLevel proficiency) {
        this.proficiencyLevel = proficiency;
    }

    @Override
    public String toString() {
        StringBuilder cardStringBuilder = new StringBuilder();
        String text = "subject: "+subject+
                "\nquestion: "+question+"\n---";
        cardStringBuilder.append(text);
        cardStringBuilder.append(question.getAnswerOptions().toString());
        return cardStringBuilder.toString();
    }
}
