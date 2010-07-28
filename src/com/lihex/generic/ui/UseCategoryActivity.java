package com.lihex.generic.ui;

import com.lihex.mybill.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

public class UseCategoryActivity extends TabActivity  {
	private static  String[] mAcTabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAcTabs=getResources().getStringArray(R.array.usage_type);
		
		final TabHost tabHost=getTabHost();
		

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator(mAcTabs[0])
                .setContent(getIntentByIndex(0)));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(mAcTabs[1])
                .setContent(getIntentByIndex(1)));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator(mAcTabs[2])
                .setContent(getIntentByIndex(2)));
       
	}

	private Intent getIntentByIndex(int index){
		Intent i=new Intent(UseCategoryActivity.this,UCItemList.class);
		i.putExtra("type", mAcTabs[index]);
		return i;
	}
	
	

}
