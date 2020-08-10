package com.supply.chain;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Record extends AppCompatActivity {
    private String mess, TAG = "Response";
    int i = 0;
    List<String> Projects = new ArrayList<String>();
    ListView list;
    TextView text;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);



        list = findViewById(R.id.list1);



        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new
                    File(getFilesDir() + File.separator + "record.txt")));
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
            ArrayList<String> arrayList = new ArrayList<String>();
            Iterator it = obj.keys();
            while(it.hasNext()){
                String key= (String) it.next();
                arrayList.add(key.toUpperCase() +" : " +obj.get(key));

            }

            ArrayAdapter adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
            list.setAdapter(adapter);

        }catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("=============================================");

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }







    }
}

