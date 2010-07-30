package com.lihex.generic.ui;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FilterQueryProvider;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;

import com.lihex.mybill.data.DBHelperFactory;
import com.lihex.mybill.data.DBHelperUsage;

public class UCItemList extends ExpandableListActivity implements OnTouchListener {
	public static final String TAG="UCItemList";
	private static final int DELETE_USAGE_TYPE = 1 << 1;
	private static final int ADD_USAGE_TYPE = 1 << 2;
	private static final int EDIT_USAGE_TYPE = 1 << 3;
	

	private CatgItemListAdapter mCAdapter;
	private DBHelperUsage mDbHelper;
	private GestureDetector mGestureDetector;
	
	public static final String[] GROUPNAME={"name"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerForContextMenu(getExpandableListView());
		
		
		mDbHelper=(DBHelperUsage)DBHelperFactory.getInstance(this).getDBHelperByType(DBHelperFactory.DB_TYPE_USAGE);
		String type=getIntent().getStringExtra("type");
		Cursor cursor=mDbHelper.fetchFirstLevel(type);

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
//		getExpandableListView().setGroupIndicator(getResources().getDrawable(com.lihex.mybill.R.drawable.group_indicator));
		Log.i(TAG,"----------------------+++++++++++");
		

	}
	
	
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		Cursor c=mCAdapter.getCursor();
		c.requery();
		Log.i(TAG,"cursor size = "+c.getCount());
		
	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ADD_USAGE_TYPE, 0, "新建").setIcon(
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
	public boolean onContextItemSelected(MenuItem item) {
		
		
		return false;
	}





	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		ExpandableListView.ExpandableListContextMenuInfo info=(ExpandableListView.ExpandableListContextMenuInfo)menuInfo;
		Log.i(TAG,"info.id = "+info.id);
		Cursor c=mDbHelper.fetchOne((int)info.id);
		
		menu.setHeaderTitle(c.getString(2));
		menu.add(0,EDIT_USAGE_TYPE,0,"编辑");
		menu.add(0,DELETE_USAGE_TYPE,0,"删除");
		
	}





	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	
		
		int menuId = item.getItemId();
		switch (menuId) {
		case ADD_USAGE_TYPE:
//			mDbHelper.insert(-1, "你好"+System.currentTimeMillis(), "支出");
			Log.i(TAG,"添加一条USAGE");
			getAlertDialog(ADD_USAGE_TYPE,item).show();
			break;
		case EDIT_USAGE_TYPE:
				Log.i(TAG,"编辑一条USAGE");
				getAlertDialog(EDIT_USAGE_TYPE,item).show();
			break;
		case DELETE_USAGE_TYPE:
			Log.i(TAG,"删除一条USAGE");
			getAlertDialog(DELETE_USAGE_TYPE, item).show();
		break;
		}
		return true;
	}

	/**
	 * 生成一个对话框
	 * @param type 按类型
	 * @return
	 */
	private AlertDialog getAlertDialog(int type,MenuItem item){
		
		AlertDialog dialog=null;
		final ExpandableListView.ExpandableListContextMenuInfo info=(ExpandableListView.ExpandableListContextMenuInfo)item.getMenuInfo();
		
		/*吹图器*/
		LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		LinearLayout view=(LinearLayout)inflater.inflate(com.lihex.mybill.R.layout.choose_usage_type, null);
		
		final Bundle usege=new Bundle();
		/*设置名称编辑框*/
		final EditText edt_name=(EditText)view.findViewById(com.lihex.mybill.R.id.edt_usage_name);
		/*设置下拉列表*/
		Cursor c=mDbHelper.fetchFirstLevel("支出");
		final SimpleCursorAdapter adapter=new SimpleCursorAdapter(UCItemList.this,com.lihex.mybill.R.layout.simple_txt,c,new String[]{"name"},new int[]{com.lihex.mybill.R.id.text1});
		
		/*设置根分类RadioButton*/
		final RadioGroup rdgRoot=(RadioGroup)view.findViewById(com.lihex.mybill.R.id.rdg_root);
		rdgRoot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Log.i(TAG,"checked id = "+checkedId+" = "+com.lihex.mybill.R.id.rdbtn_income);
				Cursor c=null;
				switch (checkedId) {
				case com.lihex.mybill.R.id.rdbtn_payout:
//					c=mDbHelper.fetchFirstLevel("支出");
					c=mDbHelper.fetchFirstLevelIgnore("支出", new int[]{1});
					adapter.changeCursor(c);
					usege.putString("type", "支出");
					break;
				case com.lihex.mybill.R.id.rdbtn_income:
					 c=mDbHelper.fetchFirstLevel("收入");
					adapter.changeCursor(c);
					usege.putString("type", "收入");
					break;
				case com.lihex.mybill.R.id.rdbtn_transfer:
					c=mDbHelper.fetchFirstLevel("转账");
					adapter.changeCursor(c);
					usege.putString("type", "转账");
					
					break;
				}
				
			}
		});
		final Spinner spinParent=(Spinner)view.findViewById(com.lihex.mybill.R.id.spin_parent);
		spinParent.setAdapter(adapter);
		spinParent.setEnabled(false);
		spinParent.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG,"Item's id = "+id);
				usege.putInt("parent_id", (int) id);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}
		});

		
		/*设置是否使用父类CHECKBOX*/
		CheckBox cbUseParent=(CheckBox)view.findViewById(com.lihex.mybill.R.id.cb_use_parent);
		cbUseParent.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						spinParent.setEnabled(true);
					}else{
						spinParent.setEnabled(false);
					}
				
			}
		});
		
		switch (type) {
		case ADD_USAGE_TYPE:
			usege.putInt("parent_id", -1);
//			usege.putString("type", "支出");
			usege.putString("type", getIntent().getStringExtra("type"));
			/*生成对话框*/
			dialog=new AlertDialog.Builder(UCItemList.this)
			.setTitle("添加分类")
			.setView(view)			
			.setNegativeButton("Cancle", null)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String name = edt_name.getText().toString();
					usege.putString("name", name);
					if(!spinParent.isEnabled()){
						usege.putInt("parent_id", -1);
					}
					mDbHelper.insert(usege);
					Log.i(TAG, usege.toString());
				}
			})
			.create();
			break;
		case EDIT_USAGE_TYPE:
			if(info!=null){
				final int id=(int) info.id;
				Cursor cursor=mDbHelper.fetchOne(id);	
				Log.i(TAG, "id = "+id+",name = "+cursor.getString(cursor.getColumnIndex("name")));
				/*设置名称*/
				edt_name.setText(cursor.getString(2));
			
				/*设置根分类*/
				String rootType=cursor.getString(3);
				usege.putString("type", rootType);
				if(rootType.equals("支出")){
					rdgRoot.check(com.lihex.mybill.R.id.rdbtn_payout);
					
				}else if(rootType.equals("收入")){
					rdgRoot.check(com.lihex.mybill.R.id.rdbtn_income);
				}else if(rootType.equals("转账")){
					rdgRoot.check(com.lihex.mybill.R.id.rdbtn_transfer);
				}
				/*设置父类*/
				int pId=cursor.getInt(1);
				usege.putInt("parent_id", pId);
				int pos=-1;
				if(-1==pId){
					cbUseParent.setChecked(false);
				}else{
					cbUseParent.setChecked(true);
					SimpleCursorAdapter a=(SimpleCursorAdapter)spinParent.getAdapter();
					for(int i=0;i<a.getCount();i++){
						if(a.getItemId(i)==pId){
							pos=i;
							break;
						}
					}
					spinParent.setSelection(pos);
				}
				
				
			}
			
			
			/*生成对话框*/
			dialog=new AlertDialog.Builder(UCItemList.this)
			.setTitle("编辑分类")
			.setView(view)			
			.setNegativeButton("Cancle", null)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {							

				@Override
				public void onClick(DialogInterface dialog, int which) {
					String name = edt_name.getText().toString();
					usege.putString("name", name);
					
					if(!spinParent.isEnabled()){
						usege.putInt("parent_id", -1);
					}
					
					mDbHelper.update((int) info.id,usege);
					Log.i(TAG, usege.toString());
				}
			})
			.create();
			break;
		case DELETE_USAGE_TYPE:
			dialog=new AlertDialog.Builder(UCItemList.this)
			.setTitle("删除分类")
			.setMessage("确定删除吗？")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mDbHelper.delete((int)info.id);
					
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			}).create();
			break;

		}
		return dialog;
		
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
