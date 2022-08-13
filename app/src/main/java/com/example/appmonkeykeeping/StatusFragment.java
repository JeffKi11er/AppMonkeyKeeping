package com.example.appmonkeykeeping;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.adapter.NoteAdapter;
import com.example.appmonkeykeeping.center.DatabaseSystem;
import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.databinding.FragmentStatusBinding;
import com.example.appmonkeykeeping.model.Money;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StatusFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentStatusBinding binding;
    private String mParam1;
    private String mParam2;
    private DatabaseSystem dbSystem;
    private NoteAdapter noteAdapter;
    private ArrayList<Money>monkeyMoney;
    private NavController navController;
    private List<Money> moneyModels;
    private TableOrganization tableOrganization;
    public StatusFragment() {
    }
    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbSystem = DatabaseSystem.getInstance();
        dbSystem.realmInitialize();
        tableOrganization = TableOrganization.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        monkeyMoney = dbSystem.readListData();
        if(monkeyMoney.size()>0){
            monkeyMoney = tableOrganization.newestSort(monkeyMoney);
        }
        noteAdapter = new NoteAdapter(getContext(),monkeyMoney);
        binding.rclNote.setAdapter(noteAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(binding.rclNote);
        binding.btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(v);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.dashBoardFragment,true).build();
                navController.navigate(R.id.action_statusFragment_to_dashBoardFragment,null,navOptions);
            }
        });
    }
    private void dataChanged(){
        monkeyMoney = new ArrayList<>();
        monkeyMoney = dbSystem.readListData();
        if(monkeyMoney.size()>0){
            monkeyMoney = tableOrganization.newestSort(monkeyMoney);
        }
        noteAdapter.updateList(monkeyMoney);
    }
    Money deletedItem = null;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction){
                case ItemTouchHelper.LEFT:
                    int position = viewHolder.getBindingAdapterPosition();
                    Money moneyInProcess = monkeyMoney.get(position);
                    long idChanged = moneyInProcess.getId();
                    deletedItem = new Money();
                    deletedItem.setId(dbSystem.getMaxId());
                    deletedItem.setDate(moneyInProcess.getDate());
                    deletedItem.setActualCost(moneyInProcess.getActualCost());
                    deletedItem.setCategory(moneyInProcess.getCategory());
                    deletedItem.setDetail(moneyInProcess.getDetail());
                    deletedItem.setLocation(moneyInProcess.getLocation());
                    deletedItem.setUsePeriod(moneyInProcess.isUsePeriod());
                    dbSystem.delete(idChanged);
                    dataChanged();
                    Snackbar.make(binding.rclNote,deletedItem.getDate(),Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           dbSystem.insert(deletedItem);
                           dataChanged();
                        }
                    }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    navController = Navigation.findNavController(getView());
                    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.mainAmountFragment,true).build();
                    int positionEdit = viewHolder.getBindingAdapterPosition();
                    Money money = monkeyMoney.get(positionEdit);
                    long findId = money.getId();
                    StatusFragmentDirections.ActionStatusFragmentToMainAmountFragment action = StatusFragmentDirections.actionStatusFragmentToMainAmountFragment();
                    action.setMessageFindingId(String.valueOf(findId));
                    navController.navigate(action,navOptions);
                    break;
            }
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.delete))
                    .addSwipeLeftActionIcon(R.drawable.ic_remove)
                    .addSwipeLeftLabel("Delete")
                    .addSwipeRightLabel("Edit")
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.edit))
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };

}