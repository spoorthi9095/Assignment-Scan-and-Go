package com.example.spoorthi.gittest.viewmodel;

import android.arch.lifecycle.ViewModel;

public class UserPhoneViewModel extends ViewModel
{
    String userPhoneNum;

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }
}
