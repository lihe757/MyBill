package com.lihex.generic.ui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.lihex.mybill.R;

public class AccountCategory extends TabActivity {
	private static  String[] mAcTabs;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAcTabs=getResources().getStringArray(R.array.acount_type);
		
		
		final TabHost tabHost=getTabHost();
		
        tabHost.addTab(tabHost.newTabSpec(mAcTabs[0])
                .setIndicator(mAcTabs[0])
                .setContent(getIntentByIndex(0)));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator(mAcTabs[1])
                .setContent(getIntentByIndex(1)));
        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator(mAcTabs[2])
                .setContent(getIntentByIndex(2)));
        tabHost.addTab(tabHost.newTabSpec("tab4")
                .setIndicator(mAcTabs[3])
                .setContent(getIntentByIndex(3)));
	}
	private Intent getIntentByIndex(int index){
		Intent i=new Intent(this,ItemList.class);
		i.putExtra("type", mAcTabs[index]);
		return i;
		
	}

	@Override
	public void finishFromChild(Activity child) {
		ItemList l=(ItemList)child;
		Log.i("AccountCategory","child is finished !");
		Intent result =new Intent();
		result.putExtra("account_id", l.getCurAccountTypeId());
		
		setResult(RESULT_OK,result);
		super.finishFromChild(child);
	}
	
	
	
}
