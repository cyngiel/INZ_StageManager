package com.example.stagemanager.urlReader;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUrlReader extends AsyncTask<Void, Void, Void> {

    JsonUrlReaderTaskResults parent;
    JSONObject jsonObject;
    String arg;

    public JsonUrlReader(JsonUrlReaderTaskResults parent, String arg) {
        this.parent = parent;
        this.arg = arg;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl() throws IOException, JSONException {
        InputStream is = new URL("https://script.google.com/macros/s/AKfycbwiUmDen8O-bn8qAxSDDdrG2KLM6OAjBG9qPuQvQhGXtY2pyaS1/exec?theArg=" + arg).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public JSONObject getJson() {
        return jsonObject;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            jsonObject = readJsonFromUrl();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Log.i("json", jsonObject.toString());
        parent.returnTaskResult(jsonObject);
    }
}
