package com.lihex.mybill;

import com.lihex.generic.ui.AccountCategory;
import com.lihex.generic.ui.CalendarActivity;
import com.lihex.generic.ui.Participant;
import com.lihex.generic.ui.UseCategoryActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	private Button mBtnDate;
	private Button mBtnAccount;
	private Button mBtnUsage;
	private Button mBtnParticipant;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
       
    }

   private void initView(){
	   mBtnDate=(Button)findViewById(R.id.btn_start_time);
	   mBtnDate.setOnClickListener(new Button.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(MainActivity.this,CalendarActivity.class));
			
		}
	});
	   mBtnAccount=(Button)findViewById(R.id.btn_acount);
	   mBtnAccount.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,AccountCategory.class));
				
			}
		});
	   mBtnUsage=(Button)findViewById(R.id.btn_category);
	   mBtnUsage.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,UseCategoryActivity.class));
				
			}
		});
	   mBtnParticipant=(Button)findViewById(R.id.btn_participant);
	   mBtnParticipant.setOnClickListener(new Button.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(MainActivity.this,Participant.class));
			
		}
	});
   }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi=getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		
	return true;
		 
	}
    
    
}