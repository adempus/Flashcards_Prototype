import components.*;
import systems.LeitnerSystem;
import systems.QuestionParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeitnerTest {
    static List<Flashcard> cardList = new ArrayList<>();

    public static void main(String[] args) {
        //createCards();
        // LeitnerSystem leitnerSys = new LeitnerSystem();
        testQuestionParser();
    }

    public static void testQuestionParser() {
        QuestionParser jsonParser = new QuestionParser(
                "/home/adempus/IdeaProjects/FlashcardsPrototype/src/main/java/res/compSciQuestions.json"
        );
        System.out.println(jsonParser.getJsonContents());
        List<Flashcard> cardList = jsonParser.getFlashcards();
        for (int i = 1 ; i < cardList.size() ; i++) {
            testMultiChoiceCards(i, cardList.get(i-1));
        }
    }

    public static void testMultiChoiceCards(int questionNum, Flashcard card) {
        while (!card.isCorrectAns())
        {
            System.out.println("Question "+questionNum+".");
            System.out.println(card.getQuestion().toString() + "\n");
            printOptions(card);
            String input = new Scanner(System.in).nextLine().toUpperCase();
            card.setResponse(input);
            System.out.println(card.isCorrectAns() ? "Correct!\n" : "Incorrect.\n");
        }
    }

    public static void printOptions(Flashcard card) {
        char key = 'a';
        for (int i = 0 ; i < card.getAnswerOptions().size() ; i++, key++) {
            System.out.println(key+". "+card.getAnswerOptions().get(i));
        }
    }

}


