package com.example.gamefytodo;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gamefytodo.enums.Pages;
import com.example.gamefytodo.viewModels.CameraPage;
import com.example.gamefytodo.viewModels.HomePage;
import com.example.gamefytodo.viewModels.RewardPage;
import com.example.gamefytodo.viewModels.TaskPage;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private Button btn_home, btn_task, btn_reward, btn_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initGui();
        Navigation(Pages.HOME);


    }

    void initGui(){
        btn_home = findViewById(R.id.btn_home);
        btn_task = findViewById(R.id.btn_task);
        btn_reward = findViewById(R.id.btn_reward);
        btn_camera = findViewById(R.id.btn_camera);

        btn_home.setOnClickListener(view -> Navigation(Pages.HOME));
        btn_task.setOnClickListener(view -> Navigation(Pages.TASK));
        btn_reward.setOnClickListener(view -> Navigation(Pages.REWARD));
        btn_camera.setOnClickListener(view -> Navigation(Pages.CAMERA));
    }

    public void Navigation(Pages page){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fragment != null) ft.remove(fragment);

        switch(page){
            case HOME:
                fragment = new HomePage();
                break;
            case TASK:
                fragment = new TaskPage();
                break;
            case REWARD:
                fragment = new RewardPage();
                break;
            case CAMERA:
                fragment = new CameraPage();
                break;
            default:
                fragment = new HomePage();
                break;
        }

        ft.add(R.id.main_view, fragment);
        ft.commit();

    }
}