package com.example.stagemanager.urlReader;

import android.os.AsyncTask;

import com.example.stagemanager.LineupInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class AsyncUrlReader extends AsyncTask<String, Void, String> {

    LineupInfoActivity parent;

    JSONObject jsonObject;

    public AsyncUrlReader(LineupInfoActivity parent) {
        this.parent = parent;
    }

    @Override
    protected String doInBackground(String... args) { //fr , satam, satpm, sun
            InputStream is = null;
            try {
                is = new URL("https://yapa.art.pl/2019/obsuwa/json1.php?day=" + args[0]).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
               jsonObject = json;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        return null;
    }

    @Override
    protected void onPostExecute(String sb) {

        parent.returnTaskResults(jsonObject);

    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}