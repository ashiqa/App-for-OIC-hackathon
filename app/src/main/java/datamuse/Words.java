package datamuse;


import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Words {

    private String api = "https://api.datamuse.com/words";

    public Words(){

    }

    private ArrayList<ResponseWord> getWords(String query) throws IOException {
        URL oracle = new URL(query);
        URLConnection yc = oracle.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        return strToRespWordsList(br.readLine());
    }

    public static ArrayList<ResponseWord> strToRespWordsList(String json_string) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json_string, new TypeReference<List<ResponseWord>>(){});
    }

    public String getHomophonesQuery(String word) throws IOException {
        return api + "?sl=" + word;
    }
}

/*
public void to_json_string() throws IOException {

    ResponseWord respWord = new ResponseWord("hello", "32");
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.writeValueAsString(respWord));
}

    public void test_single_json() throws IOException {

        String test = "{\"word\":\"elephant\", \"score\":\"32\"}";
        ResponseWord dmword = (ResponseWord)new ObjectMapper().readValue(test, ResponseWord.class);
        System.out.println(dmword);
    }*/

