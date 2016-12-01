package leapfrog_software.bitcoingo.System;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Leapfrog-Software on 2016/11/29.
 */

public class HttpRequestor extends AsyncTask<String, Integer, String> {

    private HttpCallback _callback = null;

    public HttpRequestor(HttpCallback callback) {
        this._callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection conn = null;
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            conn.setRequestMethod(params[1]);

            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (params[2].length() > 0) {
                String paramStr = params[2];
                PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
                printWriter.print(paramStr);
                printWriter.close();
            }

            conn.connect();
            int resp = conn.getResponseCode();
            if ((int)(resp / 100) == 2) {
                InputStream stream = conn.getInputStream();
                return streamToString(stream);
            }else{
                InputStream errorStream = conn.getErrorStream();
                String msg = streamToString(errorStream);
                Log.d("test", msg);
            }
        } catch (IOException e) {

            String msg = e.getMessage();
            Log.d("test", msg);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        this._callback.didReceiveData(string);
    }

    String streamToString(InputStream stream) {

        String result = "";
        try {
            StringBuffer buffer = new StringBuffer();
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufReder = new BufferedReader(reader);
            String line = "";
            while ((line = bufReder.readLine()) != null) {
                if (buffer.length() > 0) {
                    buffer.append("\n");
                }
                buffer.append(line);
            }
            try {
                stream.close();
            } catch (Exception e) {
            }

            result = buffer.toString();

        } catch (Exception e) {
        }

        return result;
    }


    public interface HttpCallback{
        void didReceiveData(String string);
    }
}

