package com.soulsoft.globalrestobar.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.soulsoft.globalrestobar.BaseFragment;
import com.soulsoft.globalrestobar.R;

public class TakeOrderFragment extends BaseFragment {

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
    }

    private void initViews(){

    }
}