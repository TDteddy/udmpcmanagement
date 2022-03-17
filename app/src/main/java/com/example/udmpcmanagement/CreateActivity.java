package com.example.udmpcmanagement;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    static TextView tvnumber;
    static EditText etuser;
    static EditText etcpu;
    static EditText etram;
    static EditText etgpu;
    MyHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mHandler=new MyHandler();
        tvnumber = (TextView) findViewById(R.id.number);
        Button btnadddata = (Button) findViewById(R.id.adddata);
        etuser= (EditText) findViewById(R.id.name);
        etcpu= (EditText) findViewById(R.id.cpu);
        etram= (EditText) findViewById(R.id.ram);
        etgpu= (EditText) findViewById(R.id.gpu);

        new Thread(new Runnable() {
            int value = 0;
            @Override
            public void run() {
                value =Apicontroll.getLastId()+1;
                Message message = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value",value);
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        }).start();

        btnadddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etuser.getText().toString().replace(" ", "").equals("")||etcpu.getText().toString().replace(" ", "").equals("")||etram.getText().toString().replace(" ", "").equals("")||etgpu.getText().toString().replace(" ", "").equals("")){
                    Toast myToast = Toast.makeText(getApplicationContext(),"데이터를확인해주세요", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    new Thread(new Runnable() {
                        int PCID = 0;
                        @Override
                        public void run() {
                            PCID=Apicontroll.getLastId()+1;
                            etuser= (EditText) findViewById(R.id.name);
                            etcpu= (EditText) findViewById(R.id.cpu);
                            etram= (EditText) findViewById(R.id.ram);
                            etgpu= (EditText) findViewById(R.id.gpu);
                            Apicontroll.addPC(PCID,etuser.getText().toString(),etcpu.getText().toString(),etram.getText().toString(),etgpu.getText().toString());
                            PCID=Apicontroll.getLastId()+1;
                            Message message = mHandler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putInt("value",PCID);
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        }
                    }).start();
                    Toast myToast = Toast.makeText(getApplicationContext(),"등록완료", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });

    }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // 다른 Thread에서 전달받은 Message 처리
            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            int no=0;
            tvnumber.setText("등록예정번호 : " + value);
            etuser.setText(null);
            etcpu.setText(null);
            etram.setText(null);
            etgpu.setText(null);
        }
    }
}