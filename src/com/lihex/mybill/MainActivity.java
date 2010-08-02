package com.lihex.mybill;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.lihex.generic.ui.AccountCategory;
import com.lihex.generic.ui.CalendarActivity;
import com.lihex.generic.ui.Participant;
import com.lihex.generic.ui.UseCategoryActivity;

public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";

	public static final int PICK_ONE_DATE = 1;
	public static final int PICK_ACCOUNT_TYPE = 2;
	public static final int PICK_USAGE_TYPE = 3;
	

	private Button mBtnDate;
	private Button mBtnAccount;
	private Button mBtnUsage;
	private Button mBtnParticipant;
	
	private SimpleDateFormat mDateFormater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mDateFormater=new SimpleDateFormat("yyyy年MM月dd日");
		initView();

	}

	private void initView() {
		mBtnDate = (Button) findViewById(R.id.btn_start_time);
		mBtnDate.setText(mDateFormater.format(new Date(System.currentTimeMillis())));
		mBtnDate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,
						CalendarActivity.class);
				startActivityForResult(intent, PICK_ONE_DATE);
			}
		});
		mBtnAccount = (Button) findViewById(R.id.btn_acount);
		mBtnAccount.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,
						AccountCategory.class);
				startActivityForResult(intent, PICK_ACCOUNT_TYPE);

			}
		});
		mBtnUsage = (Button) findViewById(R.id.btn_category);
		mBtnUsage.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,
						UseCategoryActivity.class);
				startActivityForResult(intent, PICK_USAGE_TYPE);

			}
		});
		mBtnParticipant = (Button) findViewById(R.id.btn_participant);
		mBtnParticipant.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Participant.class));

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu, menu);

		return true;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PICK_ONE_DATE:
				long time = data.getLongExtra("time", -1);
				if (-1 != time) {
					Date d = new Date(time);
					String timeStr = mDateFormater.format(d);
					mBtnDate.setText(timeStr);
					Log.i(TAG, "timeStr = " + timeStr);

				}
				break;
			case PICK_ACCOUNT_TYPE:
				Log.i(TAG,"account id = "+data.getIntExtra("account_id", -1));
				break;
			case PICK_USAGE_TYPE:
				Log.i(TAG,"usage id = "+data.getIntExtra("usage_id", -1));
				break;

			}
		}

	}

}