package com.example.appmonkeykeeping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;
    private TableOrganization tableOrganization;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        tableOrganization = TableOrganization.getInstance();
        binding.tvLastUpdate.setText((tableOrganization.showList().size()>0)?tableOrganization.showList().get(0).getDate():"");
    }
}