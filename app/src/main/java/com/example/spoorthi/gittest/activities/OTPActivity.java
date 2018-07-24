package com.example.spoorthi.gittest.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spoorthi.gittest.R;
import com.example.spoorthi.gittest.viewmodel.OTPViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.example.spoorthi.gittest.general.KeyList.CODE_RECIEVED;
import static com.example.spoorthi.gittest.general.KeyList.PHONE_NUM;

public class OTPActivity extends AppCompatActivity {

    EditText otp;
    TextView enterOTPLabel,wrongNum,resendOTP;
    Button doneBtn;

    String phoneNum = "";
    String codeSent = "";

    OTPViewModel otpViewModel;

    FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
            Log.e("codeSent ",""+codeSent);
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            Log.e("Varification success",""+phoneAuthCredential);
            signInWithPhoneAuthCredential(phoneAuthCredential);
            /*phoneAuthCredential.getSmsCode();*/

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }
    };
    private String TAG = OTPActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

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
        firebaseAuth = FirebaseAuth.getInstance();

        otp = (EditText)findViewById(R.id.et_otp);

        enterOTPLabel = (TextView)findViewById(R.id.tv_enter_otp_label);
        wrongNum = (TextView)findViewById(R.id.tv_wrong_num);
        resendOTP = (TextView)findViewById(R.id.tv_resend);

        doneBtn = (Button)findViewById(R.id.btn_done);

        phoneNum = getIntent().getStringExtra(PHONE_NUM);

        enterOTPLabel.setText("Enter the OTP sent on \n +91 "+phoneNum);

        otpViewModel = ViewModelProviders.of(this).get(OTPViewModel.class);

        setOtpValue(otpViewModel.getOtp());

        sentVerificationCode();
    }

    private void setOtpValue(String returnedOtp)
    {
        if(!TextUtils.isEmpty(returnedOtp))
        {
            otp.setText(returnedOtp);
        }
    }

    private void initListeners()
    {
        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String otpAdded = otp.getText().toString();
                if(!TextUtils.isEmpty(otpAdded))
                {
                    otpViewModel.setOtp(otpAdded);
//                    displayPhoneNum(phoneNum);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        wrongNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentVerificationCode();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean failFlag = false;

                String returnedOTP;

                returnedOTP = otp.getText().toString();

                if(TextUtils.isEmpty(returnedOTP))
                {
                    failFlag = true;
                    showErrorMessage("Enter a valid OTP");
                }

                if(failFlag == false)
                {
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(codeSent,returnedOTP);
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }
            }
        });
    }

    private void sentVerificationCode()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNum,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    private void showErrorMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showErrorMessage("Signed in successfully");
                            openUseHome();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                showErrorMessage("Please enter a valid OTP");
                            }
                        }
                    }
                });
    }

    private void openUseHome()
    {
        Intent intent = new Intent(this,EditProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
