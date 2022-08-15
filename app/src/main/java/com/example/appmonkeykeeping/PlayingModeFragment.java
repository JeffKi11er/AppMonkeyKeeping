package com.example.appmonkeykeeping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.databinding.FragmentPlayingModeBinding;

public class PlayingModeFragment extends Fragment {
    FragmentPlayingModeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayingModeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.btnOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.mainAmountFragment,true).build();
                controller.navigate(R.id.action_playingModeFragment_to_mainAmountFragment,null,navOptions);
            }
        });
        return view;
    }
}