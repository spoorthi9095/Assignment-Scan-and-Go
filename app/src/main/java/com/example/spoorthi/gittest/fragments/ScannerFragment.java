package com.example.spoorthi.gittest.fragments;


import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.spoorthi.gittest.R;
import com.example.spoorthi.gittest.activities.UserHomeActivity;
import com.example.spoorthi.gittest.activities.ViewCartActivity;
import com.example.spoorthi.gittest.database.CartItem;
import com.example.spoorthi.gittest.database.CartViewModel;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment {


    View scannFragView;
    Context context;

    private CodeScanner mCodeScanner;

    CartViewModel cartViewModel;

    int count = 0;

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        scannFragView = inflater.inflate(R.layout.fragment_scanner, container, false);

        initViews();

        initListeners();

        return scannFragView;
    }

    private void initViews()
    {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        initilizeScanner();
    }

    private void initListeners()
    {

    }

    private void initilizeScanner()
    {
        CodeScannerView scannerView = scannFragView.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(context, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        Toast.makeText(context, result.getText(), Toast.LENGTH_SHORT).show();

                        count++;
                        updateMenuValue();
                        /*CartItem cartItem = new CartItem();
                        cartItem.setAmount(100);
                        cartItem.setProductID(count);
                        cartItem.setProductName("Selected Item "+count);

                        cartViewModel.insert(cartItem);*/
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }


    private void updateMenuValue()
    {
        Log.e("count ",""+count);
        ((UserHomeActivity)getActivity()).setMenuValues();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}
