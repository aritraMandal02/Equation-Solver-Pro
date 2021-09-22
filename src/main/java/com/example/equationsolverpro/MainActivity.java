package com.example.equationsolverpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView web;
    EditText et;
    Button button;
    String APP_ID = "4WYAA2-YVWK4T7RPR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.webView);
        et = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);

        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        //check Internet connection
        if(internet||wifi)  //your code
        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult();
                }
            });
        }

        else{
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.title)
                    .setTitle("No internet connection")
                    .setMessage("Please turn on mobile data")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //code for exit
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    })
                    .show();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setResult(){
        Toast.makeText(this, "Fetching data from Wolfram|Alpha. This may take some while. please wait.", Toast.LENGTH_SHORT).show();
        String query = et.getText().toString().replaceAll("%","%25");
        query = query.replaceAll("\\+","%2B");
        query = query.replaceAll("\\$","%24");
        String URL = "https://api.wolframalpha.com/v1/simple?appid=" + APP_ID + "&i=" + query;
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        web.loadUrl(URL);
    }

}