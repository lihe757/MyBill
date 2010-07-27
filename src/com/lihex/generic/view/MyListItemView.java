package com.lihex.generic.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyListItemView extends RelativeLayout {
	private int  mImgLeft;
	private ImageView mImgViewLeft;
	private int mImgRight;
	private ImageView mImgViewRight;
	private int mTextLeft;
	private String mTextLeftStr;
	private TextView mTextViewLeft;
	private int mTextRight;
	private String mTextRightStr;
	private String mTextViewRight;
	
	private Context mContext;
	public MyListItemView(Context context,int imgLeft,int imgRight,int txtLeft,int txtRight) {
		super(context);
	}
	public MyListItemView(Context context,int imgLeft,int imgRight,String txtLeft,String txtRight) {
		super(context);
		this.mContext=context;
		this.mImgLeft=imgLeft;
		this.mImgRight=imgRight;
		this.mTextLeftStr=txtLeft;
		this.mTextRightStr=txtRight;
		initView();
	}
	
	/**
	 * 初始化视图
	 */
	private void initView(){
//		this.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		TextView textView=new TextView(mContext);
		textView.setText("Hello World!");
//		TextView textView2=new TextView(mContext);
//		textView2.setText("sdfdf");
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
		addView(textView,lp);
	}

}
