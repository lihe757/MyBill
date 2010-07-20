package com.lihex.generic.ui;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.lihex.mybill.R;

public class CalendarActivity extends Activity implements OnItemClickListener {
	private static final boolean DEBUG = true;
	private static final String TAG = "CalendarActivity";

	private Calendar mCalendar = Calendar.getInstance();
	private WeekDayAdapter weekDayAdapter;
	private GridView mGridWeekDay;

	private Button mBtnDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		initView();
	}

	private void initView() {
		mBtnDate = (Button) findViewById(R.id.btn_date);
		mBtnDate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				btnDateOnClicked(v);
			}
		});

		Button btnLeft = (Button) findViewById(R.id.btn_left);
		btnLeft.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCalendar
						.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) - 1);
				OnDateChangeListener();
			}
		});
		Button btnRight = (Button) findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCalendar
						.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) + 1);
				OnDateChangeListener();
			}
		});

		/* 初始化星期标题 */
		GridView grdView = (GridView) findViewById(R.id.grd_week_title);
		ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.week_title, R.layout.text_week_title);
		

		grdView.setAdapter(adapter);
		// /*初始化日历表*/
		mGridWeekDay = (GridView) findViewById(R.id.grd_week_day);
		weekDayAdapter = new WeekDayAdapter(this, mCalendar,
				 R.layout.text_week_day);

		mGridWeekDay.setAdapter(weekDayAdapter);
		mGridWeekDay.setOnItemClickListener(this);

	}

	private void btnDateOnClicked(View v) {
		// mCalendar=Calendar.getInstance();
		DatePickerDialog dpd = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						mCalendar.set(year, monthOfYear, dayOfMonth);
						OnDateChangeListener();
					}
				}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH));
		dpd.setTitle("选择时间");

		dpd.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		debug(TAG, "" + ((TextView) view).getText());
		// view.requestFocus();

	}

	/**
	 * 处理日历变化
	 */
	private void OnDateChangeListener() {

		weekDayAdapter.notifyDataSetChanged();
		mBtnDate.setText(DateFormat.format("yyyy年MM月", mCalendar));
	}

	private static void debug(String tag, String mesg) {
		if (DEBUG) {
			Log.i(tag, mesg);
		}

	}

}
