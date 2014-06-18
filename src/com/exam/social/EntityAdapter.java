package com.exam.social;

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

public abstract class EntityAdapter extends ArrayAdapter<Entity> {

	Context mycontext;
	int usedLayout;
	Resources resources;
	
   public EntityAdapter(Context context,int elementViewResourceId, List<Entity> entity) {
        super(context,elementViewResourceId, entity);
        this.usedLayout=elementViewResourceId;
        mycontext = context;
		resources = context.getResources();
    }
   @Override   
   public View getView(int position, View convertView, ViewGroup parent){

       View view = convertView;

       if (view == null) {
           LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(usedLayout, null);
       }
       Entity item = getItem(position);
       if (item!= null) {
           populateLayout();
        }
       return view;
   }
   
   abstract void populateLayout();
}
