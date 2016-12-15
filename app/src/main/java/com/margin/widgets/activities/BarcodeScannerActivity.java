package com.cargomatrix.widgets.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cargomatrix.widgets.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by andranik.azizbekyan on 3/24/2016.
 */
public class BarcodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int PERMISSIONS_CAMERA = 111;

    private ZXingScannerView mScannerView;
    private boolean isRequestingPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        if (null != contentFrame) {
            mScannerView = new ZXingScannerView(this);
            contentFrame.addView(mScannerView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRequestingPermission) {
            isRequestingPermission = false;
        } else {
            initCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        mScannerView.setResultHandler(null);
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(BarcodeScannerActivity.this, "Content: " + rawResult.getText() + "\n"
                + "Format: " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(BarcodeScannerActivity.this);
            }
        }, 1000);
    }

    private void initCamera() {
        if (checkPermissions()) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(BarcodeScannerActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_CAMERA);
                isRequestingPermission = true;
                return false;
            }
        }
        return true;
    }

    /**
     * In case that the user denied permission, a {@link Snackbar} will be shown with asking
     * to go to the settings and allow app permissions.
     */
    private void showSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(
                com.cargomatrix.camera.R.string.message_no_permissions_snackbar), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(com.cargomatrix.camera.R.string.settings),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCamera();
                } else {
                    showSnackbar();
                }
                isRequestingPermission = true;
                break;
        }
    }

}
