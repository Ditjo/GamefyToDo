package com.example.gamefytodo.data;

//
import com.example.gamefytodo.enums.TodoType;
import com.example.gamefytodo.enums.Priority;
import com.example.gamefytodo.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class mockData {

    public static List<Todo> getMockTodoData() {
        List<Todo> list = new ArrayList<>();

        list.add(new Todo(
                        "Task1",
                        "Task Description1",
                        1,
                        null,
                        10,
                        TodoType.CLEANING,
                        Priority.MEDIUM,
                        null,
                        false,
                        false
                )
        );
        list.add(new Todo(
                        "Task2",
                        "Task Description2",
                        2,
                        null,
                        20,
                        TodoType.COOKING,
                        Priority.LOW,
                        null,
                        false,
                        false
                )
        );
        list.add(new Todo(
                        "Task3",
                        "Task Description3",
                        3,
                        null,
                        30,
                        TodoType.PLANNING,
                        Priority.HIGH,
                        null,
                        false,
                        false
                )
        );
        return list;
    }

}
