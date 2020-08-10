package com.supply.chain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Information extends AppCompatActivity {

    private Button tool1;
    private Button tool2;
    private Button toolbreak,materialreq,inspect;
    String user,password,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        tool1=findViewById(R.id.tool1);
        tool2=findViewById(R.id.tool2);
        toolbreak=findViewById(R.id.toolbreakage);
        materialreq=findViewById(R.id.materialslip);
        inspect=findViewById(R.id.Inspection);
        Intent newint=getIntent();
        user=newint.getStringExtra("user");
        password=newint.getStringExtra("password");
        ip=newint.getStringExtra("ip");
        tool1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolinfo();

            }
        });
        tool2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolstaff();
            }
        });
        toolbreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbreakage();
            }
        });
        materialreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Materialreq();
            }
        });
        inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inspect();
            }
        });

    }

    private void toolstaff() {
        Intent int1=new Intent(this,Toolstaff.class);
        int1.putExtra("user",user);
        int1.putExtra("password",password);
        int1.putExtra("ip",ip);
        startActivity(int1);
    }

    private void toolinfo() {
        Intent int2=new Intent(this,Toolinfo.class);
        int2.putExtra("user",user);
        int2.putExtra("password",password);
        int2.putExtra("ip",ip);
        startActivity(int2);
    }
    private void toolbreakage(){
        Intent int3=new Intent(this,Toolbreak.class);
        int3.putExtra("user",user);
        int3.putExtra("password",password);
        int3.putExtra("ip",ip);
        startActivity(int3);
    }
    private void Materialreq(){
        Intent int3=new Intent(this,Materialrequi.class);
        int3.putExtra("user",user);
        int3.putExtra("password",password);
        int3.putExtra("ip",ip);
        startActivity(int3);

    }
    private void Inspect(){
        Intent int3=new Intent(this,Inspection.class);
        int3.putExtra("user",user);
        int3.putExtra("password",password);
        int3.putExtra("ip",ip);
        startActivity(int3);
    }
}
