package com.example.appmonkeykeeping.insertScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.ModifyActivity;
import com.example.appmonkeykeeping.annotation.AnnotationCode;
import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.databinding.FragmentNecessityBinding;
import com.example.appmonkeykeeping.model.Money;

public class NecessityFragment extends Fragment {
    private FragmentNecessityBinding binding;
    private TableOrganization tableOrganization;
    private String mainAmount;
    private String location;
    private String comment;
    private boolean isPeriod;
    private String dateTime;
    private String category;
    private NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableOrganization = TableOrganization.getInstance();
        tableOrganization.initializeDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNecessityBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            NecessityFragmentArgs args = NecessityFragmentArgs.fromBundle(getArguments());
            comment = args.getComment();
            location = args.getLocationInsert();
            isPeriod = args.getIsPeriod();
            mainAmount = args.getMainAmount();
            dateTime = args.getDateTime();
        }
        binding.tagBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Breakfast";
                insertData();
            }
        });
        binding.tagElectricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Electricity";
                insertData();
            }
        });
        binding.tagGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Gas";
                insertData();
            }
        });
        binding.tagGroceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Groceries";
                insertData();
            }
        });
        binding.tagInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Internet";
                insertData();
            }
        });
        binding.tagLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Lunch";
                insertData();
            }
        });
        binding.tagTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Transport";
                insertData();
            }
        });
        binding.tvDateNecessity.setText(dateTime);
    }
    private void insertData(){
        Money money = new Money();
        money.setId(tableOrganization.maxIdDB());
        money.setTag(AnnotationCode.typesOfRecording[1]);
        money.setDate(dateTime);
        money.setActualCost(Long.parseLong(mainAmount.replaceAll(",","").trim()));
        money.setCategory(category);
        money.setDetail(comment.trim().equals("")?"Necessity":"(Necessity) "+comment.trim());
        money.setLocation(location);
        money.setUsePeriod(isPeriod);
        tableOrganization.addMoneyNote(money);
        startActivity(new Intent(getActivity(), ModifyActivity.class));
    }
}