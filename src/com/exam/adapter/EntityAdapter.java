package com.exam.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.exam.models.Entity;

import android.R.layout;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class EntityAdapter<T extends Entity> extends ArrayAdapter<T> {

	protected LayoutInflater inflater;
	protected Resources resources;
	
	public EntityAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		resources = context.getResources();
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent) ;
	
}
