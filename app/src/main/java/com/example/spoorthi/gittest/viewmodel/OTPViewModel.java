package com.example.spoorthi.gittest.viewmodel;

import android.arch.lifecycle.ViewModel;

public class OTPViewModel extends ViewModel
{
    String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
