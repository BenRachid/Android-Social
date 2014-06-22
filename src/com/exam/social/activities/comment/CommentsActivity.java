package com.exam.social.activities.comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exam.adapter.CommentsAdapter;
import com.exam.adapter.EntityAdapter;
import com.exam.helpers.CommunicationServices;
import com.exam.models.Comments;
import com.exam.models.Entity;
import com.exam.social.AbstractActivity;
import com.exam.social.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CommentsActivity extends AbstractActivity {
	
	CommentsAdapter entityAdapter;
	List<Comments> comments;

	OnItemClickListener onListClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> listView, View rowView, int position,
				long id) {
			Comments comment = entityAdapter.getItem(position);
			Log.i("OnItemClick",comment.toString());
			//Here acces to Likes Or Sub Comment IF it's a Post
			comments.add(new Comments(2, 1, 1, 1, null, null, "TEST"));
			entityAdapter.notifyDataSetChanged();
		}
	};
	
	public CommentsActivity() {
		super();
		comments=new ArrayList<Comments>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.all_comments);
		comments.add(new Comments(1, 1, 1, 1, null, null, "TEST"));
		Log.i("CommentsActivity",comments.toString());
		entityAdapter = new CommentsAdapter(this, R.layout.comment, comments);
		Log.i("CommentsActivity","GGs");
	        ListView listView = (ListView) findViewById(R.id.list_comment);
	        listView.setAdapter(entityAdapter);
			Log.i("CommentsActivity","INITIALIZE");
	        listView.setOnItemClickListener(onListClick);
		
		
	}
}
