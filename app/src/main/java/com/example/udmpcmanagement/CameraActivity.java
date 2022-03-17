package com.example.udmpcmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    MyHandler mHandler;
    static TextView tvpcno;
    static TextView tvpcuser;
    static TextView tvpccpu;
    static TextView tvpcram;
    static TextView tvpcgpu;
    int codea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mHandler = new MyHandler();
        tvpcno = (TextView) findViewById(R.id.PCno);
        tvpcuser = (TextView) findViewById(R.id.PCuser);
        tvpccpu = (TextView) findViewById(R.id.PCcpu);
        tvpcram = (TextView) findViewById(R.id.PCram);
        tvpcgpu = (TextView) findViewById(R.id.PCgpu);
        Intent camIntent = getIntent();
        codea = camIntent.getIntExtra("code",0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List info=Apicontroll.getPC(codea);
                if(info.get(0) !="null"){
                Message message = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                System.out.println("aa"+info);
                bundle.putParcelableArrayList("value", (ArrayList<? extends Parcelable>) info);
                message.setData(bundle);
                mHandler.sendMessage(message);}

            }
        }).start();

    }
    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // 다른 Thread에서 전달받은 Message 처리
            Bundle bundle = msg.getData();
            List info = bundle.getParcelableArrayList("value");
            String end=info.toString();
            String[] temp= end.replace("[","").replace("]","").split(",");
            tvpcno.setText("PC번호: "+temp[0]);
            tvpcuser.setText("사용자명: "+temp[1].replaceAll("\"",""));
            tvpccpu.setText("CPU: "+temp[2].replaceAll("\"",""));
            tvpcram.setText("RAM: "+temp[3].replaceAll("\"",""));
            tvpcgpu.setText("GPU: "+temp[4].replaceAll("\"",""));
        }
    }
}