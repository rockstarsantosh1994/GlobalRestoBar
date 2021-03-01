package com.soulsoft.globalrestobar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;

import com.soulsoft.globalrestobar.BaseActivity;
import com.soulsoft.globalrestobar.R;
import com.soulsoft.globalrestobar.adapter.GetAllCaptainAdapter;
import com.soulsoft.globalrestobar.model.getcaptain.GetAllCaptain;

import java.util.ArrayList;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.act_waitername)
    AutoCompleteTextView actWaiterName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;

    GetAllCaptainAdapter getAllCaptainAdapter;

    private ArrayList<GetAllCaptain> getAllCaptainArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //basic intialisation...
        initViews();

    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_login;
    }

    private void initViews(){
        btnSubmit.setOnClickListener(this);

        getAllCaptainArrayList.add(new GetAllCaptain("Captain1"));
        getAllCaptainArrayList.add(new GetAllCaptain("Captain2"));
        getAllCaptainArrayList.add(new GetAllCaptain("Santosh"));
        getAllCaptainArrayList.add(new GetAllCaptain("Prasad"));
        getAllCaptainArrayList.add(new GetAllCaptain("Vikas"));
        getAllCaptainArrayList.add(new GetAllCaptain("Lalit"));

        getAllCaptainAdapter = new GetAllCaptainAdapter(LoginActivity.this,getAllCaptainArrayList);
        actWaiterName.setAdapter(getAllCaptainAdapter);
        actWaiterName.setThreshold(1);

        actWaiterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_submit){
            if(isValidated()){
                Intent intent = new Intent(mContext, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    }

    private void filter(String text) {
        ArrayList<GetAllCaptain> filteredList = new ArrayList<>();
        for (GetAllCaptain item : getAllCaptainArrayList) {
            if (item.getEmpName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        getAllCaptainAdapter.updateData(LoginActivity.this, filteredList);

    }

    public boolean isValidated() {
        if (actWaiterName.getText().toString().isEmpty()) {
            actWaiterName.setError("Please enter name!");
            actWaiterName.requestFocus();
            actWaiterName.setFocusable(true);
            return false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Please enter password!");
            etPassword.requestFocus();
            etPassword.setFocusable(true);
            return false;
        }
        return true;
    }
}