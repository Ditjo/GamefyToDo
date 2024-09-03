package com.example.gamefytodo.enums;

import java.util.ArrayList;
import java.util.List;

public enum Repeatable {
    DAY,
    WEEK,
    MONTH,
    YEAR;

    public static List<String> getEnumsAsStringList(){
        java.util.List<java.lang.String> list = new ArrayList<>();
        for (Repeatable pri : Repeatable.values()){
            list.add(pri.toString());
        }
        return list;
    }
}
