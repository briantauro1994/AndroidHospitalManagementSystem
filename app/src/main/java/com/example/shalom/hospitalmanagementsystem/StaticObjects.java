package com.example.shalom.hospitalmanagementsystem;

import java.util.ArrayList;

import jsonDtos.PatHistoryDto;
import jsonDtos.PatInfoDto;

/**
 * Created by shalom on 6/4/16.
 */
public class StaticObjects {
    static ArrayList<String> ht;

    static PatInfoDto patInfoDto;
    static PatHistoryDto patHistoryDto;

    public PatInfoDto getPatInfoDto() {
        return patInfoDto;
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
