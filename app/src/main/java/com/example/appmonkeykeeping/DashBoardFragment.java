package com.example.appmonkeykeeping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.databinding.FragmentDashBoardBinding;

public class DashBoardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentDashBoardBinding binding;
    private String mParam1;
    private String mParam2;
    private NavController controller;

    public DashBoardFragment() {
    }
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashBoardBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        binding.btnShowRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = Navigation.findNavController(v);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.statusFragment,true).build();
                controller.navigate(R.id.action_dashBoardFragment_to_statusFragment,null,navOptions);
            }
        });
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller = Navigation.findNavController(v);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.mainAmountFragment,true).build();
                controller.navigate(R.id.action_dashBoardFragment_to_mainAmountFragment,null,navOptions);
            }
        });
    }
}