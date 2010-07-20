package com.lihex.mybill;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi=getMenuInflater();
		mi.inflate(R.menu.menu, menu);
		
	return true;
		 
	}
    
    
}