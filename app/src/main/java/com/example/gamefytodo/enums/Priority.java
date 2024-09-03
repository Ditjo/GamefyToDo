package com.example.gamefytodo.enums;

import java.util.ArrayList;
import java.util.List;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW;

    public static List<String> getEnumsAsStringList(){
        List<String> list = new ArrayList<>();
        for (Priority pri : Priority.values()){
            list.add(pri.toString());
        }
        return list;
    }

}
