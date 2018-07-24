package com.example.spoorthi.gittest.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.spoorthi.gittest.R;
import com.example.spoorthi.gittest.viewmodel.UserPhoneViewModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.spoorthi.gittest.general.KeyList.PHONE_NUM;

public class MainActivity extends AppCompatActivity
{
    EditText mobileNum;

    Button continueBtn;

    private UserPhoneViewModel userPhoneViewModel;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLightStatusBar(getWindow().getDecorView(),this);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        initViews();
        initListeners();
    }

    private void initViews()
    {
        mobileNum = (EditText)findViewById(R.id.et_mobile_num);

        continueBtn = (Button)findViewById(R.id.btn_continue);

        userPhoneViewModel = ViewModelProviders.of(this).get(UserPhoneViewModel.class);

        displayPhoneNum(userPhoneViewModel.getUserPhoneNum());

    }

    private void displayPhoneNum(String userPhoneNum)
    {
        if(!TextUtils.isEmpty(userPhoneNum))
        {
            mobileNum.setText(userPhoneNum);
        }
    }

    private void initListeners()
    {
        mobileNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNum = mobileNum.getText().toString();
                if(!TextUtils.isEmpty(phoneNum))
                {
                    userPhoneViewModel.setUserPhoneNum(phoneNum);
//                    displayPhoneNum(phoneNum);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNum = mobileNum.getText().toString();
                if(!TextUtils.isEmpty(phoneNum))
                {
                    if(phoneNum.length()<10)
                    {
                        mobileNum.setError("Please enter a valid mobile number.");
                        mobileNum.requestFocus();
                    }
                    else {
                        goToOTPScreen(phoneNum);
                    }
                }
                else {
                    mobileNum.setError("Mobile number is required.");
                    mobileNum.requestFocus();
                }

            }
        });
    }

    private void goToOTPScreen(String phoneNum)
    {
        Intent intent = new Intent(this,OTPActivity.class);
        intent.putExtra(PHONE_NUM,phoneNum);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Log.e("user ","user is present "+user);
            Intent intent = new Intent(this,UserHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Log.e("user ","user is not present "+user);
        }

    }

    public static void setLightStatusBar(View view, Activity activity){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.parseColor("#f48d2a"));
        }
    }
}
