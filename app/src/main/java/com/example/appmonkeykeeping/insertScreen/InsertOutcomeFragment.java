package com.example.appmonkeykeeping.insertScreen;

import static com.example.appmonkeykeeping.annotation.Annotation.DATE_FORMAT;
import static com.example.appmonkeykeeping.annotation.Annotation.INSERT_OUTCOME_COMMENT;
import static com.example.appmonkeykeeping.annotation.Annotation.INSERT_OUTCOME_DATE;
import static com.example.appmonkeykeeping.annotation.Annotation.INSERT_OUTCOME_LOCATION;
import static com.example.appmonkeykeeping.annotation.Annotation.INSERT_OUTCOME_MAIN_MONEY;
import static com.example.appmonkeykeeping.annotation.Annotation.INSERT_OUTCOME_PERIOD;
import static com.example.appmonkeykeeping.annotation.Annotation.INSERT_SHOWING_FORMAT;
import static com.example.appmonkeykeeping.annotation.Annotation.requestChoose;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.appmonkeykeeping.MainActivity;
import com.example.appmonkeykeeping.ModifyActivity;
import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.StatusFragmentDirections;
import com.example.appmonkeykeeping.annotation.Annotation;
import com.example.appmonkeykeeping.center.DatabaseSystem;
import com.example.appmonkeykeeping.custom.NumberTextWatcherForThousand;
import com.example.appmonkeykeeping.databinding.FragmentInsertOutcomeBinding;
import com.example.appmonkeykeeping.dialog.DialogLocationSuggest;
import com.example.appmonkeykeeping.model.Money;
import com.example.appmonkeykeeping.remote.LocationInserting;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InsertOutcomeFragment extends Fragment implements LocationInserting {
    FragmentInsertOutcomeBinding binding;
    private String dateInsert;
    private boolean period;
    private SharedPreferences shareSavingMoney;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInsertOutcomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatDb = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat dateFormatShow = new SimpleDateFormat(INSERT_SHOWING_FORMAT);
        String dateNow = dateFormatShow.format(calendar.getTime());
        binding.tvDateOutcome.setText(dateNow);
        dateInsert = dateFormatDb.format(calendar.getTime());
        binding.edtMainMoneyOutcome.addTextChangedListener(new NumberTextWatcherForThousand(binding.edtMainMoneyOutcome));
        NumberTextWatcherForThousand.trimCommaOfString(binding.edtMainMoneyOutcome.getText().toString());
        binding.imgCalendarOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                SimpleDateFormat dateFormat = new SimpleDateFormat(INSERT_SHOWING_FORMAT);
                                binding.tvDateOutcome.setText(dateFormat.format(calendar.getTime()));
                                SimpleDateFormat dateFormatDb = new SimpleDateFormat(DATE_FORMAT);
                                dateInsert = dateFormatDb.format(calendar.getTime());
                            }
                        };
                        new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                    }
                };
                new DatePickerDialog(getContext(), dateListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        binding.imgLocationOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(getClass().getName(),"location request");
                openDialogSuggestion();
            }
        });
        binding.btnSaveOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtLocationOutcome.getText().toString().trim().equals("")){
                    binding.edtLocationOutcome.setError("Please enter the location field or tap the button location");
                    return;
                }
                if (binding.edtMainMoneyOutcome.getText().toString().trim().equals("")){
                    binding.edtMainMoneyOutcome.setError("Please enter the outcome cash");
                    return;
                }
                NavController navController = Navigation.findNavController(v);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.detailIncomeFragment,true).build();
                InsertOutcomeFragmentDirections.ActionInsertOutcomeFragmentToDetailIncomeFragment action = InsertOutcomeFragmentDirections.actionInsertOutcomeFragmentToDetailIncomeFragment();
                action.setDatetime(dateInsert);
                action.setComment(binding.edtDetailOutcome.getText().toString());
                action.setLocation(binding.edtLocationOutcome.getText().toString());
                action.setMainAmount(binding.edtMainMoneyOutcome.getText().toString());
                action.setIsperiod(binding.boxPeriodOutcome.isChecked());
                navController.navigate(action,navOptions);
            }
        });
        binding.btnBackInsertOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ModifyActivity.class));
            }
        });
    }

    private void openDialogSuggestion(){
        DialogLocationSuggest dialogSuggest = new DialogLocationSuggest();
        dialogSuggest.setListener(this);
        dialogSuggest.show(getActivity().getSupportFragmentManager(),"dialog support");
    }

    @Override
    public void enterLocation(String locationSuggested) {
        binding.edtLocationOutcome.setText(locationSuggested);
    }
}