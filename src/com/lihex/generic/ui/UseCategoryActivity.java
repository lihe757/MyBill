package com.lihex.generic.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

public class UseCategoryActivity extends TabActivity implements TabContentFactory{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final TabHost tabHost=getTabHost();
		
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("支出")
                .setContent(new Intent(UseCategoryActivity.this,UCItemList.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("收入")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("转账")
                .setContent(this));
        
	}

	@Override
	public View createTabContent(String tag) {
		TextView textView=new TextView(this);
		textView.setText(tag);
		return textView;
	}

}
