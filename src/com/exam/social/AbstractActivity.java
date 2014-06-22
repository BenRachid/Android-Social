package com.exam.social;

import java.io.IOException;

import org.json.JSONException;

import com.exam.helpers.CommunicationServices;
import com.exam.helpers.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public abstract class AbstractActivity extends Activity implements AsyncTaskListener{
	// PHPCommunication
	protected CommunicationServices communication;	
	protected DatabaseHelper db;
	protected SharedPreferences sharedPref;
	
	@Override
	public void onTaskComplete(String result) {
		// TODO Auto-generated method stub
		Log.d("EndTask "+ this.getClass().getName()
				,result.toString());
	}


	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	communication = new CommunicationServices(this.getApplicationContext());
    	db = new DatabaseHelper(this.getApplicationContext());
    	sharedPref = getPreferences(MODE_PRIVATE);
    	
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	new RefreshAsyncTask().execute();
            return true;
        }
        if (id == R.id.action_logout)
        {
        	communication.logoutUser(getApplicationContext());
			Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
        return super.onOptionsItemSelected(item);
    }
    class RefreshAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			try {
				Log.d("refresh","ProceedRefresh");
				communication.proceedRefresh();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}};
}