package com.lihex.generic.ui;

import java.util.Calendar;
import java.util.List;

import com.lihex.mybill.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeekDayAdapter extends BaseAdapter {

	private static final int MAX_OF_DAY = 42;
	private Calendar mCalendar;
	private Calendar tmpCalendar = Calendar.getInstance();
	/* 本月1号，是本周的第几天，星期天为一周当中的第一天 */
	private int weekDayOfMonthDay;
	/* 本月的最大天数 */
	private int maxdays;

	private int mResource;
	private int mFieldId = 0;
	private LayoutInflater mInflater;

	private Context mContext;

	public WeekDayAdapter(Context context) {
		this(context, Calendar.getInstance(), 0);
	}

	public WeekDayAdapter(Context context, Calendar c, int textViewResourceId) {

		init(context, c, textViewResourceId,0);

	}
	public WeekDayAdapter(Context context, Calendar c, int resource,int textViewResourceId) {
		init(context, c, resource,textViewResourceId);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return MAX_OF_DAY;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getDay(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {

		View view;
		TextView text;

		if (convertView == null) {
			view = mInflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}

		try {
			if (mFieldId == 0) {
				// If no custom field is assigned, assume the whole resource is
				// a TextView
				text = (TextView) view;
			} else {
				// Otherwise, find the TextView field within the layout
				text = (TextView) view.findViewById(mFieldId);
			}
		} catch (ClassCastException e) {
			Log.e("ArrayAdapter",
					"You must supply a resource ID for a TextView");
			throw new IllegalStateException(
					"ArrayAdapter requires the resource ID to be a TextView", e);
		}

		if (isEnabled(position)) {
			text.setText("" + getDay(position));
		} else {
			text.setText("");
		}

		return view;

	}

	@Override
	public boolean isEnabled(int position) {
		if ((position < weekDayOfMonthDay)
				|| (position > (maxdays + weekDayOfMonthDay))) {
			return false;
		}
		return true;

	}

	public int getDay(int position) {
		return (position - weekDayOfMonthDay) + 1;
	}

	@Override
	public void notifyDataSetChanged() {
		tmpCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar
				.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		/* 设置为本月一号 */
		this.tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
		/* 本月1号，是本周的第几天，星期天为一周当中的第一天 */
		this.weekDayOfMonthDay = tmpCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		/* 本月的最大天数 */
		this.maxdays = tmpCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1;
		super.notifyDataSetChanged();
	}

	private void init(Context context, Calendar c, int viewResource,int textViewResourceId) {
		mContext = context;
		mResource=viewResource;
		mCalendar = c;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mFieldId = textViewResourceId;
		notifyDataSetChanged();
	}
	public static WeekDayAdapter createFromResource(Context context,Calendar c,
            int textViewResId) {
        return new WeekDayAdapter(context,c,textViewResId);
    }


}
