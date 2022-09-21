package com.example.appmonkeykeeping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.example.appmonkeykeeping.databinding.ActivityIncomeBinding;
import com.example.appmonkeykeeping.insertScreen.InsertIncomeFragment;

public class IncomeActivity extends AppCompatActivity {
    ActivityIncomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }
    private void init(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_income,
                new InsertIncomeFragment()).commit();
    }
}