package com.example.shalom.hospitalmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jsonDtos.LoginDto;
import jsonDtos.StringDto;

public class MainActivity extends AppCompatActivity {
Button login;
    EditText username,password;
    Intent intent;
    String TAG="Debugg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button)findViewById(R.id.button);
        username=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
new AsyncHttpTask().execute();
            }
        });
    }






    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

 class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

String user=username.getText().toString();
     String pass=password.getText().toString();
    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;

        HttpURLConnection urlConnection = null;

        Integer result = 0;
        try {

                /* forming th java.net.URL object */
            URL url = new URL(params[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
            urlConnection.setRequestProperty("Accept", "application/json");

            LoginDto loginDto=null;

               loginDto=new LoginDto();
                loginDto.setName(user);
           loginDto.setPassword(pass);


            String s=    new Gson().toJson(loginDto);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
           /* HashMap<String, String> postDataParams=new HashMap<>();
            postDataParams.put("name","brian");
            postDataParams.put("age", "21");
            Log.d("tttttttttttt", getPostDataString(postDataParams));*/
            //writer.write(getPostDataString(postDataParams));
            writer.write(s);
            writer.flush();
            writer.close();
            os.close();
            // urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
            if (statusCode ==  200) {

                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                String response = convertInputStreamToString(inputStream);

                parseResult(response);

                result = 1; // Successful

            }else{
                result = 0; //"Failed to fetch data!";
            }

        } catch (Exception e) {
            Log.d(TAG + "hello", e.getMessage() + "..................");
        }

        return result; //"Failed to fetch data!";
    }


    @Override
    protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */

        if(result == 1){

            intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }else{
            Log.e(TAG, "Failed to fetch data!");
        }
    }
}


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }

        return result;
    }
    private void parseResult(String result) {
        /* JSONObject response = new JSONObject(result);

            JSONArray posts = response.optJSONArray("posts");*/

        Type type = new TypeToken<List<StringDto>>(){}.getType();
        StringDto in = new Gson().fromJson(result, type);

        Log.d(TAG,in.getMessage());

    }
}
