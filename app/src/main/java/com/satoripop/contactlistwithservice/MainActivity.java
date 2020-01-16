package com.satoripop.contactlistwithservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int readContactPermissionCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startContactService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (readContactPermissionCode == requestCode && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            startContactService();
        }
    }

    private void startContactService() {
        if (PackageManager.PERMISSION_GRANTED ==
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
            Intent intent = new Intent(MainActivity.this, ContactService.class);
            if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, readContactPermissionCode);
        }
    }
}
