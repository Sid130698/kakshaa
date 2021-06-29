package com.example.tuitionbuddy01;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class CustomLoadingScreen extends Dialog {
    public CustomLoadingScreen(@NonNull Context context) {
        super(context);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        getWindow().setAttributes(params);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        setTitle(null);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnCancelListener(null);
        View view= LayoutInflater.from(context).inflate(R.layout.custom_loading_screen,null);
        setContentView(view);
    }
}
