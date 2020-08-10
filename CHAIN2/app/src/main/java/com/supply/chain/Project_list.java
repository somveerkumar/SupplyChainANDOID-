package com.supply.chain;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Project_list extends AppCompatActivity {
    String jsonString;
    ProgressBar progressbar;
    int i=0;
    Handler handler = new Handler();
    private static final String TAG = "Response";
    List<String> Projects = new ArrayList<String>();
    ListView list;
    TextView text;
    String text1;
    String gurl="http://34.93.146.176";
    ArrayList<String> arrayList = new ArrayList<String>();
    JSONObject a=new JSONObject();




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        jsonString = getIntent().getExtras().getString("data");
        text = findViewById(R.id.text2);
        progressbar=findViewById(R.id.progressBar);
        System.out.println("MESSAGE :__________++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(jsonString);
        int ch;

        list = findViewById(R.id.list);



        BufferedReader bufferedReader = null;
        try {
            FileInputStream is=new FileInputStream(getFilesDir()+"/"+"mytext.txt");
            DataInputStream data =new DataInputStream(is);

            bufferedReader = new BufferedReader(new InputStreamReader(data));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("bufferReader:"+bufferedReader);
        String read = null;
        StringBuilder builder = new StringBuilder("");

        while (true) {
            try {
                if (!((read = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(read);
        }
        System.out.println("=============================================");
        Log.d("Output", builder.toString());
        try {
            JSONObject obj=new JSONObject(builder.toString());
            JSONArray arr=obj.getJSONArray("project_list");




            for (int i=0;i<arr.length();i++){
                arrayList.add(arr.getString(i));
            }
            ArrayAdapter adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
            list.setAdapter(adapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("=============================================");

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(Project_list.this, arrayList.get(position),Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.VISIBLE);
                setProgressValue(i);



                try {
                    a.put("Record","Record");
                    a.put("Project",arrayList.get(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                new dataFetch().execute();

            }
        });






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
                    webSocket.send(String.valueOf(a.toString()));
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
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return text1;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            final Intent intent =new Intent(Project_list.this,Record_List.class);

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
            f=openFileOutput("myrecord.txt",MODE_PRIVATE);
            f.write(content.getBytes());
            Toast.makeText(this,"save to" + getFilesDir()+"/"+"myrecord.txt",Toast.LENGTH_LONG).show();
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


    private void setProgressValue(final int progress) {

        // set the progress
        progressbar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }



    // new dataFetch().execute();

}




   // @SuppressLint(value = "StaticFieldLeak"


