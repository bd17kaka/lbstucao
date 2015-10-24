package com.hacklm.tucao;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hacklm.assist.Detail;
import com.hacklm.assist.NetUtils;

public class DetailActivity extends Activity {
	private ListView tucaoList;
	private String json;
	private Double x, y;
	private String[] contents;
	private Button add;
	private EditText input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		json = this.getIntent().getExtras().getString("data");
		Gson gson = new Gson();
		Detail d = gson.fromJson(json, Detail.class);
		x = d.getX();
		y = d.getY();
		contents = d.getContents();
		tucaoList = (ListView) findViewById(R.id.tucao_list);
		add = (Button) findViewById(R.id.addTucao);
		input = (EditText) findViewById(R.id.input2);

	}

	public class mAddThread extends Thread {
		@Override
		public void run() {
			Looper.prepare();
			// TODO Auto-generated method stu
			NetUtils.addTucao(x, y, input.getText().toString());
		}
	};

	public void resetView() {
		input.setVisibility(View.GONE);
		add.setText("我也要吐槽");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, contents);
		tucaoList.setAdapter(adapter);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (input.getVisibility() == View.GONE) {
					add.setText("发布");
					input.setVisibility(View.VISIBLE);
					input.setText("");
					input.requestFocus();
				} else {
					if (!TextUtils.isEmpty(input.getText())) {
						Thread t = new mAddThread();
						t.start();
						resetView();
						InputMethodManager imm = (InputMethodManager) input
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
						DetailActivity.this.finish();
					} else {
						Toast.makeText(
								DetailActivity.this.getApplicationContext(),
								"随便吐槽点什么呗", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(input.getVisibility() != View.GONE){
			resetView();
			input.clearFocus();
		}else{
			super.onBackPressed();
		}
	}

}
