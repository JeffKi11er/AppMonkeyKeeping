package com.example.appmonkeykeeping.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.adapter.AdapterSupport;
import com.example.appmonkeykeeping.center.DatabaseSystem;
import com.example.appmonkeykeeping.model.Money;
import com.example.appmonkeykeeping.remote.ItemClickListener;
import com.example.appmonkeykeeping.remote.LocationInserting;

import java.util.ArrayList;
import java.util.List;

public class DialogLocationSuggest extends AppCompatDialogFragment implements ItemClickListener {
    private RecyclerView rclListLocation;
    private AdapterSupport supportAdapter;
    private ArrayList<String>location;
    private LocationInserting listener;
    private DatabaseSystem systemDb;

    public void setListener(LocationInserting listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_support_location,null);
        builder.setView(view)
                .setTitle("Location")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        systemDb = DatabaseSystem.getInstance();
        systemDb.realmInitialize();
        List<Money>data = systemDb.readListData();
        location = new ArrayList<>();
        for (Money money:data) {
            if(!location.contains(money.getLocation())){
                location.add(money.getLocation());
            }
        }
        rclListLocation = view.findViewById(R.id.rcl_support);
        supportAdapter = new AdapterSupport(getContext(),location);
        supportAdapter.setListener(this);
        rclListLocation.setAdapter(supportAdapter);
        return builder.create();
    }

    @Override
    public void onClick(int position) {
        if(listener!=null){
            listener.enterLocation(location.get(position));
            dismiss();
        }
    }

    @Override
    public void onLongClick(int position) {
    }
}
