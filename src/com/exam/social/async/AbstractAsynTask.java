package com.exam.social.async;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.exam.adapter.EntityAdapter;
import com.exam.models.Entity;
import com.exam.social.AsyncTaskListener;

public abstract class AbstractAsynTask<T1,T2,T3> extends AsyncTask<T1, T2, T3>{

	
	protected String TAG;
	protected Activity activity;
	protected ProgressDialog dialog;
	protected AsyncTaskListener callback;
	//protected EntityAdapter adapter;
	
	public AbstractAsynTask(Activity act,String tag) {
		this.TAG =tag;
		this.activity = act;
		this.callback = (AsyncTaskListener)act;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(activity.getApplicationContext());
		dialog.setMessage("Executing "+TAG+" ...");
		dialog.show();
		Log.d(TAG, " PreExecute");
	}
	@Override
	protected void onPostExecute(T3 result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.dismiss();
	}
	
	
    /*{
        dialog.dismiss();
        int count = entity.size();
        Log.d(TAG, "count = " + count);
        Log.d(TAG, " PostExecute");


        locationAdapter adapter = new locationAdapter(LocationViewer.this, android.R.layout.simple_list_item_1, addresses);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);




    }
	*/
}
