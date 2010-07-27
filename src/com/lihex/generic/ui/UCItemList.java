package com.lihex.generic.ui;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.GestureOverlayView.OnGesturingListener;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.lihex.mybill.data.DBHelperFactory;
import com.lihex.mybill.data.DBHelperUsage;

public class UCItemList extends ExpandableListActivity implements OnTouchListener {
	public static final String TAG="UCItemList";
	private static final int DELETE_ACOUNT_TYPE = 1 << 1;
	private static final int ADD_ACOUNT_TYPE = 1 << 2;
	private static final int EDIT_ACOUNT_TYPE = 1 << 3;
	

	private CatgItemListAdapter mCAdapter;
	private DBHelperUsage mDbHelper;
	private GestureDetector mGestureDetector;
	
	public static final String[] GROUPNAME={"name"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=(DBHelperUsage)DBHelperFactory.getInstance(this).getDBHelperByType(DBHelperFactory.DB_TYPE_USAGE);
		Cursor cursor=mDbHelper.fetchFirstLevel();
		Log.i(TAG,"cursor size = "+cursor.getCount());
		mCAdapter=new CatgItemListAdapter(cursor,
                this,
                android.R.layout.simple_expandable_list_item_1,
                android.R.layout.simple_expandable_list_item_1,
                new String[] {"name"}, // Name for group layouts
                new int[] {android.R.id.text1},
                new String[] {"name"}, // Number for child layouts
                new int[] {android.R.id.text1});
		setListAdapter(mCAdapter);
	
		getExpandableListView().setOnTouchListener(this);
		getExpandableListView().setLongClickable(true);
		mGestureDetector=new GestureDetector(new MyGesture());

	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ADD_ACOUNT_TYPE, 0, "新建").setIcon(
				android.R.drawable.ic_menu_add);
		return true;
	}





	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}





	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int menuId = item.getItemId();
		switch (menuId) {
		case ADD_ACOUNT_TYPE:
//			mDbHelper.insert(2, "你好"+System.currentTimeMillis(), "支付");
			startActivity(new Intent(this,ChooseUsageType.class));
			Log.i(TAG,"添加一条USAGE");
			break;
		}
		return true;
	}





	public class CatgItemListAdapter extends SimpleCursorTreeAdapter {

        public CatgItemListAdapter(Cursor cursor, Context context, int groupLayout,
                int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
                int[] childrenTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childrenFrom,
                    childrenTo);
        }

		@Override
		protected Cursor getChildrenCursor(Cursor groupCursor) {
			// TODO Auto-generated method stub
			Log.i(TAG,"id = "+groupCursor.getInt(0));
			
			return mDbHelper.fetchSecendLevel(groupCursor.getInt(0));
		}

    }
	public class MyGesture extends GestureDetector.SimpleOnGestureListener{

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.i(TAG,"on DoubleTaping!!");
			return super.onDoubleTap(e);
		}

		
		
	}




	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		mGestureDetector.onTouchEvent(event);
		return false;
	}





	

}
