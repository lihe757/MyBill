package com.lihex.generic.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.lihex.mybill.R;
import com.lihex.mybill.data.DBHelperAccountType;
import com.lihex.mybill.data.DBHelperFactory;
import com.lihex.mybill.data.DBHelperParticipant;

public class ParticiPantItemList extends ListActivity {
	public static final String TAG = "ItemList";

	private SimpleCursorAdapter mCAdapter;
	private DBHelperParticipant mDbHelper;
	private Cursor mCursor;
	private String[] mTypeArray;
	private static final String[] FROM = { "name", "remark" };
	private static final int[] TO = { R.id.txt_part_name, R.id.txt_part_remark };

	private static final int DELETE_ACOUNT_TYPE = 1 << 1;
	private static final int ADD_ACOUNT_TYPE = 1 << 2;
	private static final int EDIT_ACOUNT_TYPE = 1 << 3;
	
	private int curTypeId=0;
	

	/* 编辑、删除菜单组ID */
	private static final int MENU_GROUP_ALTERNATIVE = 0x00000001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Inform the list we provide context menus for items
		getListView().setOnCreateContextMenuListener(this);

		/* 读取站户类型数组 */
		mTypeArray = getResources().getStringArray(R.array.participant);

		/* 数据库操作 */
		mDbHelper = (DBHelperParticipant) DBHelperFactory.getInstance(this)
				.getDBHelperByType(DBHelperFactory.DB_TYPE_PARTICIPANT);
		int type_id = getIntent().getIntExtra("type_id", -1);
		if (type_id == -1) {
			type_id = 0;
		}
		Log.i(TAG, "type_id = " + type_id);
		mCursor = mDbHelper.fetchAllByType(type_id);
		mCAdapter = new SimpleCursorAdapter(this, R.layout.participant_item,
				mCursor, FROM, TO);
		this.setListAdapter(mCAdapter);
		
		startManagingCursor(mCursor);

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		final Bundle participant = new Bundle();

		final AdapterView.AdapterContextMenuInfo info;
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(
				R.layout.participant_type_text_dialog, null);
		/* 账户类型 */
		final Button typeBtn = (Button) textEntryView
				.findViewById(R.id.btn_ac_type);
		typeBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				final AlertDialog typeDialog = new AlertDialog.Builder(
						ParticiPantItemList.this).setTitle("选人员/机构类型")
						.setItems(mTypeArray,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										typeBtn.setText(mTypeArray[which]);
										participant.putInt("type_id", which);
									}
								}).create();

				typeDialog.show();

			}
		});
		/* 账户名称 */
		final EditText edtName = (EditText) textEntryView
				.findViewById(R.id.edt_ac_name);
		edtName.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					String name = edtName.getText().toString();
					if (name.equals("") || name.length() == 0) {
						participant.putString("name", "新建人员/机构");
					} else {
						participant.putString("name", name);
					}
				}

			}
		});
		final EditText edtRemark = (EditText) textEntryView
				.findViewById(R.id.edt_ac_remark);

		int menuId = item.getItemId();
		switch (menuId) {
		case ADD_ACOUNT_TYPE:
			Log.i("ItemList", (String) item.getTitle());
			new AlertDialog.Builder(ParticiPantItemList.this)
					// .setIcon(R.drawable.alert_dialog_icon)
					.setTitle("添加人员/机构").setView(textEntryView)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									participant.putString("remark", edtRemark
											.getText().toString());
									mDbHelper.insert(participant);
									/* 刷新数据 */
									mCAdapter.getCursor().requery();
									Log.i(TAG, "" + whichButton);

								}
							}).setNegativeButton("取消",
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

			final Cursor c = mCAdapter.getCursor();
			if (null == info) {
				c.moveToPosition(getSelectedItemPosition());
			} else {
				c.moveToPosition(info.position);
			}
			participant.putInt("type_id", c.getInt(1));
			edtName.setText(c.getString(2));
			participant.putString("name", c.getString(2));
			edtRemark.setText(c.getString(3));
			participant.putString("remark", c.getString(3));

			typeBtn.setText(mTypeArray[c.getInt(1)]);
			new AlertDialog.Builder(ParticiPantItemList.this)
					// .setIcon(R.drawable.alert_dialog_icon)
					.setTitle("编辑人员/机构").setView(textEntryView)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									participant.putString("remark", edtRemark
											.getText().toString());
									mDbHelper.update(c.getInt(0), participant);
									//
									// /* 更新视图 */
									mCAdapter.getCursor().requery();
								}
							}).setNegativeButton("取消",
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

			new AlertDialog.Builder(ParticiPantItemList.this)
			// .setIcon(R.drawable.alert_dialog_icon)
					.setTitle("删除账户类型").setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Cursor c = mCAdapter.getCursor();
									if (null == info) {
										c
												.moveToPosition(getSelectedItemPosition());
									} else {
										c.moveToPosition(info.position);
									}

									int id = c.getInt(0);
									mDbHelper.delete(id);
									/* 刷新数据 */
									mCAdapter.getCursor().requery();

									Log.i(TAG, "Delete id = " + id);
								}
							}).setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Log.i(TAG, "" + whichButton);

								}
							}).setMessage("确定删除改账户类型？").create().show();
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ADD_ACOUNT_TYPE, 0, "新建").setIcon(
				android.R.drawable.ic_menu_add);
		menu.add(MENU_GROUP_ALTERNATIVE, EDIT_ACOUNT_TYPE, 0, "edit");
		menu.add(MENU_GROUP_ALTERNATIVE, DELETE_ACOUNT_TYPE, 0, "delete");
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
		menu.setHeaderTitle(c.getString(2));

		Log.i(TAG, "id onCreateContextMenu" + info.id);

		// Add a menu item to delete and edit the note
		menu.add(0, EDIT_ACOUNT_TYPE, 0, "Edit");
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
			Log.i(TAG, "onContextItemSelected = " + c.getInt(0));
			return true;
		}
		}
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		super.onPrepareOptionsMenu(menu);

		final boolean haveItems = getListAdapter().getCount() > 0;
		Log.i(TAG, getSelectedItemPosition() + "");

		// If there are any notes in the list (which implies that one of
		// them is selected), then we need to generate the actions that
		// can be performed on the current selection. This will be a combination
		// of our own specific actions along with any extensions that can be
		// found.
		if (haveItems && (getSelectedItemPosition() != -1)) {
			menu.setGroupVisible(MENU_GROUP_ALTERNATIVE, true);

		} else {
			menu.setGroupVisible(MENU_GROUP_ALTERNATIVE, false);
		}

		return true;
	}

	@Override
	protected void onDestroy() {
		
		mDbHelper.close();
		super.onDestroy();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		curTypeId=(int)id;
	
		finish();
	}

	public int getTypeId(){
		return curTypeId;
	}

}
