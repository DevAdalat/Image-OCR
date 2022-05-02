package com.adalat.ocr;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;
import android.hardware.camera2.CameraDevice;
import android.graphics.Color;
import android.hardware.Camera;
import android.view.View;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import android.support.annotation.NonNull;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import android.content.pm.PackageManager;
import android.widget.Switch;
import android.text.TextUtils;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.api.CommonStatusCodes;


public class Scanner extends Activity { 

	private SurfaceView SurfaceView;
	public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
	private TextView TextView;
	private Camerasource cameraSource;
	private static final int REQUEST_ID = 9003;
	public static final String TextBlockObject = "String";
	Intent data = new Intent();



	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
		boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, false);
        boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
        SurfaceView = findViewById(R.id.SurfaceView);
        TextView = findViewById(R.id.TextView);
		
		
		SurfaceView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if ( TextUtils.isEmpty(TextView.getText()))
					Toast.makeText(getApplicationContext(), "Please Scan The Text ", Toast.LENGTH_SHORT).show();
					
					else {
						data.putExtra(TextBlockObject, TextView.getText());
					setResult(CommonStatusCodes.SUCCESS, data);
					finish();
				} 
}

			});
		
		

        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        if (textRecognizer.isOperational()) {
            cameraSource = new Camerasource.Builder(this, textRecognizer)
				. setFacing(Camerasource.CAMERA_FACING_BACK)
				. setRequestedPreviewSize(1280, 1024)
				. setRequestedFps(2.0f)
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null)
				. build();
            SurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

					@Override
					public void surfaceCreated(SurfaceHolder surfaceHolder) {
						try {
							if (ActivityCompat.checkSelfPermission(Scanner.this,
																   Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
								ActivityCompat.requestPermissions(Scanner.this, new String[]{Manifest.permission.CAMERA},
																  REQUEST_ID);
								return;
							}
							cameraSource.start(SurfaceView.getHolder());
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						}

					@Override
					public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

					}

					@Override
					public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
						cameraSource.stop();
					}
				});

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {

					@Override
					public void release() {

					}

					@Override
					public void receiveDetections(Detector.Detections<TextBlock> detections) {
						final SparseArray<TextBlock> items = detections.getDetectedItems();
						TextView.post(new Runnable() {
								@Override
								public void run() {
									if (items.size() == 0) {
										TextView.setText("");
										TextView.setVisibility(View.INVISIBLE);
									} else {
										StringBuilder stringBuilder = new StringBuilder();
										for (int i = 0; i < items.size(); i++) {
											TextBlock item = items.valueAt(i);
											stringBuilder.append(item.getValue());
											stringBuilder.append("\n");
										}
										TextView.setText(stringBuilder.toString());
										TextView.setVisibility(View.VISIBLE);
									}
								}
							});
					}
				});
        } else {
            Log.d(getClass().getSimpleName(), "Text Recognizer is not ready.");
        }
	}


//camera config parameter

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				|View.SYSTEM_UI_FLAG_FULLSCREEN);


		}
	}
	


//Camera Permission to access the camera
	@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(Scanner.this,
														   Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        try {
                            cameraSource.start(SurfaceView.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
		

		

}

}

	
