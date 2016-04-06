package com.example.shalom.hospitalmanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import jsonDtos.PatHistoryDto;

public class PatientHistory extends AppCompatActivity {
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);

        t1 = (TextView) findViewById(R.id.tv1);
        t2 = (TextView) findViewById(R.id.tv2);
        PatHistoryDto patHistoryDto = new StaticObjects().getPatHistoryDto();
        t1.setText(patHistoryDto.getIllness());
        t2.setText(patHistoryDto.getPrescription());

    }
}
