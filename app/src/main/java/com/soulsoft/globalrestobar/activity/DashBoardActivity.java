package com.soulsoft.globalrestobar.activity;

import android.os.Bundle;
import com.soulsoft.globalrestobar.BaseActivity;
import com.soulsoft.globalrestobar.R;

public class DashBoardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_dash_board;
    }
}