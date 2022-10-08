package com.example.appmonkeykeeping.insertScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.MainActivity;
import com.example.appmonkeykeeping.ModifyActivity;
import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.annotation.AnnotationCode;
import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.databinding.FragmentDetailIncomeBinding;
import com.example.appmonkeykeeping.helper.AlarmManagerHelper;
import com.example.appmonkeykeeping.helper.NotificationHelper;
import com.example.appmonkeykeeping.helper.ViewSwiper;
import com.example.appmonkeykeeping.model.ModelPage;
import com.example.appmonkeykeeping.model.Money;

public class DetailIncomeFragment extends Fragment implements ViewSwiper.PageClickNotification {
    FragmentDetailIncomeBinding binding;
    private TableOrganization tableOrganization;
    private String mainAmount;
    private String location;
    private String comment;
    private boolean isPeriod;
    private String dateTime;
    private NavController navController;
    private ViewSwiper viewSwiper;
    private NotificationHelper notificationHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableOrganization = TableOrganization.getInstance();
        tableOrganization.initializeDatabase();
        notificationHelper = NotificationHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailIncomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            DetailIncomeFragmentArgs args = DetailIncomeFragmentArgs.fromBundle(getArguments());
            comment = args.getComment();
            location = args.getLocation();
            isPeriod = args.getIsperiod();
            mainAmount = args.getMainAmount();
            dateTime = args.getDatetime();
        }
        navController = Navigation.findNavController(view);
        viewSwiper = new ViewSwiper();
        viewSwiper.setListener(this);
        for (int i = 0; i < tableOrganization.moneyProjectCategorizes().length; i++) {
            Log.e(getClass().getName(),String.valueOf(tableOrganization.moneyProjectCategorizes()[i]));
        }
        viewSwiper.addListPage(new ModelPage(R.drawable.necessity,"NECESSITIES","Which is very necessary for you ?",tableOrganization.moneyProjectCategorizes()[0]));
        viewSwiper.addListPage(new ModelPage(R.drawable.finance_team,"FINANCIAL FREEDOM","Free in investiagtion",tableOrganization.moneyProjectCategorizes()[1]));
        viewSwiper.addListPage(new ModelPage(R.drawable.save,"LONG TERM SAVING","Time is your money",tableOrganization.moneyProjectCategorizes()[2]));
        viewSwiper.addListPage(new ModelPage(R.drawable.study,"EDUCATION","Billionare also need to study",tableOrganization.moneyProjectCategorizes()[3]));
        viewSwiper.addListPage(new ModelPage(R.drawable.play,"PLAY","I got the money, let's spend it off",tableOrganization.moneyProjectCategorizes()[4]));
        viewSwiper.addListPage(new ModelPage(R.drawable.give,"GIVE","Now that's the way your heart working ! You are not a robot",tableOrganization.moneyProjectCategorizes()[5]));
        binding.viewpageOutcome.setAdapter(viewSwiper.initializeAdapter(getContext()));
        binding.viewpageOutcome.setPadding(130,0,130,0);
        binding.viewpageOutcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                binding.viewpageOutcome.setBackgroundColor(viewSwiper.setUpPaging(new Integer[]{
                        view.getResources().getColor(R.color.necessary),
                        view.getResources().getColor(R.color.finance),
                        view.getResources().getColor(R.color.save_money),
                        view.getResources().getColor(R.color.education),
                        view.getResources().getColor(R.color.play),
                        view.getResources().getColor(R.color.give)
                },position,positionOffset,positionOffsetPixels));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.imgDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ModifyActivity.class));
            }
        });
    }
    @Override
    public void onClickPaging(int position) {
        if (viewSwiper.showTargetPage(position).equals("NECESSITIES")){
            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.necessityFragment,true).build();
            DetailIncomeFragmentDirections.ActionDetailIncomeFragmentToNecessityFragment action = DetailIncomeFragmentDirections.actionDetailIncomeFragmentToNecessityFragment();
            action.setDateTime(dateTime);
            action.setLocationInsert(location);
            action.setComment(comment);
            action.setIsPeriod(isPeriod);
            action.setMainAmount(mainAmount);
            navController.navigate(action,navOptions);
        }else {
            Money money = new Money();
            money.setId(tableOrganization.maxIdDB());
            money.setTag(AnnotationCode.typesOfRecording[0]);
            switch (viewSwiper.showTargetPage(position)){
                case "FINANCIAL FREEDOM":
                    money.setCategory("Finance");
                    break;
                case "LONG TERM SAVING":
                    money.setCategory("Save");
                    break;
                case "EDUCATION":
                    money.setCategory("Education");
                    break;
                case "PLAY":
                    money.setCategory("Play");
                    break;
                case "GIVE":
                    money.setCategory("Give");
                    break;
            }
            money.setActualCost(Long.parseLong(mainAmount.replaceAll(",","").trim()));
            money.setLocation(location);
            money.setUsePeriod(isPeriod);
            money.setDate(dateTime);
            money.setDetail(comment);
            tableOrganization.addMoneyNote(money);
//            notificationHelper.sendOnChannel(getContext(),"Successfully saved !",AnnotationCode.CHANNEL_ALARM);
            startActivity(new Intent(getActivity(), ModifyActivity.class));
        }
    }
}