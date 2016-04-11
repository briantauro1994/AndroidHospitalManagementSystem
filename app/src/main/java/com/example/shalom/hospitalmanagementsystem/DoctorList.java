package com.example.shalom.hospitalmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jsonDtos.ChatDto;
import jsonDtos.DoctorMessageDto;
import jsonDtos.StringDto;

public class DoctorList extends AppCompatActivity {
ListView listView;
    ArrayAdapter arrayAdapter;
String TAG="Debugg";
    ArrayList as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
listView=(ListView)findViewById(R.id.l1);
        final String url = "http://10.4.5.139:8080/HospitalManagementServer/ViewDoctorsList";

new AsyncHttpTask().execute(url);

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i=new Intent(getApplicationContext(),DoctorChat.class);
       i.putExtra("pos",position);
        startActivity(i);
    }
});
    }
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;
            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("POST");
                StringDto chatDto = null;

                chatDto = new StringDto();

                chatDto.setMessage(new StaticObjects().getPhoneno());

                String s = new Gson().toJson(chatDto);

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
                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode ==  200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful
                    Log.d(TAG , "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
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

                listView.setAdapter(arrayAdapter);
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

        Type type = new TypeToken<List<DoctorMessageDto>>(){}.getType();
        ArrayList<DoctorMessageDto> inpList = new Gson().fromJson(result, type);
        as = new ArrayList<String>();

        for(int i=0; i< inpList.size();i++ ){
            //JSONObject post = posts.optJSONObject(i);
            String name = inpList.get(i).getName();

            as.add(name);
        }
new StaticObjects().setDoctorMessageDto(inpList);
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, as);



    }
}

