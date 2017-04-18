package com.example.myviewutils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";

	@ViewInject(R.id.tv1)
	private TextView textView1;
	
	@ViewInject(R.id.tv2)
	private TextView textView2;
	
	private String name;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		
		Log.d(TAG, textView1.getText().toString()+"/"+textView2.getText().toString());
		
	}
	
	@OnClick({R.id.btn1,R.id.btn2})
	private void click(View view){
		switch (view.getId()) {
		case R.id.btn1:
			Toast.makeText(this, "btn1被点击了", Toast.LENGTH_SHORT).show();
			break;

		case R.id.btn2:
			Toast.makeText(this, "btn2被点击了", Toast.LENGTH_SHORT).show();
			break;
			
		default:
			break;
		}
		
	}
	

}
