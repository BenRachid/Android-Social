package com.exam.social.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import com.exam.helpers.CommunicationServices;
import com.exam.helpers.DatabaseHelper;
import com.exam.models.Comments;
import com.exam.models.Friends;
import com.exam.models.Notifications;
import com.exam.social.AbstractActivity;
import com.exam.social.AsyncTaskListener;
import com.exam.social.R;
import com.exam.social.LoginActivity;
import com.exam.social.activities.comment.CommentsActivity;
import com.exam.social.activities.comment.NewCommentActivity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class DashboardActivity extends AbstractActivity { 
	

	// Progress Dialog
	private ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> objectsList;
	private int uid;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	communication = new CommunicationServices(this);
    	Log.e("Dashboard","TEST");
    	if( communication.isUserLoggedIn(getApplicationContext()))
    	{
    		uid= sharedPref.getInt("uid", 0);
    		DatabaseHelper db=new DatabaseHelper(this);
    		if(db.getRowCount()==0)
    		{
    			new LoadAllProducts().execute();
    		}
    			
    		setContentView(R.layout.activity_dashboard);
        }else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }    
    }


    
   //Here Add an internal load operation executed on background
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DashboardActivity.this);
			pDialog.setMessage("Loading objects. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		/**
		 * getting All objects from url
		 * */
		protected String doInBackground(String... args) {
			
			// Check your log cat for JSON reponse
			communication.getFriends(uid);
			communication.getComments(uid);
			communication.getNotifications(uid);
			
			List<Comments> comments=db.getAllComments();
			Log.d("All Comments: ", + comments.size() + " MERMBERS " + comments.toString());
			
			List<Friends> friends=db.getAllFriends();
			Log.d("All Friends: ", + friends.size() + " MERMBERS " + friends.toString());
			
			List<Notifications> notifications=db.getAllNotifications();
			Log.d("All Notifcations: ", + notifications.size() + " MERMBERS " + notifications.toString());
			return null;
		}

//		/**
//		 * After completing background task Dismiss the progress dialog
//		 * **/
//		protected void onPostExecute(String file_url) {
//			// dismiss the dialog after getting all objects
//			pDialog.dismiss();
//			// updating UI from Background Thread
//			runOnUiThread(new Runnable() {
//				public void run() {
//					/**
//					 * Updating parsed JSON data into ListView
//					 * */
//					ListAdapter adapter = new SimpleAdapter(
//							DashboardActivity.this, objectsList,
//							R.layout.list_item, new String[] { TAG_PID,
//									TAG_NAME},
//							new int[] { R.id.pid, R.id.name });
//				}
//			});
//
//		}

	}



}
