package com.supply.chain;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import java.text.MessageFormat;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    private static final String TAG = "Response";

    private Button Login;
    private EditText user;
    private EditText password;
    private RadioButton role,role2;
    private RadioGroup rg;
    private EditText ip;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ip=findViewById(R.id.ip);
        Login = findViewById(R.id.login);
        user = (EditText)findViewById(R.id.user);
        password = findViewById(R.id.password);
        rg=findViewById(R.id.rg1);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              url="http://"+ip.getText().toString();
                final JSONObject a = new JSONObject();
                try {
                    a.put("authenticate","authenticate");
                    a.put("name", user.getText().toString());
                    a.put("password", password.getText().toString());
                } catch(JSONException e){
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                login(a);


            }

        });

    }

    public void login(final JSONObject product) {
        OkHttpClient clientCoinPrice = new OkHttpClient();
        Request requestCoinPrice = new Request.Builder().url(url).build();



        WebSocketListener webSocketListenerCoinPrice = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                webSocket.send(String.valueOf(product));
                Log.e(TAG, "onOpen");

            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.e(TAG, "MESSAGE: " + text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Log.e(TAG, "MESSAGE: " + bytes.hex());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(1000, null);
                webSocket.cancel();
                Log.e(TAG, "CLOSE: " + code + " " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                //TODO: stuff
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                //TODO: stuff
            }
        };



        clientCoinPrice.newWebSocket(requestCoinPrice, webSocketListenerCoinPrice);
        clientCoinPrice.dispatcher().executorService().shutdown();
        switch(rg.getCheckedRadioButtonId()){
            case R.id.Shop:





                Intent int2=new Intent(this,Information.class);
                int2.putExtra("user",user.getText().toString());
                int2.putExtra("password",password.getText().toString());
                int2.putExtra("ip",ip.getText().toString());

                startActivity(int2);
            break;
            case R.id.Store:
                Intent int3=new Intent(this,Material.class);
                int3.putExtra("user",user.getText().toString());
                int3.putExtra("password",password.getText().toString());
                int3.putExtra("ip",ip.getText().toString());

                startActivity(int3);
            break;
        }




    }

}
