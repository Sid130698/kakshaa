package com.example.tuitionbuddy01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class ViewPDF extends AppCompatActivity {
    WebView webViewPDF;
    String filename,fileURL;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f);
        intializeField();
        filename=getIntent().getStringExtra("filename");
        fileURL=getIntent().getStringExtra("fileURL");
        webViewPDF.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        String url="";
        try {
          url=  URLEncoder.encode(fileURL,"UTF-8");

        }
        catch(Exception e){

        }
        webViewPDF.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);


    }


    private void intializeField() {
        webViewPDF=(WebView)findViewById(R.id.webViewPDF);
        webViewPDF.getSettings().setJavaScriptEnabled(true);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Opening File!");
        progressDialog.setMessage("please wait!.....");
        progressDialog.setCanceledOnTouchOutside(true);
    }
}