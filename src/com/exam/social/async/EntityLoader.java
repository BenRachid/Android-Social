package com.exam.social.async;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.exam.models.Entity;
import com.exam.social.AsyncTaskListener;
import com.exam.social.EntityAdapter;

public abstract class EntityLoader extends AsyncTask<Object, Void, List<Entity>>{

	protected String TAG;
	private Activity activity;
	private ProgressDialog dialog;
	private AsyncTaskListener callback;
	EntityAdapter adapter;
	
	public EntityLoader(Activity act,String tag) {
		this.TAG =tag;
		this.activity = act;
		this.callback = (AsyncTaskListener)act;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(activity);
		dialog.setMessage("Loading "+TAG+" ...");
		dialog.show();
	}
	
	/*
	 * this is used in order to show information in LOGs
	 * */
    @Override
    protected void onPostExecute(List<Entity> entity){
        dialog.dismiss();
        int count = entity.size();
        Log.d(TAG, "count = " + count);
        Log.d(TAG, " PostExecute");
        adapter = new EntityAdapter(activity, count, entity) {
			
			@Override
			void populateLayout() {
				// TODO Auto-generated method stub
				
			}
		};(this, data);
        /*
        locationAdapter adapter = new locationAdapter(LocationViewer.this, android.R.layout.simple_list_item_1, addresses);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        */



    }
	
}
