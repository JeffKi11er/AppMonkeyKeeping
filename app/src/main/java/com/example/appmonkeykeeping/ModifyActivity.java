package com.example.appmonkeykeeping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.appmonkeykeeping.databinding.ActivityModifyBinding;

public class ModifyActivity extends AppCompatActivity {
    ActivityModifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }
    private void init(){}
}