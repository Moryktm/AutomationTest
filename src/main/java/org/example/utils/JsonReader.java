package org.example.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


public class JsonReader {

    public static String getData(String loginData) throws IOException, ParseException {
        JSONParser jsonParser=new JSONParser();
        FileReader fileReader=new FileReader("src/main/resources/testData/TestData.json");
        Object object=jsonParser.parse(fileReader);
        JSONObject correctLogin=(JSONObject)object;
        return (String)correctLogin.get(loginData);

    }

}
