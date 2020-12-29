package datamuse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ResponseWord {
    public String word;
    public Integer score;
    public ResponseWord(){}

    public ResponseWord(String word, Integer score){
        this.word = word;
        this.score = score;
    }

    public ResponseWord(String word, String score){
        this.word = word;
        this.score = Integer.parseInt(score);
    }

    @Override
    public String toString() {
        return "word: " + this.word + ", score: " + this.score.toString();
    }
}
