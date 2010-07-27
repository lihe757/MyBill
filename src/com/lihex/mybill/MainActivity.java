package com.lihex.mybill;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        ListView listView=(ListView)findViewById(R.id.ListView01);
        MainAdapter adapter=new MainAdapter(this);
//        listView.setAdapter(adapter);
       
    }

    public class MainAdapter extends BaseAdapter{
    	
    	private Context mContext;
    	public MainAdapter(Context context) {
    		this.mContext=context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			RelativeLayout view=(RelativeLayout)inflater.inflate(R.layout.main_list_item, null);
			
			return view;
		}
    	
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi=getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		
	return true;
		 
	}
    
    
}