package systems;

import java.util.*;
import java.util.stream.Collectors;

import components.Flashcard;


public class LeitnerSystem {
    private List<Session> sessionList;
    private int numSessions = 3;

    /** Creates 3 sessions by default.
     * @param flashcards - the flashcards to be processed in repetition sessions. */
    public LeitnerSystem(List<Flashcard> flashcards) {
        this(3, flashcards);
    }

    /** @param numSessions - the number of study sessions for each flashcard. */
    public LeitnerSystem(int numSessions, List<Flashcard> flashcards) {
        this.sessionList = new ArrayList<>(numSessions);
        this.numSessions = numSessions;

        for (int i = 0 ; i < numSessions ; i++)
            sessionList.add(new Session());

        initSessions(flashcards);
    }

    private void initSessions(List<Flashcard> flashcards) {
        int sessionNo = 0;
        for (Flashcard card : flashcards) {
            this.sessionList.get(sessionNo).addCard(card);
            sessionNo = (sessionNo >= numSessions ? 0 : sessionNo+1);
        }
    }

    /** Starts repetition sessions for all initialized flashcards. */
    public void start() { }

    public class Session
    {
        private Stack<Flashcard> cardStack;

        public Session(Flashcard ... flashcards) {
            cardStack = new Stack<>();
            for (Flashcard card : flashcards)
                addCard(card);
        }

        /** @param flashcard - a flashcard to add inside of this session box. */
        public void addCard(Flashcard flashcard) {
            cardStack.push(flashcard);
            shuffleCards();         // randomize.
        }

        /** @return the next card in this session box. */
        public Flashcard getNextCard() {
            return cardStack.pop();
        }

        /** @param cards - specified cards to remove, if included in this session. */
        public void removeCards(List<Flashcard> cards) {
            cardStack.removeAll(cards);
        }

        /** @return a list of flashcards in this session that have been correctly answered. */
        public List<Flashcard> getAllCorrect() {
            return getCards(true);
        }

        /** @return a list of flashcards in this session that have been incorrectly answered. */
        public List<Flashcard> getAllIncorrect() {
            return getCards(false);
        }

        /** @return a list of flashcards that are all either correct (true), or incorrectly (false) answered. */
        private List<Flashcard> getCards(boolean flag) {
            return cardStack.stream()
                    .filter(card -> card.isCorrectAns() == flag)
                    .collect(Collectors.toList());
        }

        /** Shuffles all the flashcards in this session box. */
        public void shuffleCards() {
            Collections.shuffle(cardStack);
        }
    }
}
