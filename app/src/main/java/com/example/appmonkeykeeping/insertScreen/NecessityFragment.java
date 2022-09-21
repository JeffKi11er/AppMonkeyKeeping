package com.example.appmonkeykeeping.insertScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.center.DatabaseSystem;
import com.example.appmonkeykeeping.databinding.FragmentNecessityBinding;

public class NecessityFragment extends Fragment {
    private FragmentNecessityBinding binding;
    private DatabaseSystem databaseSystem;
    private String mainAmount;
    private String location;
    private String comment;
    private boolean isPeriod;
    private String dateTime;
    private NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseSystem = DatabaseSystem.getInstance();
        databaseSystem.realmInitialize();
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
        Log.e(getClass().getName(),dateTime+"/"+mainAmount+"/"+location+"/"+comment+"/"+isPeriod);
    }
}