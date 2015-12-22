package com.yoyoshop.myrsademo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wenke.rsaencryption.RSAEncryption;

public class MainActivity extends Activity {

    private Button send;

    private String url = "http://192.168.1.5:8080/TestRSA/servlet/RSAServlet";
    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMQKGp7zSUktNOQkPdfcvJ3sWP7O46ENmSO4s0+iDdhHbR7klyt2oj0gXM1sAJj8ZBWFudu8GpiDKqXwN88IZemfT5c1LEQshTD1WHfZC6EY2vf9MGl5yGtV5WkU8vDJpg0STUJrCAcJ5Cp/m5qqJqgEM5Op6jHzwtzWV3syx+CBAgMBAAECgYEApSzqPzE3d3uqi+tpXB71oY5JcfB55PIjLPDrzFX7mlacP6JVKN7dVemVp9OvMTe/UE8LSXRVaFlkLsqXC07FJjhuwFXHPdnUf5sanLLdnzt3Mc8vMgUamGJl+er0wdzxM1kPTh0Tmq+DSlu5TlopAHd5IqF3DYiORIen3xIwp0ECQQDj6GFaXWzWAu5oUq6j1msTRV3mRZnx8Amxt1ssYM0+JLf6QYmpkGFqiQOhHkMgVUwRFqJC8A9EVR1eqabcBXbpAkEA3DQfLVr94vsIWL6+VrFcPJW9Xk28CNY6Xnvkin815o2Q0JUHIIIod1eVKCiYDUzZAYAsW0gefJ49sJ4YiRJN2QJAKuxeQX2s/NWKfz1rRNIiUnvTBoZ/SvCxcrYcxsvoe9bAi7KCMdxObJknhNXFQLav39wKbV73ESCSqnx7P58L2QJABmhR2+0A5EDvvj1WpokkqPKmfv7+ELfDHQq33LvU4q+N3jPn8C85ZDedNHzx57kru1pyb/mKQZANNX10M1DgCQJBAMKn0lExQH2GrkjeWgGVpPZkp0YC+ztNjaUMJmY5g0INUlDgqTWFNftxe8ROvt7JtUvlgtKCXdXQrKaEnpebeUQ=";
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEChqe80lJLTTkJD3X3Lyd7Fj+zuOhDZkjuLNPog3YR20e5JcrdqI9IFzNbACY/GQVhbnbvBqYgyql8DfPCGXpn0+XNSxELIUw9Vh32QuhGNr3/TBpechrVeVpFPLwyaYNEk1CawgHCeQqf5uaqiaoBDOTqeox88Lc1ld7MsfggQIDAQAB";
    private String encodedName;
    private String encodedPassword;
    private EditText nameEdit;
    private EditText passwordEdit;
    private TextView tv;
    private TextView response;
    private String nameSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("RSA", MyApplication.publicKey + "");
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        tv = (TextView) findViewById(R.id.tv);
        response = (TextView) findViewById(R.id.response);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameData = nameEdit.getText().toString().trim();
                String passwordData = passwordEdit.getText().toString().trim();
//                String data ="洛阳亲友如相问，一片冰心在玉壶";
                try {
                    /*encodedName = RSAEncryption.encryptByPublicKey(nameData, publicKey);
                    encodedPassword = RSAEncryption.encryptByPublicKey(passwordData, publicKey);*/


                    encodedName = RSAEncryption.encryptByPublicKey("音乐", publicKey);
                    nameSign = RSAEncryption.signByPrivateKey(encodedName.getBytes(), privateKey);
                    encodedPassword = RSAEncryption.encryptByPublicKey("速写", publicKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                RequestParams requestParams = new RequestParams();
                requestParams.put("name", encodedName);
                requestParams.put("nameSign",nameSign);
                requestParams.put("password", encodedPassword);
                asyncHttpClient.post(url, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {

                        try {
                            tv.setText(new String(bytes));
                            String serverData = RSAEncryption.decryptByPublicKey(new String(bytes, "utf-8"), publicKey);
                            response.setText(serverData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            }
        });
    }
}
