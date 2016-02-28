package com.example.khan.post_interaction;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
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
    SharedPreferences sharedPreferencesToken;
    public String token = null;

    String studentIdString;
    String emailString;
    String firstNameString;
    String lastNameString;
    String middleNameString;
    String motherNameString;
    String fatherNameString;
    String mobileNoString;

    EditText studentId;
    EditText email;
    EditText firstName;
    EditText lastName;
    EditText middleName;
    EditText fatherName;
    EditText motherName;
    EditText mobileNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferencesToken = this.getSharedPreferences("TOKEN",MODE_PRIVATE);
        studentId = (EditText) findViewById(R.id.studentId);
        email = (EditText) findViewById(R.id.email);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        middleName = (EditText) findViewById(R.id.middle_name);
        fatherName = (EditText) findViewById(R.id.father_name);
        motherName = (EditText) findViewById(R.id.mother_name);
        mobileNo = (EditText) findViewById(R.id.mobile_number);

        token = sharedPreferencesToken.getString("token",null);
        Log.d(TAG,"Token from sharedprefrences is: " + token);

        Log.d(TAG, "Calling read from web.");

    }

    public void signup(View view) {
        token = sharedPreferencesToken.getString("token",null);

        Log.d(TAG, "Signing in");
        studentIdString = studentId.getText().toString();
        emailString = email.getText().toString();
        if (token == null)
        {
            new ReadFromWeb().execute("signup", studentIdString, emailString, "http://52.27.78.220:8000/api/signup/");
        }
        else
        {
            Log.d(TAG,"You have already logged in using another account.");
        }
    }

    public void get(View view) {
        token = sharedPreferencesToken.getString("token",null);

        studentIdString = studentId.getText().toString();
        emailString = email.getText().toString();
        Log.d(TAG, "Signing in");
        new ReadFromWeb().execute("get", studentIdString, emailString, "http://52.27.78.220:8000/api/user/get/ ");
    }

    public void update(View view) {
        token = sharedPreferencesToken.getString("token",null);
        Toast.makeText(getApplicationContext(),"Updating data.",Toast.LENGTH_LONG).show();

        studentIdString = studentId.getText().toString();
        emailString = email.getText().toString();
        firstNameString = firstName.getText().toString();
        middleNameString = middleName.getText().toString();
        lastNameString = lastName.getText().toString();
        motherNameString = motherName.getText().toString();
        fatherNameString = fatherName.getText().toString();
        mobileNoString = mobileNo.getText().toString();
        Log.d(TAG, "Signing in");
        new ReadFromWeb().execute("update", studentIdString, emailString,
                "http://52.27.78.220:8000/api/user/update/",
                firstNameString, middleNameString,
                lastNameString, motherNameString
                , fatherNameString,mobileNoString

        );
    }

    public class ReadFromWeb extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            String data = "";
            URLConnection conn = null;
            try {
                url = new URL(params[3]);

                conn = url.openConnection();
                conn.setDoOutput(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Send POST data request


            Log.d(TAG, "Inside do in background");
            String text = "";

            if (params[0].equals("signup")) {
                Log.i(TAG, "signup : " + params[1] + params[2]);
                data = "";
                try {
                    data = URLEncoder.encode("studentId", "UTF-8")
                            + "=" + URLEncoder.encode(params[1], "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                            + URLEncoder.encode(params[2], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                BufferedReader reader = null;

                // Send data
                try {

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    Log.d(TAG, "SEnd" + data);
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
                    Log.d(TAG, "Unable to communicate.");
                } finally {
                    try {
                        reader.close();
                    } catch (Exception ex) {
                    }
                }
                Log.d(TAG, "read : " + text);

            } else if (params[0].equals("get")) {
                Log.i(TAG, "get : " + params[1] + params[2]);

//                conn.setRequestProperty("Content-Type", "application/json");
                Log.d(TAG, "Token is: " + token);

                conn.setRequestProperty("Authorization", "token "
                        + token);
//                conn.setRequestMethod("GET");


                BufferedReader reader = null;

                // Send data
                try {

                    // Defined URL  where to send data


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
                    Log.d(TAG, "Unable to communicate.");
                } finally {
                    try {
                        reader.close();
                    } catch (Exception ex) {
                    }
                }


            } else {
                Log.i(TAG, "update : " + params[1] + params[2]);

                Log.d(TAG, "Token is: " + token);

                conn.setRequestProperty("Authorization", "token "
                        + token);

                try {
                    data = URLEncoder.encode("studentId", "UTF-8")
                            + "=" + URLEncoder.encode(params[1], "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                            + URLEncoder.encode(params[2], "UTF-8");
                    data += "&" +URLEncoder.encode("first_name", "UTF-8")
                            + "=" + URLEncoder.encode(params[4], "UTF-8");
                    data += "&" + URLEncoder.encode("middle_name", "UTF-8") + "="
                            + URLEncoder.encode(params[5], "UTF-8");
                    data += "&" + URLEncoder.encode("last_name", "UTF-8")
                            + "=" + URLEncoder.encode(params[6], "UTF-8");
                    data += "&" + URLEncoder.encode("mother_name", "UTF-8") + "="
                            + URLEncoder.encode(params[7], "UTF-8");
                    data += "&" + URLEncoder.encode("father_name", "UTF-8") + "="
                            + URLEncoder.encode(params[8], "UTF-8");
                    data += "&" + URLEncoder.encode("mobile_no", "UTF-8") + "="
                            + URLEncoder.encode(params[9], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                BufferedReader reader = null;

                // Send data
                try {

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    Log.d(TAG, "SEnd" + data);
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
                    Log.d(TAG, "Unable to communicate.");
                } finally {
                    try {
                        reader.close();
                    } catch (Exception ex) {
                    }
                }
                Log.d(TAG, "read : " + text);


            }

//            // Create data variable for sent values to server
            Log.d(TAG, "the returned text is: " + text);
            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject tokenSignup = new JSONObject(s);

                int status = tokenSignup.getInt("status");

                if (token != null) {
                    boolean is_token_error = tokenSignup.getBoolean("is_token_error");

                    if (!is_token_error) {
                        JSONArray dataJson = tokenSignup.getJSONArray("data");
                        JSONObject dataObject = dataJson.getJSONObject(0);

                        Toast.makeText(getApplicationContext(),

                                "Last_name: " + dataObject.getString("last_name") + " " +
                                        "first_name: " + dataObject.getString("first_name") + " " +
                                        "studentId: " + dataObject.getString("studentId") + " " +
                                        "middle_name: " + dataObject.getString("middle_name") + " " +
                                        "email: " + dataObject.getString("email") + " "

                                , Toast.LENGTH_LONG
                        ).show();

                         studentId.setText(dataObject.getString("studentId"));
                         email.setText(dataObject.getString("email") );
                         firstName.setText(dataObject.getString("first_name") );
                         lastName.setText(dataObject.getString("last_name") );
                         middleName.setText(dataObject.getString("middle_name") );
                         fatherName.setText(dataObject.getString("father_name") );
                         motherName.setText(dataObject.getString("mother_name") );
                         mobileNo.setText(dataObject.getString("mobile_no") );
                        Toast.makeText(getApplicationContext(),"Loaded data.",Toast.LENGTH_LONG).show();



                    } else {
                        Log.d(TAG, "Token error: " + token);
                    }
                } else {
                    if (status == 200) {
                        Toast.makeText(getApplicationContext(),"successfully signed up",Toast.LENGTH_LONG).show();

                                token = tokenSignup.getString("token");
                        sharedPreferencesToken.edit().putString("token",token).apply();
                        Log.d(TAG, "Token is: " + token);
                    } else {
                        Log.d(TAG, "User is already logged in.");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
