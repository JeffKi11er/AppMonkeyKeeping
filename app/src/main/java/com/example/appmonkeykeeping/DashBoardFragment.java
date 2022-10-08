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

import com.example.appmonkeykeeping.adapter.ItemChartAdapter;
import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.databinding.FragmentDashBoardBinding;
import com.example.appmonkeykeeping.helper.ChartAnalyze;
import com.example.appmonkeykeeping.helper.DataPieChart;
import com.example.appmonkeykeeping.model.ChartItemCategorize;
import com.example.appmonkeykeeping.model.Money;

import java.util.ArrayList;
import java.util.Random;

public class DashBoardFragment extends Fragment {
    private FragmentDashBoardBinding binding;
    private NavController controller;
    private TableOrganization tableOrganization;
    private ChartAnalyze chartAnalyze;
    private long totalCash;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashBoardBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        tableOrganization = TableOrganization.getInstance();
        chartAnalyze = ChartAnalyze.getInstance();
        totalCash = 0;
        tableOrganization.initializeDatabase();
        Long[]tableOverall = tableOrganization.totalAmount();
        Long[]tableCategories = tableOrganization.categoriesIncomeAndOutcome();
        Long[]tableStackHolder = tableOrganization.moneyStackHolder();
        totalCash += tableOverall[0];
        totalCash -= tableOverall[1];
        binding.tvCashTotal.setText("¥"+encodeMoney(totalCash));
        binding.tvCashTotal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                binding.tvCashTotal.setText("¥"+totalCash);
                return true; 
            }
        });
        binding.tvLastUpdate.setText((tableOrganization.showList().size()>0)?tableOrganization.showList().get(0).getDate():"");
        ArrayList<DataPieChart>moneyInAndOut = new ArrayList<>();
        ArrayList<Integer>colorInAndOut = new ArrayList<>();
        analyzeData(tableCategories,view,moneyInAndOut,colorInAndOut);
        binding.pieChartOverall.setData(chartAnalyze.setUpDataPie(moneyInAndOut,"Income and Outcome",convertIntegers(colorInAndOut)));
        chartAnalyze.setAnimatePie(binding.pieChartOverall,"Income and Outcome spending Chart");
        ArrayList<DataPieChart>moneyHolder = new ArrayList<>();
        ArrayList<Integer>colorHolder = new ArrayList<>();
        analyzeData(tableStackHolder,view,moneyHolder,colorHolder);
        binding.pieChartOverallHolder.setData(chartAnalyze.setUpDataPie(moneyHolder,"Overall holder",convertIntegers(colorHolder)));
        chartAnalyze.setAnimatePie(binding.pieChartOverallHolder,"Money in holder for each categories");
        ArrayList<ChartItemCategorize>chartItemCategorizes = new ArrayList<>();
        analyzeCategorizeChart(tableCategories,tableStackHolder,chartItemCategorizes,view);
         ItemChartAdapter adapter = new ItemChartAdapter(getContext(),chartItemCategorizes);
        binding.rclCategoryChart.setAdapter(adapter);
        return view;
    }
    private void analyzeData(Long[]data,View view,ArrayList<DataPieChart>dataPieCharts,ArrayList<Integer>colors){
        switch (data.length){
            case 7:
                if(data[0]!=0) {
                    dataPieCharts.add(new DataPieChart("Income", (float) data[0]));
                    colors.add(view.getResources().getColor(R.color.income));
                }
                if(data[1]!=0) {
                    dataPieCharts.add(new DataPieChart("Necessities", (float) data[1]));
                    colors.add(view.getResources().getColor(R.color.necessary));
                }
                if(data[2]!=0) {
                    dataPieCharts.add(new DataPieChart("Finance", (float) data[2]));
                    colors.add(view.getResources().getColor(R.color.finance));
                }
                if(data[3]!=0) {
                    dataPieCharts.add(new DataPieChart("Saved", (float) data[3]));
                    colors.add(view.getResources().getColor(R.color.save_money));
                }
                if(data[4]!=0) {
                    dataPieCharts.add(new DataPieChart("Education", (float) data[4]));
                    colors.add(view.getResources().getColor(R.color.education));
                }
                if(data[5]!=0) {
                    dataPieCharts.add(new DataPieChart("Play", (float) data[5]));
                    colors.add(  view.getResources().getColor(R.color.play));
                }
                if(data[6]!=0) {
                    dataPieCharts.add(new DataPieChart("Give", (float) data[6]));
                    colors.add(view.getResources().getColor(R.color.give));
                }
                break;
            case 6:
                if(data[0]!=0) {
                    dataPieCharts.add(new DataPieChart("Necessities", (float) data[0]));
                    colors.add(view.getResources().getColor(R.color.necessary));
                }
                if(data[1]!=0) {
                    dataPieCharts.add(new DataPieChart("Finance", (float) data[1]));
                    colors.add(view.getResources().getColor(R.color.finance));
                }
                if(data[2]!=0) {
                    dataPieCharts.add(new DataPieChart("Saved", (float) data[2]));
                    colors.add(view.getResources().getColor(R.color.save_money));
                }
                if(data[3]!=0) {
                    dataPieCharts.add(new DataPieChart("Education", (float) data[3]));
                    colors.add(view.getResources().getColor(R.color.education));
                }
                if(data[4]!=0) {
                    dataPieCharts.add(new DataPieChart("Play", (float) data[4]));
                    colors.add( view.getResources().getColor(R.color.play));
                }
                if(data[5]!=0) {
                    dataPieCharts.add(new DataPieChart("Give", (float) data[5]));
                    colors.add(view.getResources().getColor(R.color.give));
                }
                break;
        }
    }
    private void analyzeCategorizeChart(Long[]dataIO,Long[]dataMaintain,ArrayList<ChartItemCategorize>chartItems,View view){
        int []colors = new int[]{
                view.getResources().getColor(R.color.necessary),
                view.getResources().getColor(R.color.finance),
                view.getResources().getColor(R.color.save_money),
                view.getResources().getColor(R.color.education),
                view.getResources().getColor(R.color.play),
                view.getResources().getColor(R.color.give)
        };
        for (int i = 0; i < dataMaintain.length; i++) {
            int indexIO = i+1;
            String categorize = null;
            switch (i){
                case 0:
                    categorize = "Necessities";
                    break;
                case 1:
                    categorize = "Finance";
                    break;
                case 2:
                    categorize = "Saved";
                    break;
                case 3:
                    categorize = "Education";
                    break;
                case 4:
                    categorize = "Play";
                    break;
                case 5:
                    categorize = "Give";
                    break;
            }
            chartItems.add(new ChartItemCategorize(colors[i],categorize,dataMaintain[i],dataIO[indexIO]));
        }
    }
    public int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
    private String encodeMoney(long text){
        String str = Long.toString(text);
        Log.e("hashing",str);
        str = str.replaceAll("0",pickRandom("x","s"));
        str = str.replaceAll("1","t");
        str = str.replaceAll("2","n");
        str = str.replaceAll("3","m");
        str = str.replaceAll("4","r");
        str = str.replaceAll("5","l");
        str = str.replaceAll("6","g");
        str = str.replaceAll("7","k");
        str = str.replaceAll("8",pickRandom("v","d"));
        str = str.replaceAll("9",pickRandom("p","b"));
        return str;
    }
    private String pickRandom(String cs1,String cs2){
        return new Random().nextBoolean() ? cs1 : cs2;
    }
}