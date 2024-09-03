package com.example.gamefytodo.viewModels;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gamefytodo.data.mockData;
import com.example.gamefytodo.enums.Pages;
import com.example.gamefytodo.enums.Priority;
import com.example.gamefytodo.enums.Repeatable;
import com.example.gamefytodo.enums.TodoType;
import com.example.gamefytodo.models.Todo;
import com.example.gamefytodo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskPage extends Fragment {

    private View viewContext;
    private FloatingActionButton btn_newTask;
    private EditText et_newTask_title, et_newTask_description, et_newTask_coins, et_newTask_startDate, et_newTask_endDate;
    private TextView tv_newTask_title, tv_newTask_description, tv_newTask_coins;
    private Spinner sv_newTask_typeOfTask, sv_newTask_priority, sv_newTask_repeatable;
    private TextView tv_taskPage_text;
    private Calendar calendar;
    List<Todo> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContext = inflater.inflate(R.layout.task_page, container, false);

        calendar = Calendar.getInstance();
        list = mockData.getMockTodoData();
        initGui();
        return viewContext;
    }

    void initGui(){
        btn_newTask = viewContext.findViewById(R.id.btn_newTask);
        tv_taskPage_text = viewContext.findViewById(R.id.tv_taskPage_title);

        tv_taskPage_text.setText("Task Page View: " + String.valueOf(list.size()));

        btn_newTask.setOnClickListener(x -> onNewTaskOpenDialog());

    }

    void onNewTaskOpenDialog(){
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Create New Task")
                .setView(inflater.inflate(R.layout.new_task, null))
                .setPositiveButton("Create", (dialogInterface, i) -> onCreateNewTask())
                .setNegativeButton("Cancel", ((dialogInterface, i) -> {

                }));

        AlertDialog dialog = builder.create();

        dialog.show();

        et_newTask_title = dialog.findViewById(R.id.et_newTask_title);
        et_newTask_description = dialog.findViewById(R.id.et_newTask_description);
        et_newTask_coins = dialog.findViewById(R.id.et_newTask_coins);

        sv_newTask_priority = dialog.findViewById(R.id.sv_newTask_priority);
        sv_newTask_typeOfTask = dialog.findViewById(R.id.sv_newTask_typeOfTask);
        sv_newTask_repeatable = dialog.findViewById(R.id.sv_newTask_repeatable);

        et_newTask_startDate = dialog.findViewById(R.id.et_newTask_startDate);
        et_newTask_endDate = dialog.findViewById(R.id.et_newTask_endDate);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(viewContext.getContext(), android.R.layout.simple_list_item_1, Priority.getEnumsAsStringList());
        ArrayAdapter<String> typeOfTaskAdapter = new ArrayAdapter<>(viewContext.getContext(), android.R.layout.simple_list_item_1, TodoType.getEnumsAsStringList());
        ArrayAdapter<String> repeatableAdapter = new ArrayAdapter<>(viewContext.getContext(), android.R.layout.simple_list_item_1, Repeatable.getEnumsAsStringList());

        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfTaskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sv_newTask_priority.setAdapter(priorityAdapter);
        sv_newTask_typeOfTask.setAdapter(typeOfTaskAdapter);
        sv_newTask_repeatable.setAdapter(repeatableAdapter);

        et_newTask_startDate.setOnClickListener(x -> showDatePickerDialog(true));
        et_newTask_endDate.setOnClickListener(x -> showDatePickerDialog(false));
    }

    void showDatePickerDialog(boolean isStartDate){

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                viewContext.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        updateEditTextDate(isStartDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    void updateEditTextDate(boolean isStartDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (isStartDate){
            et_newTask_startDate.setText(sdf.format(calendar.getTime()));
        } else {
            et_newTask_endDate.setText(sdf.format(calendar.getTime()));
        }
    }

    void onCreateNewTask(){
        Todo todo = new Todo(
            et_newTask_title.getText().toString(),
            et_newTask_description.getText().toString(),
            Integer.parseInt(et_newTask_coins.getText().toString())
        );

        tv_newTask_title.setText(todo.getTitle());
        tv_newTask_description.setText(todo.getDescription());
        tv_newTask_coins.setText(String.valueOf(todo.getCoins()));
    }



}
