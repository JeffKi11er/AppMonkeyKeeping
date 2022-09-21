package com.example.appmonkeykeeping.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.model.Money;
import com.example.appmonkeykeeping.remote.GotInfoEditProgress;

public class DialogEdit extends AppCompatDialogFragment{
    private Money money;
    private GotInfoEditProgress listener;
    public DialogEdit(Money money){
        this.money = money;
    }
    private TableOrganization tableOrganization;

    public void setListener(GotInfoEditProgress listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_dialog,null);
        builder.setView(view);
        tableOrganization = TableOrganization.getInstance();
        tableOrganization.initializeDatabase();
        EditText edtDate = view.findViewById(R.id.edt_edit_date);
        edtDate.setText(money.getDate());
        EditText edtLocation = view.findViewById(R.id.edt_edit_location);
        edtLocation.setText(money.getLocation());
        EditText edtMoney = view.findViewById(R.id.edt_edit_money);
        edtMoney.setText(String.valueOf(money.getActualCost()));
        EditText edtTag = view.findViewById(R.id.edt_edit_tag);
        edtTag.setText(money.getCategory());
        EditText edtComment = view.findViewById(R.id.edt_edit_comment);
        edtComment.setText(money.getDetail());
        Button btnVerify = view.findViewById(R.id.btn_edit_verify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOrganization.updateMoneyNote(money);
                if (listener!=null){
                    Money moneyEdited = new Money();
                    moneyEdited.setId(money.getId());
                    moneyEdited.setTag(money.getTag());
                    moneyEdited.setDiff(money.getDiff());
                    moneyEdited.setProjectCost(money.getProjectCost());
                    moneyEdited.setDate(edtDate.getText().toString().trim());
                    moneyEdited.setLocation(edtLocation.getText().toString());
                    moneyEdited.setActualCost(Long.parseLong(edtMoney.getText().toString().trim()));
                    moneyEdited.setCategory(edtTag.getText().toString().trim());
                    moneyEdited.setDetail(edtComment.getText().toString().trim());
                    tableOrganization.updateMoneyNote(moneyEdited);
                    listener.updateFinish();
                }
                dismiss();
            }
        });
        TextView btnReturn = view.findViewById(R.id.tv_edit_cancel);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.updateFinish();
                }
                dismiss();
            }
        });
        return builder.create();
    }
}
