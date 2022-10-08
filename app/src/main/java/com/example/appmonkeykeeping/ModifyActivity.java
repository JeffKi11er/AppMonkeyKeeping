package com.example.appmonkeykeeping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.appmonkeykeeping.databinding.ActivityModifyBinding;
import com.google.android.material.navigation.NavigationBarView;

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
    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.nav_list:
                    fragment = new StatusFragment();
                    break;
                case R.id.nav_edit:
                    fragment = new PlayingModeFragment();
                    break;
                case R.id.nav_chart:
                    fragment = new DashBoardFragment();
                    break;
                case R.id.nav_setting:
                    fragment = new SettingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_bottom_nav,
                    fragment).commit();
            return  true;
        }
    };
    private void init(){
        binding.bottomNav.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_bottom_nav,
                new StatusFragment()).commit();
    }
}