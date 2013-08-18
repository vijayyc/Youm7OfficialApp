package com.youm7.newsapp;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youm7.newsapp.NewsLoader.TaskCompletedListener;



public class MixedNewsAdapter implements  TaskCompletedListener,OnClickListener{

	OnHomeArticleSelected HomeFrag;
	
	private volatile  ArrayList<NewsItem>  mixednews;
	NewsLoader mLoader;
	LinearLayout RightColumn;
	LinearLayout LeftColumn;
	LinearLayout holder;
	private Context context;
	private int NumberOfSections;
	int i=0;
	private ImageLoader mImageloader;
	private LayoutInflater inflater;
	private String[] mSectionNames;
	String mSectionIds;
	volatile int j=0;
	OnArticleSelectedListener Articleclicklistener;
	static DisplayImageOptions dispoptions= new DisplayImageOptions.Builder()
	.cacheInMemory(true)
	.cacheOnDisc(true)
	.build(); 
	public MixedNewsAdapter(
			Context context, ImageLoader loader ,Fragment Frag) {
		super();
		mImageloader=loader;
		HomeFrag=(OnHomeArticleSelected) Frag;
		
		this.context = context;
		mLoader= new NewsLoader();
		mSectionIds=context.getResources().getString(R.string.homesectionnews);
		mSectionNames= context.getResources().getStringArray(R.array.home_sectionNames);
		
		mixednews=new ArrayList<NewsItem>();
		inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		UpdateSections();
	}
	public void setAdapter(View holder)
	{
		if(holder!=null)
		{
		this.holder = (LinearLayout) holder;
		RightColumn=(LinearLayout) holder.findViewById(R.id.rightcolumnView);
		LeftColumn=(LinearLayout) holder.findViewById(R.id.leftcolumnView);
		}
		
		try {
			FillLayout();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HomeFrag.RefreshFinished();
	}
	public void UpdateSections()
	{
		
		
		mLoader=new NewsLoader();
		mLoader.loadSection(mSectionIds, this, 100);
		
		
	}
	@Override
	public  synchronized void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
	
	mixednews=result;
	
		setAdapter(null);
		
	
	
		
		
	}
	private void FillLayout() {
		// TODO Auto-generated method stub
		//fill the right comlumn
		int j= mixednews.size();
		RightColumn.removeAllViews();
		LeftColumn.removeAllViews();
		for(int i=0;i<j;i+=10)
		{
			RelativeLayout temp=	(RelativeLayout) inflater.inflate(R.layout.homenewssection, null);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setText(mixednews.get(i).NewsTitle);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setTag(mixednews.get(i));
			((TextView)(temp.findViewById(R.id.SecTitleTV))).setText(mSectionNames[i/5]);
			
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setText(mixednews.get(i+1).NewsTitle);
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setTag(mixednews.get(i+1));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setText(mixednews.get(i+2).NewsTitle);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setTag(mixednews.get(i+2));
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setText(mixednews.get(i+3).NewsTitle);
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setTag(mixednews.get(i+3));
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setText(mixednews.get(i+4).NewsTitle);
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setTag(mixednews.get(i+4));
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setOnClickListener(this);
			ImageView tempimg=(ImageView) temp.findViewById(R.id.secimg);
		    mImageloader.displayImage(mixednews.get(i).NewsImgLink,tempimg,dispoptions);
		    tempimg.setTag(mixednews.get(i));
		    tempimg.setOnClickListener(this);
		    
			RightColumn.addView(temp);
		}
		// fill left column
		for(int i=5;i<j;i+=10)
		{
			RelativeLayout temp=	(RelativeLayout) inflater.inflate(R.layout.homenewssection, null);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setText(mixednews.get(i).NewsTitle);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setTag(mixednews.get(i));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setText(mixednews.get(i+1).NewsTitle);
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setTag(mixednews.get(i+1));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setText(mixednews.get(i+2).NewsTitle);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setTag(mixednews.get(i+2));
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setText(mixednews.get(i+3).NewsTitle);
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setTag(mixednews.get(i+3));
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setText(mixednews.get(i+4).NewsTitle);
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setTag(mixednews.get(i+4));
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.SecTitleTV))).setText(mSectionNames[i/5]);
		ImageView tempimg=(ImageView) temp.findViewById(R.id.secimg);
		    mImageloader.displayImage(mixednews.get(i).NewsImgLink,tempimg,dispoptions);
		    tempimg.setTag(mixednews.get(i));
		    tempimg.setOnClickListener(this);
		    LeftColumn.addView(temp);
			
		}
		
	}
	

private String ConstructURL(String SecID)
{
	String template=  context.getResources().getString(R.string.sectionapi);
	return template.replace("{SecID}", SecID);
	
	
}



@Override
public void onClick(View v) {
	
	HomeFrag.HomeSelected((NewsItem) v.getTag());
	
}

}
