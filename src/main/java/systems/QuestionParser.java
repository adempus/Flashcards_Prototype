package systems;

import javax.json.*;
import javax.json.stream.JsonParser;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import javax.json.stream.JsonParser.Event;
import java.io.StringReader;

/** Generated questions retrieved from: https://opentdb.com/api_config.php */

public class QuestionParser
{
    private String jsonContents;

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
        JsonParser jsonParser = Json.createParser(new StringReader(jsonContents));
        while (jsonParser.hasNext()) {
            final Event event = jsonParser.next();
            switch (event) {
                case KEY_NAME : System.out.println(jsonParser.getString()); break;
                case VALUE_STRING: System.out.println(jsonParser.getString()+"\n") ; break;
                default: break;
            }
        }
        jsonParser.close();
    }

    private void setJsonContents(String contents) {
        this.jsonContents = contents;
    }

    public String getJsonContents() {
        return this.jsonContents;
    }
}
