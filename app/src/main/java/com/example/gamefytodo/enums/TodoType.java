package com.example.gamefytodo.enums;

import java.util.ArrayList;
import java.util.List;

public enum TodoType {
    CLEANING,
    COOKING,
    HOMEWORK,
    PLANNING,
    FIXING;

    public static List<String> getEnumsAsStringList(){
        List<String> list = new ArrayList<>();
        for (TodoType pri : TodoType.values()){
            list.add(pri.toString());
        }
        return list;
    }
}
