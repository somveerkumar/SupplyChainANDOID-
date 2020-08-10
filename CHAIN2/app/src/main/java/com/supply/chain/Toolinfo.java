package com.supply.chain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


public class Toolinfo extends AppCompatActivity {
    private static final String TAG = "Response";
    private EditText record1,type1,desc,parent1,quantity1;
    private EditText Name1,Roll,Department,date1,HOD,Detail;
    private Button CreateRecord1;
    private String ip,user,password;
    String gurl ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolinfo);
        Intent newint=getIntent();
        user=newint.getStringExtra("user");
        password=newint.getStringExtra("password");
        ip=newint.getStringExtra("ip");
        record1=findViewById(R.id.record1);
        type1=findViewById(R.id.type1);
        desc=findViewById(R.id.desc);
        parent1=findViewById(R.id.parent1);
        quantity1=findViewById(R.id.quantity1);
        Name1=findViewById(R.id.Name1);
        Roll=findViewById(R.id.Roll);
        Department=findViewById(R.id.Department);
        date1=findViewById(R.id.date);
        HOD=findViewById(R.id.HOD);
        Detail=findViewById(R.id.Detail);
        CreateRecord1=findViewById(R.id.create1);
        CreateRecord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gurl="http://"+ip;
                final JSONObject a = new JSONObject();
                JSONObject b=new JSONObject();
                try {
                    //a.put("authenticate","authenticate");
                    a.put("create","create");
                    a.put("authenticate","authenticate");
                    a.put("toolinfo","toolinfo");
                    a.put("name", user);
                    a.put("password", password);
                    a.put("recordid", record1.getText().toString());
                    b.put("type", type1.getText().toString());
                    b.put("description",desc.getText().toString());
                    b.put("parent_id", parent1.getText().toString());
                    b.put("quantity", quantity1.getText().toString());
                    b.put("Name", Name1.getText().toString());
                    b.put("Roll no", Roll.getText().toString());
                    b.put("Date of return committed",date1.getText().toString());
                    b.put("Department",Department.getText().toString());
                    b.put("Details", Detail.getText().toString());
                    b.put("Guide-HOD approval", HOD.getText().toString());
                    a.put("other_fields",b);
                    a.put("new","");

                } catch(JSONException e){
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                sendinfo(a);
            }
        });

    }
    public void sendinfo(final JSONObject product) {
        OkHttpClient clientCoinPrice = new OkHttpClient();
        Request requestCoinPrice = new Request.Builder().url(gurl).build();
        Intent intent=new Intent(this,Information.class);
        startActivity(intent);

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

    }
}


