package com.supply.chain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

//implementing onclicklistener
public class Barcode extends AppCompatActivity {

    //View Objects
    private Button QR;
    private static final String TAG = "Response";
    JSONObject ob = new JSONObject();
    JSONObject ob2 = new JSONObject();


    String url;
    private EditText ip;
    String text1;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);


        //View objects
        QR = (Button) findViewById(R.id.QR);
        ip = findViewById(R.id.ip);


        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "http://" + ip.getText();

                qrScan.initiateScan();


            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data

                try {
                    //converting the data to json
                    ob.put("showRecord", "showRecord");
                    ob.put("record_id", result.getContents());


                    //setting values to textviews
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
                getinfo(ob);


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getinfo(final JSONObject message) {
        OkHttpClient clientCoinPrice = new OkHttpClient();
        Request requestCoinPrice = new Request.Builder().url(url).build();


        WebSocketListener webSocketListenerCoinPrice = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                webSocket.send(String.valueOf(message));
                Log.e(TAG, "onOpen");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.e(TAG, "MESSAGE: " + text);
                text1 = text;
                writeToFile(text1);


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
        Intent int1 = new Intent(this, Record.class);
        startActivity(int1);


    }

    private void writeToFile(String content) {
        System.out.println("+++++++" + content);
        FileOutputStream f = null;
        try {
            f = openFileOutput("mytext.txt", MODE_PRIVATE);
            f.write(content.getBytes());
            Toast.makeText(this, "save to" + getFilesDir() + "/" + "record.txt", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}





