package com.example.udmpcmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(MainActivity.this, "취소되었습니다", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                    String tocam = result.getContents();
                    try {
                        intent.putExtra("code",Integer.parseInt(tocam));
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "PC번호: " + result.getContents(), Toast.LENGTH_LONG).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "바코드를 다시읽혀주세요", Toast.LENGTH_LONG).show();
                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnscan = (Button) findViewById(R.id.scan);
        Button btncreate = (Button) findViewById(R.id.create);
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeLauncher.launch(new ScanOptions());
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });
    }
}