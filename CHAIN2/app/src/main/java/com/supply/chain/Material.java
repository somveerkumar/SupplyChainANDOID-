package com.supply.chain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Material extends AppCompatActivity {
    private Button waste,coolant,rawmaterial,safety,tool;
    String user,password,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        waste = findViewById(R.id.Wastage);
        coolant = findViewById(R.id.Coolant);
        safety = findViewById(R.id.safety);
        rawmaterial = findViewById(R.id.rawmaterial);
        tool = findViewById(R.id.toolused);
        Intent newint = getIntent();
        user = newint.getStringExtra("user");
        password = newint.getStringExtra("password");
        ip = newint.getStringExtra("ip");
        waste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wast();
            }
        });
        coolant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cool();
            }
        });
        safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safe();
            }
        });
        rawmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rawmat();
            }
        });
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolused();
            }
        });
    }
        private void wast() {
            Intent int1=new Intent(this,Waste.class);
            int1.putExtra("user",user);
            int1.putExtra("password",password);
            int1.putExtra("ip",ip);
            startActivity(int1);
        }

        private void cool() {
            Intent int2=new Intent(this,Cool.class);
            int2.putExtra("user",user);
            int2.putExtra("password",password);
            int2.putExtra("ip",ip);

            startActivity(int2);
        }
        private void safe(){
            Intent int3=new Intent(this,Safety.class);
            int3.putExtra("user",user);
            int3.putExtra("password",password);
            int3.putExtra("ip",ip);
            startActivity(int3);
        }
        private void toolused(){
            Intent int3=new Intent(this,Tool.class);
            int3.putExtra("user",user);
            int3.putExtra("password",password);
            int3.putExtra("ip",ip);
            startActivity(int3);

        }
        private void rawmat(){
            Intent int3=new Intent(this,MaterialRaw.class);
            int3.putExtra("user",user);
            int3.putExtra("password",password);
            int3.putExtra("ip",ip);
            startActivity(int3);
        }
    }


