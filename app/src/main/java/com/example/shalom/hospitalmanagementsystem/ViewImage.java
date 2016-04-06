package com.example.shalom.hospitalmanagementsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import jsonDtos.PatientDto;
import jsonDtos.StringDto;

public class ViewImage extends AppCompatActivity {
    static StringDto in;
    Intent intent;
    String TAG = "Debugg";
    Button enter;
    EditText ed1;
    VideoView ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        enter = (Button) findViewById(R.id.b1);
        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (VideoView) findViewById(R.id.ed2);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://10.4.5.139:8080/HospitalManagementServer/Video";
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

    public byte[] convertInputStreamTobytes(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";
        byte[] r = new byte[3000000];
        inputStream.read(r);

        return r;
    }

    private void parseResult(String result) {
        /* JSONObject response = new JSONObject(result);

            JSONArray posts = response.optJSONArray("posts");*/


        in = new Gson().fromJson(result, StringDto.class);

        Log.d(TAG, in.getMessage());

    }

    class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        byte[] sp;
        String id = ed1.getText().toString();
        String response;

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
                urlConnection.setRequestProperty("Accept", "UNDEFINED");

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
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                    sp = convertInputStreamTobytes(inputStream);

                    //       parseResult(response);

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
                    //decodedString = Base64.decode(in.getMessage().getBytes());
                    File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "test.mp4");

                    file.createNewFile();
                    byte[] data1 = {1, 1, 0, 0};
//////write the bytes in file
                    if (file.exists()) {
                        OutputStream fo = new FileOutputStream(file);
                        Log.d(TAG, "" + sp.length);
                        fo.write(sp);
                        fo.close();
                        System.out.println("file created: " + file);

                        Uri video = Uri.parse(file.getPath());
                        ed2.setVideoURI(video);
                        ed2.start();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("Error", e.toString());
                }

            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
}
