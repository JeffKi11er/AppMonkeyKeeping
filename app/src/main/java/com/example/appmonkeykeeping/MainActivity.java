package com.example.appmonkeykeeping;

import static com.example.appmonkeykeeping.annotation.Annotation.TOTAL_AMOUNT_IN_USING;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appmonkeykeeping.databinding.ActivityMainBinding;
import com.example.appmonkeykeeping.remote.DataSaveInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements DataSaveInterface {
    ActivityMainBinding binding;
    private String totalInUse;
    private int unit;
    private boolean unableBack = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();

    }
    private void init(){
    }
    @Override
    public void onBackPressed() {
        if (unableBack) {
            super.onBackPressed();
        }
    }

    @Override
    public void dataInSaving(String message,int unit) {
        totalInUse = message.replace(",","");
        totalInUse = String.valueOf(Integer.parseInt(totalInUse)*unit);
        this.unit = unit;
        Toast.makeText(this,totalInUse,Toast.LENGTH_LONG).show();
    }
}