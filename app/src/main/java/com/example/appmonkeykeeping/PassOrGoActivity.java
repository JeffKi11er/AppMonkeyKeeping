package com.example.appmonkeykeeping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appmonkeykeeping.databinding.ActivityPassOrGoBinding;

public class PassOrGoActivity extends AppCompatActivity {
    ActivityPassOrGoBinding binding;
    private int tryLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPassOrGoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }
    private void init(){
        tryLeft = 5;
        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tryLeft<0){
                    binding.edtPw.setVisibility(View.GONE);
                }
                if (!binding.edtPw.getText().toString().equals("0902267555")){
                    binding.edtPw.setError("You have only "+ tryLeft-- +" chance left");
                    return;
                }
                startActivity(new Intent(PassOrGoActivity.this,ModifyActivity.class));
                finish();
            }
        });
    }
}