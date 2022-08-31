package com.example.appmonkeykeeping.insertScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.databinding.FragmentInsertIncomeBinding;

public class InsertIncomeFragment extends Fragment {
    FragmentInsertIncomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInsertIncomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }
}