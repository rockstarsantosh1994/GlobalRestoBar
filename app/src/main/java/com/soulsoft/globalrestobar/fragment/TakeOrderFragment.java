package com.soulsoft.globalrestobar.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.soulsoft.globalrestobar.BaseFragment;
import com.soulsoft.globalrestobar.R;
import com.soulsoft.globalrestobar.activity.LoginActivity;
import com.soulsoft.globalrestobar.adapter.GetAllCaptainAdapter;
import com.soulsoft.globalrestobar.adapter.GetMenuAdapter;
import com.soulsoft.globalrestobar.adapter.GetTableAdapter;
import com.soulsoft.globalrestobar.model.getcaptain.GetAllCaptainBO;
import com.soulsoft.globalrestobar.model.menucard.MenuDataBO;
import com.soulsoft.globalrestobar.model.table.GetTableDataBO;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import io.paperdb.Paper;

public class TakeOrderFragment extends BaseFragment {

    @BindView(R.id.act_tableno)
    AutoCompleteTextView actTableNo;
    @BindView(R.id.act_menu)
    AutoCompleteTextView actMenu;

    private GetMenuAdapter getMenuAdapter;
    private ArrayList<MenuDataBO> menuDataBOArrayList=new ArrayList<>();
    private GetTableAdapter getTableAdapter;
    private ArrayList<GetTableDataBO> getTableDataBOArrayList=new ArrayList<>();

    public TakeOrderFragment( ) {
        // Required empty public constructor
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.fragment_take_order;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //basic intialisation...
        initViews();

        //attach data to table and menu
        setData();
    }

    private void initViews(){

    }

    private void setData(){
        menuDataBOArrayList.addAll(Paper.book().read("menucard"));
        getMenuAdapter = new GetMenuAdapter(mContext, menuDataBOArrayList);
        actMenu.setAdapter(getMenuAdapter);
        actMenu.setThreshold(1);

        actMenu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterMenu(s.toString());
            }
        });

        getTableDataBOArrayList.addAll(Paper.book().read("tabledata"));
        getTableAdapter = new GetTableAdapter(mContext, getTableDataBOArrayList);
        actTableNo.setAdapter(getTableAdapter);
        actTableNo.setThreshold(1);

        actTableNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterTable(s.toString());
            }
        });
    }

    private void filterMenu(String text) {
        ArrayList<MenuDataBO> filteredList = new ArrayList<>();
        for (MenuDataBO item : menuDataBOArrayList) {
            if (item.getITEMNAME().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        getMenuAdapter.updateData(mContext, filteredList);
    }

    private void filterTable(String text) {
        ArrayList<GetTableDataBO> filteredList = new ArrayList<>();
        for (GetTableDataBO item : getTableDataBOArrayList) {
            if (item.getTABLENO().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        getTableAdapter.updateData(mContext, filteredList);
    }
}