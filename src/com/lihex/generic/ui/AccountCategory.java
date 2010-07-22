package com.lihex.generic.ui;

import com.lihex.mybill.R;
import com.lihex.mybill.data.DBHelper;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class AccountCategory extends TabActivity implements TabHost.TabContentFactory{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.account_category);
		final TabHost tabHost=getTabHost();
		
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("tab1")
                .setContent(new Intent(this,ItemList.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("tab2")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("tab3")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab4")
                .setIndicator("tab4")
                .setContent(this));
        DBHelper dbHelper=new DBHelper(this);
        dbHelper.establishDb();
	}
	  /** {@inheritDoc} */
    public View createTabContent(String tag) {
    	LayoutInflater mInflater = (LayoutInflater) this
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	RelativeLayout rl=(RelativeLayout)mInflater.inflate(R.layout.tab_list_item, null);

    	return rl;
    }
}
