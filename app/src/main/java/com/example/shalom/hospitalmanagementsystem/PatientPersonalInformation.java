package com.example.shalom.hospitalmanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import jsonDtos.PatInfoDto;

public class PatientPersonalInformation extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_personal_information);

        t1 = (TextView) findViewById(R.id.tv1);
        t2 = (TextView) findViewById(R.id.tv2);

        t3 = (TextView) findViewById(R.id.tv3);
        t4 = (TextView) findViewById(R.id.tv4);
        t5 = (TextView) findViewById(R.id.tv5);
        t6 = (TextView) findViewById(R.id.tv6);
        t7 = (TextView) findViewById(R.id.tv7);


        PatInfoDto patInfoDto = new StaticObjects().getPatInfoDto();
        Log.d("geddddddddddddddd", patInfoDto.getName());

        t1.setText(patInfoDto.getName());
        t2.setText(patInfoDto.getAddress());
        t3.setText(patInfoDto.getPhoneno());
        t4.setText(patInfoDto.getAge());
        t5.setText(patInfoDto.getSex());
        t6.setText(patInfoDto.getIllness());
        t7.setText(patInfoDto.getPrescription());


    }
}
