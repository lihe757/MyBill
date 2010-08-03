package com.lihex.generic.ui;

import com.lihex.mybill.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Participant extends TabActivity {
private static  String[] mAcTabs;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAcTabs=getResources().getStringArray(R.array.participant);
		
		
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
      
	}
	private Intent getIntentByIndex(int index){
		Intent i=new Intent(this,ParticiPantItemList.class);
		i.putExtra("type_id", index);
		return i;
		
	}
	@Override
	public void finishFromChild(Activity child) {
		ParticiPantItemList item=(ParticiPantItemList)child;
		Intent result=new Intent();
		result.putExtra("participant_id",item.getTypeId());
		setResult(RESULT_OK,result);
		finish();
	}
	
	

}
