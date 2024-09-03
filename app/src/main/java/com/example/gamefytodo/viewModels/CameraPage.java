package com.example.gamefytodo.viewModels;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.gamefytodo.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class CameraPage extends Fragment {



    private View view;
    Button btn_takePicture, btn_viewGallery;
    private PreviewView previewView;
    private ImageCapture imageCapture;

    private ActivityResultLauncher<String> requestPermissionLauncher;
//    int cameraFacing = CameraSelector.LENS_FACING_BACK;

//    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
//        @Override
//        public void onActivityResult(Boolean result) {
//            if (result){
//                startCamera(cameraFacing);
//            }
//        }
//    });


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.camera_page, container, false);

        initGui();

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->{
            if (isGranted) {
                startCamera();
            } else {
                Toast.makeText(view.getContext(), "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        });

        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            startCamera();
        } else{
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }

        return view;
    }

    void initGui(){
        btn_takePicture = view.findViewById(R.id.btn_take_picture);
        btn_viewGallery = view.findViewById(R.id.btn_view_gallery);
        previewView = view.findViewById(R.id.camera_preview);

        btn_takePicture.setOnClickListener(v -> {
//            Toast.makeText(getContext(),"Picture Taken", Toast.LENGTH_LONG).show();
            takePhoto();
        });
        btn_viewGallery.setOnClickListener(v -> {
            File directory = view.getContext().getExternalFilesDir("Images");
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".jpg"));
            if (files != null){
                List<File> imageFiles = new ArrayList<>(Arrays.asList(files));
            }

        });

    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(view.getContext());

        cameraProviderFuture.addListener(() ->{
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(previewView.getDisplay().getRotation())
                        .build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing((CameraSelector.LENS_FACING_BACK))
                        .build();

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (ExecutionException | InterruptedException e){
                Log.e("CameraXApp", "Error starting camera",e);
            }
        }, ContextCompat.getMainExecutor(view.getContext()));
    }

    private void takePhoto() {
        if (imageCapture == null) {
            return;
        }

        File photoFile = createImageFile(view.getContext());

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(view.getContext()), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Toast.makeText(view.getContext(), "Photo saved to " + photoFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CameraXApp", "Photo capture failed: " + exception.getMessage(), exception);
            }
        });
    }

    private File createImageFile(Context context){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = context.getExternalFilesDir("Images");
        return new File(storageDir, imageFileName);
    }
}
