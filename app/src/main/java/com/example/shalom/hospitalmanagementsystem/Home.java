package com.example.shalom.hospitalmanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Home extends Activity {
    ListView listView;
    ArrayList as;
    ArrayAdapter ad;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(R.id.l1);

        as = new ArrayList();
        as.add("View Patient Record");
        as.add("View Patient History");
        as.add("View Patient Image");
        as.add("View Patient Videos");
        as.add("Doctor Forum Chat");

        ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, as);
        listView.setAdapter(ad);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    intent = new Intent(getApplicationContext(), PatientgetInfo.class);
                } else if (position == 1) {
                    intent = new Intent(getApplicationContext(), PatientgetHistory.class);
                } else if (position == 2) {
                    intent = new Intent(getApplicationContext(), Image.class);
                }
                else if(position==3)
                {
                    intent = new Intent(getApplicationContext(), Videos.class);
                }
                else if (position == 4) {
                    intent = new Intent(getApplicationContext(), DoctorList.class);
                }
                startActivity(intent);
            }
        });



    }

}
