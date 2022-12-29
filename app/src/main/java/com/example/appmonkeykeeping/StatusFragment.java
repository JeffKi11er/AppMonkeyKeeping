package com.example.appmonkeykeeping;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmonkeykeeping.adapter.NoteAdapter;
import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.databinding.FragmentStatusBinding;
import com.example.appmonkeykeeping.dialog.DialogEdit;
import com.example.appmonkeykeeping.model.Money;
import com.example.appmonkeykeeping.remote.GotInfoEditProgress;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StatusFragment extends Fragment implements GotInfoEditProgress {
    private FragmentStatusBinding binding;
    private NoteAdapter noteAdapter;
    private ArrayList<Money>monkeyMoney;
    private TableOrganization tableOrganization;
    public StatusFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    private int showListLength(ArrayList<Money>monies){
        if(monies.size()>=10){
            return 10;
        }
        return monies.size();
    }
    private void updateList(){
        ArrayList<Money>moneyShowList = tableOrganization.showList();
        for (int i = 0; i < showListLength(moneyShowList); i++) {
            monkeyMoney.add(moneyShowList.get(i));
        }
    }
    private void init() {
        monkeyMoney = new ArrayList<>();
        updateList();
        noteAdapter = new NoteAdapter(getContext(),monkeyMoney);
        binding.rclNote.setAdapter(noteAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(binding.rclNote);
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int sizeNow = monkeyMoney.size();
                int sizeVirtual = tableOrganization.showList().size();
                if(sizeNow==sizeVirtual){
                    binding.refreshLayout.setRefreshing(false);
                    return;
                }
                int numberAdd = Math.min((sizeVirtual - sizeNow), 10);
                for (int i = sizeNow; i < sizeNow+numberAdd; i++) {
                    monkeyMoney.add(tableOrganization.showList().get(i));
                }
                noteAdapter.updateList(monkeyMoney);
                binding.refreshLayout.setRefreshing(false);
            }
        });
    }
    private void dataChanged(){
        updateList();
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
                    deletedItem.setId(tableOrganization.maxIdDB());
                    deletedItem.setDate(moneyInProcess.getDate());
                    deletedItem.setActualCost(moneyInProcess.getActualCost());
                    deletedItem.setCategory(moneyInProcess.getCategory());
                    deletedItem.setDetail(moneyInProcess.getDetail());
                    deletedItem.setLocation(moneyInProcess.getLocation());
                    deletedItem.setUsePeriod(moneyInProcess.isUsePeriod());
                    tableOrganization.removeMoneyNote(idChanged);
                    dataChanged();
                    Snackbar.make(binding.rclNote,deletedItem.getDate(),Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           tableOrganization.addMoneyNote(deletedItem);
                           dataChanged();
                        }
                    }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    int positionCheck = viewHolder.getBindingAdapterPosition();
                    Money moneyEditing = monkeyMoney.get(positionCheck);
                    openEditDialog(moneyEditing);
                    break;
            }
        }
        private void openEditDialog(Money money) {
            DialogEdit editDialogFragment = new DialogEdit(money);
            editDialogFragment.setListener(StatusFragment.this);
            editDialogFragment.show(getActivity().getSupportFragmentManager(),"dialog edit");
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

    @Override
    public void updateFinish() {
        dataChanged();
    }
}