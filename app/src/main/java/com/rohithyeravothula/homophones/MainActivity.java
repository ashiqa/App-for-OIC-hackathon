package com.rohithyeravothula.homophones;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import datamuse.GetWordsAPI;
import datamuse.ResponseWord;
import datamuse.Words;

public class MainActivity extends AppCompatActivity {

    private static Words wordsApi = new Words();


    private TextToSpeech tts;


    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    tts.setPitch(1.2f);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        System.out.printf("TTS", "This Language is not supported");
                    }
                    speak("Welcome to sounds application");

                } else {
                    System.out.printf("TTS", "Initilization Failed!");
                }
            }
        });
    }

    public void speakResponse(String word, List<String> response){
        String template;
        if (response.size() > 0) {
            template = String.format("%s %s, lot similat to %s", word, word, response.get(0));
        }
        else{
            template = String.format("%s %s", word, word);
        }
        speak(template);
    }

    public void onSearchButtonClick(View view) throws IOException, ExecutionException, InterruptedException {
        TextView textview = (TextView) findViewById(R.id.editText);
        String queryWord = textview.getText().toString();
        String museApi= wordsApi.getHomophonesQuery(textview.getText().toString());
        List<ResponseWord> resp_words = new GetWordsAPI().execute(museApi).get();
        List<String> words = new ArrayList<>();
        for(ResponseWord rw: resp_words){
            words.add(rw.word);
        }
        TextView responseTextView = (TextView) findViewById(R.id.textView);
        responseTextView.setText(toResponseString(words));
        speakResponse(queryWord, words);
    }

    public String toResponseString(List<String> response){
        return toResponseString(response, "\n");
    }

    public String toResponseString(List<String> response, String delimiter){
        StringBuilder responseString = new StringBuilder();
        for(String s: response){
            responseString.append(s);
            responseString.append(delimiter);
        }
        if(responseString.length() > 1)
        responseString.setLength(responseString.length() - 1);

        return responseString.toString();
    }

    @Override
    protected void onDestroy() {
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
