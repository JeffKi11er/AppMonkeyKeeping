package com.example.appmonkeykeeping;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.adapter.PageAdapter;
import com.example.appmonkeykeeping.databinding.FragmentPlayingModeBinding;
import com.example.appmonkeykeeping.helper.ViewSwiper;
import com.example.appmonkeykeeping.model.ModelPage;

import java.util.ArrayList;
import java.util.List;

public class PlayingModeFragment extends Fragment implements ViewSwiper.PageClickNotification{
    FragmentPlayingModeBinding binding;
    ViewSwiper viewSwiper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayingModeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewSwiper = new ViewSwiper();
        viewSwiper.setListener(this);
        viewSwiper.addListPage(new ModelPage(R.drawable.outcome_image,"OUTCOME","Insert your outcome and calculate your spending",0));
        viewSwiper.addListPage(new ModelPage(R.drawable.img_funny_count_money,"INCOME","Add your income into your cash",0));
        viewSwiper.addListPage(new ModelPage(R.drawable.lend_page,"LEND","Checkout your lend and remember it",0));
        viewSwiper.addListPage(new ModelPage(R.drawable.transfer_money,"TRANSFER","Transfer your money for beloved and family",0));
        binding.viewPager.setAdapter(viewSwiper.initializeAdapter(getContext()));
        binding.viewPager.setPadding(130,0,130,0);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                binding.viewPager.setBackgroundColor(viewSwiper.setUpPaging(new Integer[]{
                        view.getResources().getColor(R.color.black),
                        view.getResources().getColor(R.color.save),
                        view.getResources().getColor(R.color.lend),
                        view.getResources().getColor(R.color.teal_700)
                },position,positionOffset,positionOffsetPixels));
            }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClickPaging(int position) {
        switch (viewSwiper.showTargetPage(position)){
            case "OUTCOME":
                startActivity(new Intent(getActivity(),MainActivity.class));
                break;
            case "INCOME":
                startActivity(new Intent(getActivity(),IncomeActivity.class));
                break;
        }

    }
}