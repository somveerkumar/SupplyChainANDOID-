package com.supply.chain;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Response";
    private Button  Projects;
    private String gurl;
    String text1;
    String text;
    JSONObject project_list = new JSONObject();
    Handler handler = new Handler();
    ImageButton page;
    ImageButton scan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page = (ImageButton) findViewById(R.id.page1);
        scan = (ImageButton)findViewById(R.id.scan);
        Projects = findViewById(R.id.project_list);
        gurl = "http://34.93.146.176";


        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });


        Projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    project_list.put("project_list", "list");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(project_list);
                new dataFetch().execute();


            }
        });
    }


    private void scan() {
        Intent intent = new Intent(this, Barcode.class);
        startActivity(intent);
    }

    private void login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }


    public class dataFetch extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        @Override
        protected String doInBackground(String... params) {

            OkHttpClient clientCoinPrice = new OkHttpClient();
            Request requestCoinPrice = new Request.Builder().url(gurl).build();


            WebSocketListener webSocketListenerCoinPrice = new WebSocketListener() {
                @Override
                public void onOpen(WebSocket webSocket, Response response) {
                    webSocket.send(String.valueOf(project_list));
                    Log.e(TAG, "onOpen");
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
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


            return text1;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            final Intent intent = new Intent(MainActivity.this, Project_list.class);

            intent.putExtra("data", result);
            System.out.println(result);
            startActivity(intent);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(intent);

                }
            },800);



        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void writeToFile(String content){
        System.out.println("+++++++"+content);
        FileOutputStream f=null;
        try {
            f=openFileOutput("mytext.txt",MODE_PRIVATE);
            f.write(content.getBytes());
            Toast.makeText(this,"save to" + getFilesDir()+"/"+"mytext.txt",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(f!=null){
                try{
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
















