package com.example.stagemanager;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.stagemanager.stageCrew.StageCrewMainActivity;

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

    StageCrewMainActivity parent;
    JSONObject jsonObject;

    public JsonUrlReader(Activity parent) {
        this.parent = (StageCrewMainActivity)parent;
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
        InputStream is = new URL("https://script.googleusercontent.com/macros/echo?user_content_key=RadRWdjhph75IaSTmhszHqDH-0lThODJvuIVa7zyHXRfNgv6Cvj-mNVI9JX_tJIwNUSvYvm_A1w5Rn0b4RycZCiKVHpB-WbDm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnCmcA004SKi3nBZ46TraySlXx2mS44wT3wWVRVDTTBc_o-nazYKrUb0p8dd_9Lj67vDd_qw96iat&lib=MsYnNGBYgQb8m0AIWczyd_Va-ZRIQ7o7_").openStream();
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
