package com.soulsoft.globalrestobar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;

import com.soulsoft.globalrestobar.BaseActivity;
import com.soulsoft.globalrestobar.R;
import com.soulsoft.globalrestobar.utility.CommonMethods;

import javax.security.auth.login.LoginException;

import butterknife.BindView;

public class IPAddressActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_ipaddress)
    EditText etIpAddress;
    @BindView(R.id.btn_validateIP)
    AppCompatButton btnValidateIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //basic intialisation..
        initViews();
    }

    @Override
    protected int getActivityLayout( ) {
        return R.layout.activity_i_p_address;
    }

    private void initViews(){
        btnValidateIp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_validateIP){
            if(isIPValidated()){
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

    public boolean isIPValidated(){
        if(etIpAddress.getText().toString().equals("")){
            etIpAddress.setError("Please enter IPAddress!");
            etIpAddress.requestFocus();
            etIpAddress.setFocusable(true);
            return false;
        }

        else if(!CommonMethods.IPAdressValidator(etIpAddress.getText().toString())){
            etIpAddress.setError("Invalid IPAddress!");
            etIpAddress.requestFocus();
            etIpAddress.setFocusable(true);
            return false;
        }
        return true;
    }
}