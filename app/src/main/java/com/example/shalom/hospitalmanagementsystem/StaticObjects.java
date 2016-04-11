package com.example.shalom.hospitalmanagementsystem;

import java.util.ArrayList;

import jsonDtos.DoctorMessageDto;
import jsonDtos.PatHistoryDto;
import jsonDtos.PatInfoDto;

/**
 * Created by shalom on 6/4/16.
 */
public class StaticObjects {
    static ArrayList<String> ht;
static String phoneno;
    static PatInfoDto patInfoDto;
    static PatHistoryDto patHistoryDto;
static ArrayList<DoctorMessageDto> doctorMessageDto;

    public ArrayList<DoctorMessageDto> getDoctorMessageDto() {
        return doctorMessageDto;
    }

    public void setDoctorMessageDto(ArrayList<DoctorMessageDto> doctorMessageDto) {
        this.doctorMessageDto = doctorMessageDto;
    }

    public PatInfoDto getPatInfoDto() {
        return patInfoDto;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public void setPatInfoDto(PatInfoDto patInfoDto) {
        StaticObjects.patInfoDto = patInfoDto;
    }

    public PatHistoryDto getPatHistoryDto() {
        return patHistoryDto;
    }

    public void setPatHistoryDto(PatHistoryDto patHistoryDto) {
        StaticObjects.patHistoryDto = patHistoryDto;
    }

    public ArrayList<String> getHt() {
        return ht;
    }

    public void setHt(ArrayList<String> ht) {
        StaticObjects.ht = ht;
    }
}
