package com.exam.helpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.exam.models.Action;
import com.exam.models.CommentLikes;
import com.exam.models.Comments;
import com.exam.models.Friends;
import com.exam.models.Notifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.method.DateTimeKeyListener;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "socialManager";

	// Table Names
	private static final String TABLE_COMMENTS = "Comments";
	private static final String TABLE_FRIENDS = "friends";
	private static final String TABLE_NOTIFICATIONS = "notifications";
	private static final String TABLE_COMMENT_LIKES = "comment_likes";
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_ACTION = "action";
	// column names
	private static final String KEY_ID = "id";
	private static final String KEY_FIRSTNAME = "first_name";
	private static final String KEY_LASTNAME = "last_name";
	private static final String KEY_NICKNAME = "nickname";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_UPDATED_AT = "updated_at";
	private static final String KEY_PERFORMER = "performer_id";
	private static final String KEY_TARGET = "target_id";
	private static final String KEY_TYPE = "type";
	private static final String KEY_STATUS = "status";
	// private static final String KEY_GENDER = "gender";
	private static final String KEY_BIRTHDATE = "birth_date";
	private static final String KEY_PARENT = "parent_id";
	private static final String KEY_CONTENT = "content";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_EXTINT = "extInt";
	private static final String KEY_ACTION = "action";
	// tables create statements
	protected static final String CREATE_TABLE_LOGIN = "CREATE TABLE "
			+ TABLE_LOGIN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_FIRSTNAME + " TEXT," + KEY_LASTNAME + " TEXT," + KEY_EMAIL
			+ " TEXT UNIQUE," + KEY_UID + " TEXT," + KEY_PHOTO + " TEXT,"
			// + KEY_GENDER + " TEXT,"
			+ KEY_BIRTHDATE + " DATETIME," + KEY_CREATED_AT + " TEXT" + ")";

	private static final String CREATE_TABLE_COMMENTS = "CREATE TABLE "
			+ TABLE_COMMENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PERFORMER + " INTEGER," + KEY_PARENT + " INTEGER,"
			+ KEY_CONTENT + " TEXT," + KEY_STATUS + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + KEY_UPDATED_AT + " DATETIME" + ")";

	private static final String CREATE_TABLE_COMMENT_LIKES = "CREATE TABLE "
			+ TABLE_COMMENT_LIKES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PERFORMER + " INTEGER," + KEY_TARGET + " INTEGER," + KEY_TYPE
			+ " INTEGER" + ")";

	private static final String CREATE_TABLE_FRIENDS = "CREATE TABLE "
			+ TABLE_FRIENDS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_FIRSTNAME + " TEXT," + KEY_LASTNAME + " TEXT," + KEY_EMAIL
			+ " TEXT UNIQUE," + KEY_CREATED_AT + " DATETIME" + KEY_PHOTO
			+ " TEXT" + ")";

	private static final String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE "
			+ TABLE_NOTIFICATIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PERFORMER + " INTEGER," + KEY_TARGET + " INTEGER," + KEY_TYPE
			+ " INTEGER" + ")";

	private static final String CREATE_TABLE_ACTION = "CREATE TABLE "
			+ TABLE_ACTION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_PERFORMER + " INTEGER," + KEY_ACTION + " TEXT," + KEY_EXTINT
			+ " INTEGER" + KEY_CREATED_AT + " DATETIME" + KEY_STATUS
			+ " INTEGER" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
	public void createFriendsFromJsonObject(String entityType,JSONObject json_entity) throws JSONException
	{
		int id=json_entity.getInt(KEY_ID);
		Log.e(entityType,"Ajout d'un Ami");
			String nickname= json_entity.getString(KEY_FIRSTNAME) + json_entity.getString(KEY_LASTNAME);  
			String email =json_entity.getString(KEY_EMAIL);
			String photo = json_entity.getString(KEY_PHOTO);;
			createFriend(new Friends(id, nickname, email, photo));
			Log.e("FRIENDS","Ajout de "+nickname+" reussi");
/*
		if(entityType == "Comments")
		{
			int performer=json_entity.getInt(KEY_PERFORMER);
			int parent=json_entity.getInt(KEY_PARENT);
			int status=json_entity.getInt(KEY_STATUS);
			String created_at=json_entity.getString(KEY_CREATED_AT);
			String updated_at=json_entity.getString(KEY_UPDATED_AT);
			String content=json_entity.getString(KEY_CONTENT);
			createComment(new Comments(id, performer, parent, status, created_at, updated_at, content));
		}
		if(entityType == "CommentsLikes")
		{
			int performer=json_entity.getInt(KEY_PERFORMER);
			int target=json_entity.getInt(KEY_TARGET);
			int type=json_entity.getInt(KEY_TYPE);
			createCommentLikes(new CommentLikes(id, performer, target, type));
		}
		if(entityType == "Notifications")
		{
			int performer=json_entity.getInt(KEY_PERFORMER);
			int target=json_entity.getInt(KEY_TARGET);
			int type=json_entity.getInt(KEY_TYPE);
			createNotification(new Notifications(id, performer, target, type));
		}
		*/
	}
	/**
	 *
	 * get datetime
	 * */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_LOGIN);
		Log.d("Database",CREATE_TABLE_LOGIN);
		db.execSQL(CREATE_TABLE_COMMENTS);
		Log.d("Database",CREATE_TABLE_COMMENTS);
		db.execSQL(CREATE_TABLE_COMMENT_LIKES);
		Log.d("Database",CREATE_TABLE_COMMENT_LIKES);
		db.execSQL(CREATE_TABLE_FRIENDS);
		Log.d("Database",CREATE_TABLE_FRIENDS);
		db.execSQL(CREATE_TABLE_NOTIFICATIONS);
		Log.d("Database",CREATE_TABLE_NOTIFICATIONS);
		db.execSQL(CREATE_TABLE_ACTION);
		Log.d("Database",CREATE_TABLE_ACTION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		Log.d("Database Drop",TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		Log.d("Database Drop",TABLE_COMMENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT_LIKES);
		Log.d("Database Drop",TABLE_COMMENT_LIKES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
		Log.d("Database Drop",TABLE_FRIENDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
		Log.d("Database Drop",TABLE_NOTIFICATIONS);
		// we could avoid to drop this table in order to keep idle operations
		// but it will be difficult to keep persistance
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTION);
		Log.d("Database Drop",TABLE_ACTION);
		onCreate(db);
	}

	// COMMENTS ...

	/*
	 * Creating a Comments
	 */
	public long createComment(Comments comment) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CONTENT, comment.getContent());
		values.put(KEY_STATUS, comment.getStatus());
		values.put(KEY_PARENT, comment.getParent());
		values.put(KEY_PARENT, comment.getPerformer());
		values.put(KEY_CREATED_AT, getDateTime());
		// values.put(KEY_UPDATED_AT, getDateTime());

		// insert row
		long comments_id = db.insert(TABLE_COMMENTS, null, values);

		return comments_id;
	}

	/*
	 * get single Comments
	 */
	public Comments getComment(long comments_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_COMMENTS + " WHERE "
				+ KEY_ID + " = " + comments_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Comments td = new Comments();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setContent((c.getString(c.getColumnIndex(KEY_CONTENT))));
		td.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
		td.setParent(c.getInt(c.getColumnIndex(KEY_PARENT)));
		td.setPerformer(c.getInt(c.getColumnIndex(KEY_PERFORMER)));
		td.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)));
		td.setUpdated_at(c.getString(c.getColumnIndex(KEY_UPDATED_AT)));

		return td;
	}

	/**
	 * getting all comments
	 * */
	public List<Comments> getAllComments() {
		List<Comments> comments = new ArrayList<Comments>();
		String selectQuery = "SELECT  * FROM " + TABLE_COMMENTS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Comments td = new Comments();
				td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				td.setContent((c.getString(c.getColumnIndex(KEY_CONTENT))));
				td.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
				td.setParent(c.getInt(c.getColumnIndex(KEY_PARENT)));
				td.setPerformer(c.getInt(c.getColumnIndex(KEY_PERFORMER)));
				td.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)));
				td.setUpdated_at(c.getString(c.getColumnIndex(KEY_UPDATED_AT)));
				// adding to Comments list
				comments.add(td);
			} while (c.moveToNext());
		}

		return comments;
	}

	/*
	 * getting Comments count
	 */
	public int getCommentsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_COMMENTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * Updating a Comment
	 */
	public int updateComment(Comments comment) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CONTENT, comment.getContent());
		values.put(KEY_STATUS, comment.getStatus());
		values.put(KEY_UPDATED_AT, comment.getUpdated_at());
		// updating row
		return db.update(TABLE_COMMENTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(comment.getId()) });
	}

	/*
	 * Deleting a Comments
	 */
	public void deleteComment(long comment_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		// *************HERE VERIFY CONSTRAINT OF LIKED OR COMMENTED**********//
		db.delete(TABLE_COMMENTS, KEY_ID + " = ?",
				new String[] { String.valueOf(comment_id) });
	}

	// COMMENT_LIKES ...

	/*
	 * Creating a Like
	 */
	public long createCommentLikes(CommentLikes commentLikes) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PERFORMER, commentLikes.getPerformer());
		values.put(KEY_TARGET, commentLikes.getTarget());
		values.put(KEY_TYPE, commentLikes.getType());

		// insert row
		long like_id = db.insert(TABLE_COMMENT_LIKES, null, values);

		return like_id;
	}

	/*
	 * get single Like
	 */
	public CommentLikes getLike(long like_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_COMMENT_LIKES
				+ " WHERE " + KEY_ID + " = " + like_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		CommentLikes td = new CommentLikes();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setPerformer(c.getInt(c.getColumnIndex(KEY_PARENT)));
		td.setTarget(c.getInt(c.getColumnIndex(KEY_TARGET)));
		td.setType(c.getInt(c.getColumnIndex(KEY_TYPE)));

		return td;
	}

	/**
	 * getting all LikesByComments
	 * */
	public List<CommentLikes> getAllLikeByComments(long comment_id) {
		List<CommentLikes> likes = new ArrayList<CommentLikes>();
		String selectQuery = "SELECT  * FROM " + TABLE_COMMENT_LIKES
				+ " WHERE " + KEY_TARGET + " = " + comment_id;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				CommentLikes td = new CommentLikes();
				td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				td.setPerformer(c.getInt(c.getColumnIndex(KEY_PARENT)));
				td.setTarget(c.getInt(c.getColumnIndex(KEY_TARGET)));
				td.setType(c.getInt(c.getColumnIndex(KEY_TYPE)));

				// adding to Likes list
				likes.add(td);
			} while (c.moveToNext());
		}

		return likes;
	}

	/*
	 * getting Comment'sLIKES count
	 */
	public int getCommentLikesCount(long comment_id) {
		String countQuery = "SELECT  * FROM " + TABLE_COMMENT_LIKES + " WHERE "
				+ KEY_TARGET + " = " + comment_id;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * Updating a like Type
	 */
	public int updateLike(CommentLikes like) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, like.getType());
		// updating row
		return db.update(TABLE_COMMENT_LIKES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(like.getId()) });
	}

	/*
	 * Deleting a LIKE
	 */
	public void UnLike(long like_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COMMENT_LIKES, KEY_ID + " = ?",
				new String[] { String.valueOf(like_id) });
	}

	// FRIENDS...

	/*
	 * Creating a Friend
	 */
	public long createFriend(Friends friend) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRSTNAME, friend.getName());
		values.put(KEY_LASTNAME, friend.getName());
		values.put(KEY_EMAIL, friend.getEmail());
		values.put(KEY_PHOTO, friend.getPhoto());
		// insert row
		long friend_id = db.insert(TABLE_FRIENDS, null, values);

		return friend_id;
	}

	/*
	 * get single friend
	 */
	public Friends getFriend(long friend_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_FRIENDS + " WHERE "
				+ KEY_ID + " = " + friend_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Friends td = new Friends();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
		td.setName(c.getString(c.getColumnIndex(KEY_FIRSTNAME)));
		td.setName(c.getString(c.getColumnIndex(KEY_LASTNAME)));
		td.setPhoto(c.getString(c.getColumnIndex(KEY_PHOTO)));
		return td;
	}

	/**
	 * getting all friends
	 * */
	public List<Friends> getAllFriends() {
		List<Friends> friends = new ArrayList<Friends>();
		String selectQuery = "SELECT  * FROM " + TABLE_FRIENDS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Friends td = new Friends();
				td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				td.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
				td.setName(c.getString(c.getColumnIndex(KEY_FIRSTNAME)));
				td.setName(c.getString(c.getColumnIndex(KEY_LASTNAME)));
				td.setPhoto(c.getString(c.getColumnIndex(KEY_PHOTO)));
				// adding to Comments list
				friends.add(td);
			} while (c.moveToNext());
		}

		return friends;
	}

	/*
	 * getting Friends count
	 */
	public int getFriendsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_FRIENDS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * Deleting a friend
	 */
	public void deleteFriend(long friend_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FRIENDS, KEY_ID + " = ?",
				new String[] { String.valueOf(friend_id) });
	}

	// NOTIFICATIONS...

	/*
	 * Creating a Notifcation
	 */
	public long createNotification(Notifications notification) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PERFORMER, notification.getPerformer());
		values.put(KEY_TARGET, notification.getTarget());
		values.put(KEY_TYPE, notification.getType());

		// insert row
		long comments_id = db.insert(TABLE_COMMENTS, null, values);

		return comments_id;
	}

	/*
	 * get single Notification
	 */
	public Notifications getNotification(long notification_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS
				+ " WHERE " + KEY_ID + " = " + notification_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Notifications td = new Notifications();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setType(c.getInt(c.getColumnIndex(KEY_TYPE)));
		td.setPerformer(c.getInt(c.getColumnIndex(KEY_PERFORMER)));
		td.setTarget(c.getInt(c.getColumnIndex(KEY_TARGET)));

		return td;
	}

	/**
	 * getting all Notifications
	 * */
	public List<Notifications> getAllNotifications() {
		List<Notifications> notifications = new ArrayList<Notifications>();
		String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Notifications td = new Notifications();
				td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				td.setType(c.getInt(c.getColumnIndex(KEY_TYPE)));
				td.setPerformer(c.getInt(c.getColumnIndex(KEY_PERFORMER)));
				td.setTarget(c.getInt(c.getColumnIndex(KEY_TARGET))); // adding
																		// to
																		// Comments
																		// list
				notifications.add(td);
			} while (c.moveToNext());
		}

		return notifications;
	}

	/*
	 * getting Notifications count
	 */
	public int getNotificationsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * Deleting a Notification
	 */
	public void deleteNotification(long notification_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NOTIFICATIONS, KEY_ID + " = ?",
				new String[] { String.valueOf(notification_id) });
	}

	// ACTION
	/*
	 * Creating a Action
	 */
	public long createAction(Action action) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PERFORMER, action.getPerformer());
		values.put(KEY_EXTINT, action.getExtInt());
		values.put(KEY_ACTION, action.getAction());
		values.put(KEY_STATUS, action.getStatus());
		values.put(KEY_CREATED_AT, action.getPerformed_at());

		// insert row
		long comments_id = db.insert(TABLE_COMMENTS, null, values);

		return comments_id;
	}

	/*
	 * get single Action
	 */
	public Action getAction(long action_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS
				+ " WHERE " + KEY_ID + " = " + action_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Action td = new Action();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setExtInt(c.getInt(c.getColumnIndex(KEY_EXTINT)));
		td.setPerformer(c.getInt(c.getColumnIndex(KEY_PERFORMER)));
		td.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
		td.setPerformed_at(c.getString(c.getColumnIndex(KEY_ACTION)));

		return td;
	}

	/**
	 * getting all Action
	 * */
	public List<Action> getAllAction() {
		List<Action> actions = new ArrayList<Action>();
		String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Action td = new Action();
				td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				td.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
				td.setPerformer(c.getInt(c.getColumnIndex(KEY_PERFORMER)));
				td.setPerformed_at(c.getString(c.getColumnIndex(KEY_TARGET)));
				td.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)));
				actions.add(td);
			} while (c.moveToNext());
		}

		return actions;
	}

	/*
	 * getting Actions count
	 */
	public int getActionsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * Deleting a Action
	 */
	public void deleteAction(long action_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NOTIFICATIONS, KEY_ID + " = ?",
				new String[] { String.valueOf(action_id) });
	}

	/*
	 * + KEY_FIRSTNAME+ " TEXT," + KEY_LASTNAME+ " TEXT," + KEY_EMAIL +
	 * " TEXT UNIQUE," + KEY_UID + " TEXT," + KEY_PHOTO + " TEXT," + KEY_GENDER
	 * + " TEXT," + KEY_BIRTHDATE + " DATETIME," + KEY_CREATED_AT + " TEXT" +
	 */
	// LOGIN ...

	/**
	 * Storing user details in database
	 * */
	public void addUser(String first_name, String last_name, String email,
			String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRSTNAME, first_name); // Name
		values.put(KEY_LASTNAME, last_name); // Name
		values.put(KEY_EMAIL, email); // Email
		// values.put(KEY_GENDER, gender); // Gender
		// values.put(KEY_PHOTO, photo); // Photo
		values.put(KEY_UID, uid); // UID
		values.put(KEY_CREATED_AT, created_at); // Created At
		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
		Log.d("Database", values.toString());
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			user.put("first_name", cursor.getString(1));
			user.put("last_name", cursor.getString(2));
			user.put("email", cursor.getString(3));
			user.put("uid", cursor.getString(4));
			user.put("created_at", cursor.getString(5));
		}
		cursor.close();
		// return user
		return user;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.delete(TABLE_COMMENTS, null, null);
		db.delete(TABLE_COMMENT_LIKES, null, null);
		db.delete(TABLE_FRIENDS, null, null);
		db.delete(TABLE_NOTIFICATIONS, null, null);
		db.close();
	}

	/**
	 * completely reset database
	 * */
	public void resetDatabase() {
		SQLiteDatabase db = this.getWritableDatabase();
		onUpgrade(db, db.getVersion(), db.getVersion());
	}
}
