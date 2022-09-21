package com.example.appmonkeykeeping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.databinding.FragmentDashBoardBinding;
import com.example.appmonkeykeeping.helper.ChartAnalyze;
import com.example.appmonkeykeeping.model.Money;

import java.util.ArrayList;

public class DashBoardFragment extends Fragment {
    private FragmentDashBoardBinding binding;
    private NavController controller;
    private TableOrganization tableOrganization;
    private ChartAnalyze chartAnalyze;
    private long totalCash;
    private long totalIncome;
    private long totalOutcome;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashBoardBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        tableOrganization = TableOrganization.getInstance();
        chartAnalyze = ChartAnalyze.getInstance();
        totalCash = 0;
        tableOrganization.initializeDatabase();
        Long[]tableFinance = tableOrganization.totalAmount();
        totalIncome = tableFinance[0];
        totalOutcome = tableFinance[1];
        totalCash += totalIncome;
        totalCash -= totalOutcome;
        binding.tvCashTotal.setText("¥"+totalCash);
        binding.tvLastUpdate.setText((tableOrganization.showList().size()>0)?tableOrganization.showList().get(0).getDate():"");
        ArrayList<Long>moneyIO = new ArrayList<>();
        moneyIO.add(totalIncome*100);
        moneyIO.add(totalOutcome*100);
        binding.barChartTotal.setData(chartAnalyze.setUpDataBar(moneyIO,"Income and Outcome",
                new int[]{
                        view.getResources().getColor(R.color.income),
                        view.getResources().getColor(R.color.outcome)
                }));
        chartAnalyze.setAnimateBar(binding.barChartTotal,"Income and Outcome Chart");
        return view;
    }
}