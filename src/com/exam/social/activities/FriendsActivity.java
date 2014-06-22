package com.exam.social.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exam.adapter.EntityAdapter;
import com.exam.helpers.CommunicationServices;
import com.exam.models.Comments;
import com.exam.models.Entity;
import com.exam.models.Friends;
import com.exam.social.AbstractActivity;
import com.exam.social.R;

public class FriendsActivity extends AbstractActivity{
	private Map<Entity, Integer> entityMappings;
	
	public FriendsActivity() {
		super();
		entityMappings = new LinkedHashMap<Entity, Integer>();
	}


}
