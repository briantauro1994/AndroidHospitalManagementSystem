package com.example.shalom.hospitalmanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jsonDtos.PatientDto;
import jsonDtos.StringDto;

public class Image extends AppCompatActivity {
    static StringDto in;
    Intent intent1;
    String TAG = "Debugg";
    Button enter;
    EditText ed1;
    ImageView ed2;
    ArrayList<String> pp;
    ArrayAdapter ld;

    Intent intent;
    ListView l1;
    List<StringDto> inpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        enter = (Button) findViewById(R.id.b1);
        ed1 = (EditText) findViewById(R.id.ed1);
        l1 = (ListView) findViewById(R.id.l1);
        intent1 = new Intent(getApplicationContext(), ViewImage.class);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://10.4.5.139:8080/HospitalManagementServer/Image";
                new AsyncHttpTask().execute(url);
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

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

    private void parseResult(String result) {
        /* JSONObject response = new JSONObject(result);

            JSONArray posts = response.optJSONArray("posts");*/

        Type type = new TypeToken<List<StringDto>>() {
        }.getType();
        inpList = new Gson().fromJson(result, type);
        pp = new ArrayList<String>();
        for (int i = 0; i < inpList.size(); i++) {
            pp.add(("" + i + 1));
        }

    }

    class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        String id = ed1.getText().toString();


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

                PatientDto patientDto = null;

                patientDto = new PatientDto();
                patientDto.setId(id);


                String s = new Gson().toJson(patientDto);

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
                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    parseResult(response);

                    result = 1; // Successful

                } else {
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

            if (result == 1) {


                byte[] decodedString = null;
                try {
                    decodedString = Base64.decode(inpList.get(0).getMessage().getBytes());
                    ld = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, pp);
                    l1.setAdapter(ld);
                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ArrayList<String> as = new ArrayList<String>();

                            for (int ip = 0; ip < pp.size(); ip++) {
                                as.add(inpList.get(ip).getMessage());
                            }

                            StaticObjects staticObjects = new StaticObjects();
                            staticObjects.setHt(as);

                            intent1.putExtra("pos", position);
                            startActivity(intent1);

                        }
                    });
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.d("ErrorHere", "" + e);
                }
                Log.d("St--", ":" + decodedString.length);
             /*   Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                        decodedString.length);

                ed2.setImageBitmap(bitmap);*/

            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
}


