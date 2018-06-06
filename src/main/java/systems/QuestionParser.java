package systems;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import javax.json.stream.JsonParser.Event;
import java.io.StringReader;
import java.util.stream.Collectors;

import components.Flashcard;
import components.FlashcardBuilder;

/** Generated questions retrieved from: https://opentdb.com/api_config.php */

public class QuestionParser
{
    private String jsonContents;
    private List<Flashcard> cardsList;

    public QuestionParser(String jsonPath) {
        try {
            final String jsonContents = new String(Files.readAllBytes(Paths.get(jsonPath)));
            setJsonContents(jsonContents);
            parseQuestions(jsonContents);
        } catch (IOException IOEx) {
            System.err.println("File path invalid");
            IOEx.printStackTrace();
        }
    }

    // TODO: parse the questions from json string into actual flashcard questions.
    private void parseQuestions(String jsonContents) {
        this.cardsList = new ArrayList<>();
        final JsonParser jsonParser = Json.createParser(new StringReader(jsonContents));
        while (jsonParser.hasNext()) {
            final Event event = jsonParser.next();
            if (event.equals(Event.KEY_NAME)) {
                discernKey(jsonParser.getString(), jsonParser);
            }
        }
        jsonParser.close();
    }

    private List<String> choiceList;
    private String category, question, correctAns;

    private void discernKey(String key, JsonParser parser) {
        final String CATEGORY = "category", QUESTION = "question",
                CORRECT_ANS = "correct_answer", INCORRECT_ANS = "incorrect_answers";

        parser.next();
        switch (key) {
            case CATEGORY: category = parser.getString() ; break;
            case QUESTION: question = parser.getString() ; break;
            case CORRECT_ANS: correctAns = parser.getString() ; break;
            case INCORRECT_ANS:
                choiceList = parser.getArrayStream().map(jsonVal ->
                        jsonVal.toString().replace("\"", "")).collect(Collectors.toList());
                choiceList.add(correctAns);
                Collections.shuffle(choiceList);
                initFlashcard();
                break;
            default : break;
        }
    }

    private void initFlashcard() {
        Flashcard multiChoice = FlashcardBuilder.buildMultipleChoiceCard(
                category, question, choiceList, correctAns
        );
        this.cardsList.add(multiChoice);
    }

    private void setJsonContents(String contents) {
        this.jsonContents = contents;
    }

    public String getJsonContents() {
        return this.jsonContents;
    }

    public List<Flashcard> getFlashcards() {
        return this.cardsList;
    }
}
