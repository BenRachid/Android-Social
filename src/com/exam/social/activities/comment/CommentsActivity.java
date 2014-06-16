package com.exam.social.activities.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exam.helpers.JSONParser;
import com.exam.social.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CommentsActivity extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> objectsList;

	// url to get all objects list
	private static String url_all_objects = "http://10.0.2.2/android_connect/get_all_objects.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_OBJECTS = "objects";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";

	// objects JSONArray
	JSONArray objects = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);

		// Hashmap for ListView
		objectsList = new ArrayList<HashMap<String, String>>();

		// Loading objects in Background Thread
		new LoadAllProducts().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single object
		// launching Edit Product Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						EditCommentActivity.class);
				// sending pid to next activity
				in.putExtra(TAG_PID, pid);
				
				// starting new activity and expecting some response back
				startActivityForResult(in, 100);
			}
		});

	}

	// Response from Edit Product Activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received 
			// means user edited/deleted object
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	/**
	 * Background Async Task to Load all object by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CommentsActivity.this);
			pDialog.setMessage("Loading objects. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All objects from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_objects, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// objects found
					// Getting Array of Products
					objects = json.getJSONArray(TAG_OBJECTS);

					// looping through All Products
					for (int i = 0; i < objects.length(); i++) {
						JSONObject c = objects.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String name = c.getString(TAG_NAME);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_PID, id);
						map.put(TAG_NAME, name);

						// adding HashList to ArrayList
						objectsList.add(map);
					}
				} else {
					// no objects found
					// Launch Add New object Activity
					Intent i = new Intent(getApplicationContext(),
							NewCommentActivity.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all objects
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							CommentsActivity.this, objectsList,
							R.layout.list_item, new String[] { TAG_PID,
									TAG_NAME},
							new int[] { R.id.pid, R.id.name });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}