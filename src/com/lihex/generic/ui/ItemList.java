package com.lihex.generic.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import com.lihex.mybill.R;
import com.lihex.mybill.data.DBHelper;

public class ItemList extends ListActivity {
	public static final String TAG = "ItemList";

	private SimpleCursorAdapter mCAdapter;
	private DBHelper mDbHelper;
	private static final String[] FROM = { "name", "blance", "remark" };
	private static final int[] TO = { R.id.txt_acount_name,
			R.id.txt_acount_blance, R.id.txt_acount_remark };

	private static final int DELETE_ACOUNT_TYPE = 1 << 1;
	private static final int ADD_ACOUNT_TYPE = 1 << 2;
	private static final int EDIT_ACOUNT_TYPE = 1 << 3;
	
	private static final int MENU_GROUP_ALTERNATIVE=0x00000001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Inform the list we provide context menus for items
		getListView().setOnCreateContextMenuListener(this);
		
		initView();

	}

	private void initView() {
		mDbHelper = new DBHelper(this);
		mDbHelper.establishDb();
//		 mDbHelper.insert("�Ż�ȯ", 1000f, "���");
		Cursor c = mDbHelper.fetchAll();

		 mCAdapter = new SimpleCursorAdapter(this,
				R.layout.tab_list_item, c, FROM, TO);
		
		this.setListAdapter(mCAdapter);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		final AdapterView.AdapterContextMenuInfo info;
		
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(
				R.layout.acount_type_text_dialog, null);
		/*�˻�����*/
		final Button typeBtn=(Button)textEntryView.findViewById(R.id.btn_ac_type);
		typeBtn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final CharSequence[] items=getResources().getStringArray(R.array.acount_type);
				final AlertDialog typeDialog=new AlertDialog.Builder(ItemList.this)
				.setTitle("")
				.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						typeBtn.setText(items[which]);
					}
				}).create();
				
				typeDialog.show();
				
			}
		});
		/*�˻�����*/
		final EditText edtName=(EditText)textEntryView.findViewById(R.id.edt_ac_name);
		final EditText edtBlance=(EditText)textEntryView.findViewById(R.id.edt_ac_blance);
		final EditText edtRemark=(EditText)textEntryView.findViewById(R.id.edt_ac_remark);
		
		int menuId = item.getItemId();
		switch (menuId) {
		case ADD_ACOUNT_TYPE:
			Log.i("ItemList", (String) item.getTitle());	
			new AlertDialog.Builder(ItemList.this)
					// .setIcon(R.drawable.alert_dialog_icon)
					.setTitle("�������˻�����").setView(textEntryView)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									 mDbHelper.insert(edtName.getText().toString(), Float.parseFloat(edtBlance.getText().toString()), edtRemark.getText().toString());
									 /*ˢ������*/
									 mCAdapter.getCursor().requery();
									Log.i(TAG, "" + whichButton);
								}
							}).setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Log.i(TAG, "" + whichButton);

								}
							}).create().show();
			break;
		case EDIT_ACOUNT_TYPE:
			
			try {
				info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			} catch (ClassCastException e) {
				Log.e(TAG, "bad menuInfo", e);
				return false;
			}
			
			Cursor c=mCAdapter.getCursor();
			if(null==info){
				c.moveToPosition(getSelectedItemPosition());
			}else{
				c.moveToPosition(info.position);
			}
			
			
			
			edtName.setText(c.getString(1));
			edtBlance.setText(c.getFloat(2)+"");
			edtRemark.setText(c.getString(3));
			new AlertDialog.Builder(ItemList.this)
			// .setIcon(R.drawable.alert_dialog_icon)
			.setTitle("�༭�˻�����").setView(textEntryView)
			.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							Log.i(TAG, "" + whichButton);
						}
					}).setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Log.i(TAG, "" + whichButton);

						}
					}).create().show();
			break;
		case DELETE_ACOUNT_TYPE:
			
			try {
				info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			} catch (ClassCastException e) {
				Log.e(TAG, "bad menuInfo", e);
				return false;
			}
			
			new AlertDialog.Builder(ItemList.this)
			// .setIcon(R.drawable.alert_dialog_icon)
			.setTitle("ɾ���˻�����")
			.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Cursor c=mCAdapter.getCursor();
							if(null==info){
								c.moveToPosition(getSelectedItemPosition());
							}else{
								c.moveToPosition(info.position);
							}
							
							int id=c.getInt(0);
							mDbHelper.delete(id);
							 /*ˢ������*/
							mCAdapter.getCursor().requery();
							
							Log.i(TAG, "Delete id = " + id);
						}
					}).setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Log.i(TAG, "" + whichButton);

						}
					})
					.setMessage("ȷ��ɾ�����˻����ͣ�")
					.create().show();
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ADD_ACOUNT_TYPE, 0, "�½�").setIcon(android.R.drawable.ic_menu_add);
    	menu.add(MENU_GROUP_ALTERNATIVE,EDIT_ACOUNT_TYPE,0,"edit");
    	menu.add(MENU_GROUP_ALTERNATIVE,DELETE_ACOUNT_TYPE,0,"delete");
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			Log.e(TAG, "bad menuInfo", e);
			return;
		}
		Cursor c = (Cursor) getListAdapter().getItem(info.position);
		if (null == c) {
			return;
		}
		// Setup the menu header
		menu.setHeaderTitle(c.getString(1));
		
		Log.i(TAG, "id onCreateContextMenu"+info.id);

		// Add a menu item to delete and edit the note
		menu.add(0,EDIT_ACOUNT_TYPE,0,"Edit");
		menu.add(0, DELETE_ACOUNT_TYPE, 0, "Delete");
		
		
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		  AdapterView.AdapterContextMenuInfo info;
	        try {
	             info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	        } catch (ClassCastException e) {
	            Log.e(TAG, "bad menuInfo", e);
	            return false;
	        }

	        switch (item.getItemId()) {
	            case DELETE_ACOUNT_TYPE: {
	            	Cursor c = (Cursor) getListAdapter().getItem(info.position);
	            	Log.i(TAG,"onContextItemSelected = "+c.getInt(0));
	            	return true;
	            }
	        }
	        return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		super.onPrepareOptionsMenu(menu);
		
		final boolean haveItems = getListAdapter().getCount() > 0;
		Log.i(TAG,getSelectedItemPosition()+"");

        // If there are any notes in the list (which implies that one of
        // them is selected), then we need to generate the actions that
        // can be performed on the current selection.  This will be a combination
        // of our own specific actions along with any extensions that can be
        // found.
        if (haveItems&&(getSelectedItemPosition()!=-1)) {
        	menu.setGroupVisible(MENU_GROUP_ALTERNATIVE, true);
        	

        }else{
        	menu.setGroupVisible(MENU_GROUP_ALTERNATIVE, false);
        }

        return true;
	}

	
}