package com.yoyoshop.myrsademo;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;

import com.wenke.rsaencryption.RSAEncryption;

/**
 * Created by wenke on 2015/12/18.
 */
public class MyApplication extends Application {
    public static String privateKey ;
    public static String publicKey ;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("RSA", "application");

        initRSAKey();

        SystemClock.sleep(5000);
        Log.e("RSA", publicKey);
        Log.e("RSA", "applicationOver");

    }
    private void initRSAKey() {
        try {
        Log.e("RSA", "init");
            privateKey = RSAEncryption.getKeyFromFile("e:\\privateKey.txt");
            publicKey = RSAEncryption.getKeyFromFile("e:\\publicKey.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
