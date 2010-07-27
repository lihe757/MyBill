package com.lihex.generic.ui;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TabHost.TabContentFactory;

import com.lihex.mybill.data.DBHelperFactory;
import com.lihex.mybill.data.DBHelperUsage;

public class UseCategoryActivity extends TabActivity implements TabContentFactory,OnTouchListener,OnChildClickListener{
	public static final String TAG="UseCategoryActivity";
	
	private static final int DELETE_ACOUNT_TYPE = 1 << 1;
	private static final int ADD_ACOUNT_TYPE = 1 << 2;
	private static final int EDIT_ACOUNT_TYPE = 1 << 3;
	
	private ExpandableListView mExpandableListView;
	private CatgItemListAdapter mCAdapter;
	private DBHelperUsage mDbHelper;
	private GestureDetector mGestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final TabHost tabHost=getTabHost();
		
        tabHost.addTab(tabHost.newTabSpec("支出")
                .setIndicator("支出")
                .setContent(this));

        tabHost.addTab(tabHost.newTabSpec("收入")
                .setIndicator("收入")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("转账")
                .setIndicator("转账")
                .setContent(this));
        
       


        
	}
	

	@Override
	public View createTabContent(String tag) {
		Log.i(TAG,"DD");
		
		if(mDbHelper==null){
			mDbHelper=(DBHelperUsage)DBHelperFactory.getInstance(this).getDBHelperByType(DBHelperFactory.DB_TYPE_USAGE);
		}
		
		Cursor cursor=mDbHelper.fetchFirstLevel(tag);
		
		if(mCAdapter==null){
			 mCAdapter=new CatgItemListAdapter(cursor,
		                this,
		                android.R.layout.simple_expandable_list_item_1,
		                android.R.layout.simple_expandable_list_item_1,
		                new String[] {"name"}, // Name for group layouts
		                new int[] {android.R.id.text1},
		                new String[] {"name"}, // Number for child layouts
		                new int[] {android.R.id.text1});
			 
		}
		if(mExpandableListView==null){
			 mExpandableListView=new ExpandableListView(this);
		        mDbHelper=(DBHelperUsage)DBHelperFactory.getInstance(this).getDBHelperByType(DBHelperFactory.DB_TYPE_USAGE);
				Log.i(TAG,"cursor size = "+cursor.getCount());
		        mExpandableListView.setOnTouchListener(this);
		        mExpandableListView.setLongClickable(true);
				mGestureDetector=new GestureDetector(new MyGesture());
				mExpandableListView.setAdapter(mCAdapter);
		}
		mCAdapter.changeCursor(cursor);
		
		
		return mExpandableListView;
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
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		mGestureDetector.onTouchEvent(event);
		return false;
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
		return true;
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
	
	
	public class MyGesture extends GestureDetector.SimpleOnGestureListener{

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.i(TAG,"on DoubleTaping!!");
			return super.onDoubleTap(e);
		}

		
		
	}

}
