package com.adalat.ocr;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Switch;
import android.graphics.Typeface;
import android.widget.Button;
import android.graphics.Color;
import android.text.TextUtils;

import android.view.View;
import android.content.Intent;
import android.content.IntentSender;
import android.app.IntentService;


public class MainActivity extends Activity {
	private TextView TextView1;
	private TextView TextView2;
	private TextView Heading;
	private TextView Result;
	private Switch AutoFocus; 
	private Switch Flash;
	private Button Read_Text;
	private static final int OcrCapture = 9003;
	private Button Copy_Text;
	private Button Speak;
	private Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView1 = (TextView)findViewById(R.id.textview1);
		TextView2 = (TextView)findViewById(R.id.textview2);
		Heading = (TextView)findViewById(R.id.Heading);
		Result = (TextView)findViewById(R.id.Result);
		AutoFocus = (Switch)findViewById(R.id.AutoFocus);
		Flash = (Switch)findViewById(R.id.Flash);
		Read_Text = (Button)findViewById(R.id.Read_Text);
		Copy_Text = (Button)findViewById(R.id.Copy_Text);
		Speak = (Button)findViewById(R.id.Speak);
		
		TextView1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
		TextView2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
		Heading.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
		Result.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
		Read_Text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
		Copy_Text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
		Speak.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
		int[] colorsCRNFJ = { Color.parseColor("#FF237E"), Color.parseColor("#ffffff") }; android.graphics.drawable.GradientDrawable CRNFJ = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNFJ);
		CRNFJ.setCornerRadii(new float[]{(int)26,(int)26,(int)26,(int)26,(int)26,(int)26,(int)26,(int)26});
		CRNFJ.setStroke((int) 0, Color.parseColor("#BB46A7"));
		Read_Text.setElevation((float) 5);
		Read_Text.setBackground(CRNFJ);


		Copy_Text.setElevation((float) 5);
		Copy_Text.setBackground(CRNFJ);
		Speak.setElevation((float) 5);
		Speak.setBackground(CRNFJ);
		if (TextUtils.isEmpty(Result.getText().toString())) {
			Heading.setText("Click \"Read Text\" Button To Scan Text");
		}
		else {
			Heading.setText("Result");
		}
		
		Read_Text.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), Scanner.class);
					intent.putExtra(Scanner.AutoFocus, AutoFocus.isChecked());
					intent.putExtra(Scanner.UseFlash, Flash.isChecked());
					startActivityForResult(intent, OcrCapture);
				}
				
				
			});
			
		}
	
	
	
}

	
