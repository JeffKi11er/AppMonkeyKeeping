package com.example.appmonkeykeeping.insertScreen;

import static com.example.appmonkeykeeping.annotation.Annotation.DATE_FORMAT;
import static com.example.appmonkeykeeping.annotation.Annotation.INSERT_SHOWING_FORMAT;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.appmonkeykeeping.ModifyActivity;
import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.annotation.Annotation;
import com.example.appmonkeykeeping.center.DatabaseSystem;
import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.custom.NumberTextWatcherForThousand;
import com.example.appmonkeykeeping.databinding.FragmentInsertIncomeBinding;
import com.example.appmonkeykeeping.dialog.DialogLocationSuggest;
import com.example.appmonkeykeeping.model.Money;
import com.example.appmonkeykeeping.remote.LocationInserting;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InsertIncomeFragment extends Fragment implements LocationInserting{
    private boolean period;
    private String dateInsert;
    FragmentInsertIncomeBinding binding;
    private TableOrganization tableOrganization;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableOrganization = TableOrganization.getInstance();
        tableOrganization.initializeDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInsertIncomeBinding.inflate(inflater,container,false);
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
        SimpleDateFormat dbFormat = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat dateFormat = new SimpleDateFormat(INSERT_SHOWING_FORMAT);
        String dateNow = dateFormat.format(calendar.getTime());
        binding.tvDateIncome.setText(dateNow);
        dateInsert = dbFormat.format(calendar.getTime());
        binding.edtMainMoneyIncome.addTextChangedListener(new NumberTextWatcherForThousand(binding.edtMainMoneyIncome));
        NumberTextWatcherForThousand.trimCommaOfString(binding.edtMainMoneyIncome.getText().toString());
        binding.imgCalendarIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);
                                SimpleDateFormat dateFormatInsert = new SimpleDateFormat(INSERT_SHOWING_FORMAT);
                                binding.tvDateIncome.setText(dateFormatInsert.format(calendar.getTime()));
                                SimpleDateFormat dbFormatDate = new SimpleDateFormat(DATE_FORMAT);
                                dateInsert = dbFormatDate.format(calendar.getTime());
                            }
                        };
                        new TimePickerDialog(getContext(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
                    }
                };
                new DatePickerDialog(getContext(),listener,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        binding.imgLocationIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSuggestion();
            }
        });
        binding.btnSaveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtLocationIncome.getText().toString().trim().equals("")){
                    binding.edtLocationIncome.setError("Please enter the location field or tap the button location");
                    return;
                }
                if (binding.edtMainMoneyIncome.getText().toString().trim().equals("")){
                    binding.edtMainMoneyIncome.setError("Please enter the income cash");
                    return;
                }
                period = binding.boxPeriodIncome.isChecked();
                Money money = new Money();
                money.setId(tableOrganization.maxIdDB());
                money.setTag(Annotation.typesOfRecording[1]);
                money.setDate(dateInsert);
                money.setActualCost(Long.parseLong(binding.edtMainMoneyIncome.getText().toString().replaceAll(",","").trim()));
                money.setCategory("Income");
                money.setDetail(binding.edtDetailIncome.getText().toString().trim().equals("")?"empty":binding.edtDetailIncome.getText().toString().trim());
                money.setLocation(binding.edtLocationIncome.getText().toString().trim());
                money.setUsePeriod(period);
                tableOrganization.addMoneyNote(money);
                startActivity(new Intent(getActivity(), ModifyActivity.class));
            }
        });
        binding.btnBackInsertIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ModifyActivity.class));
            }
        });
    }

    private void openDialogSuggestion() {
        DialogLocationSuggest dialogLocationSuggest = new DialogLocationSuggest();
        dialogLocationSuggest.setListener(this);
        dialogLocationSuggest.show(getActivity().getSupportFragmentManager(),"dialog support");
    }

    @Override
    public void enterLocation(String locationSuggested) {
        binding.edtLocationIncome.setText(locationSuggested);
    }
}