import components.*;
import systems.LeitnerSystem;
import systems.QuestionParser;
import java.util.ArrayList;
import java.util.List;

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
        for (Flashcard fc : cardList) {
            System.out.println(fc);
        }
    }

    public static void createCards() {
        // QuestionParser multiChoiceParser = new QuestionParser();
    }
}


