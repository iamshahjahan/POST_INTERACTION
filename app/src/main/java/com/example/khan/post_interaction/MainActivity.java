package com.example.khan.post_interaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "shahjahan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG,"Calling read from web.");

        new ReadFromWeb().execute();


    }

    public class ReadFromWeb extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "Inside do in background");


            // Create data variable for sent values to server

            String data = null;
            try {
                data = URLEncoder.encode("studentId", "UTF-8")
                        + "=" + URLEncoder.encode("20126227", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                        + URLEncoder.encode("ahmsjahan@gmail.com", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            String text = "";
            BufferedReader reader = null;

            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL("http://52.27.78.220:8000/api/signup/");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
//                                conn.setRE("POST");

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                Log.d(TAG,"SEnd" + data);
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();

            } catch (Exception ex) {
                Log.d(TAG,"Unable to communicate.");
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }

            // Show response on activity




//            try {

//                Log.d(TAG,"Here is new url.");
//                URL url = new URL("http://192.168.1.33/post/home.php");
//
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setDoInput(true);
//                urlConnection.setDoOutput(true);
////                urlConnection.setRequestProperty("Content-Type", "application/json");
//
//
////sending data in json format
//
////                JSONObject jsonObject = new JSONObject();
////
////                jsonObject.put("name","salman Khan");
////
////                jsonObject.put("password","khankhan");
////
////                String sendingString ;
////
////                sendingString = jsonObject.toString();
////
////                byte[] outputBytes = sendingString.getBytes("UTF-8");
//                String data = URLEncoder.encode("name", "UTF-8")
//                        + "=" + URLEncoder.encode("shahjahan", "UTF-8");
//
//                data += "&" + URLEncoder.encode("password", "UTF-8") + "="
//                        + URLEncoder.encode("khan", "UTF-8");
//
//
//                Log.d(TAG, "Output bytes are " + data);
//
//                OutputStream outputStream = urlConnection.getOutputStream();
//                OutputStreamWriter wr = new OutputStreamWriter(outputStream);
//                Log.d(TAG," got output stream.");
//                wr.write(data);
//
//                Log.d(TAG,"I am waiting for response code");
//                int responseCode = urlConnection.getResponseCode();
//
//                Log.d(TAG,"response code: " + responseCode);
//
//                if ( responseCode == HttpURLConnection.HTTP_OK)
//                {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//
//                    String line;
//
//                    Log.d(TAG,"Before reading the line.");
//
//                    while ( (line = bufferedReader.readLine()) != null )
//                    {
//                        Log.d(TAG, " The line read is: " + line);
//                    }
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            Log.d(TAG,"read : " + text);

            return null;
        }
    }

}
