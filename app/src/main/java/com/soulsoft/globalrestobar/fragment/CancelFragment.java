package com.soulsoft.globalrestobar.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.soulsoft.globalrestobar.BaseFragment;
import com.soulsoft.globalrestobar.R;
import com.soulsoft.globalrestobar.activity.DashBoardActivity;

public class CancelFragment extends BaseFragment {

    public CancelFragment( ) {
        // Required empty public constructor
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.fragment_cancel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //basic intialistiaon...
        initViews();
    }

    private void initViews(){
        DashBoardActivity.tvTitle.setText(getResources().getString(R.string.cancel_order1));
    }
}