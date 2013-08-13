package com.youm7.newsapp;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SectionFragment extends Fragment implements OnItemClickListener,OnHomeArticleSelected{
	
	PullToRefreshListView mNewsScroll;
	ArrayList<NewsItem> sectionNewsList;
	ImageLoader mImageloader;
	String mSecTitle;
	String mSecID;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	SectionNewsAdapter newsAdapter;
	ImageLoaderConfiguration mLoadconfig ;
	OnArticleSelectedListener CallActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.news_category_layout,
				container, false);
	    View footer= inflater.inflate(R.layout.footer_view	, null);
		mNewsScroll=(PullToRefreshListView) rootView.findViewById(R.id.news_listview);
		mNewsScroll.getRefreshableView().addFooterView(footer);
		mNewsScroll.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					newsAdapter.UpdateSection();
					
				}
			});
		mNewsScroll.setAdapter(newsAdapter);
	    mNewsScroll.setOnItemClickListener(this);
	   
	   ((TextView) rootView.findViewById(R.id.News_category_title)).setText(mSecTitle);
	 
	   
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mSecTitle=getArguments().getString("SecTitle");
		mSecID=getArguments().getString("SecID");
		  dispoptions= new DisplayImageOptions
	        		.Builder()
	        .cacheInMemory(true)
	        .cacheOnDisc(true)
	        .build();      
	        mLoadconfig= new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).build();
		    mloadImage=ImageLoader.getInstance();
		    mloadImage.init(mLoadconfig);
		    newsAdapter=new SectionNewsAdapter(sectionNewsList, getActivity().getApplicationContext(), 2, mloadImage,ConstructURL(mSecID), this);
		    newsAdapter.UpdateSection();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		
		super.onStart();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ArticleFragment frag=new ArticleFragment();
		NewsItem ArticleToOpen=(NewsItem) newsAdapter.getItem(arg2-1);
		Bundle details=new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		frag.setArguments(details);
		
		CallActivity.onSecArticleSelected(frag,this,true);
		
	}

@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	CallActivity=(OnArticleSelectedListener) activity;
}
private String ConstructURL(String SecID)
{
	String template=  getActivity().getApplicationContext().getResources().getString(R.string.sectionapi);
	return template.replace("{SecID}", SecID);
	
	
}

@Override
public void HomeSelected(NewsItem item) {
	// TODO Auto-generated method stub
	
}

@Override
public void RefreshFinished() {
	// TODO Auto-generated method stub
	mNewsScroll.onRefreshComplete();
}



}
