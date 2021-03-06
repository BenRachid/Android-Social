package com.exam.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.exam.models.Comments;
import com.exam.social.R;

public class CommentsAdapter extends EntityAdapter<Comments> {

	public CommentsAdapter(Context context, int resource, List<Comments> objects) {
		super(context, resource, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.comment, null);
		}
		
		Comments comment= getItem(position);
//		ImageView image = (ImageView) convertView.findViewById(R.id.icon);
//		if (afaire.isFait()) {
//			image.setImageDrawable(resources.getDrawable(R.drawable.ok));
//		} else {
//			image.setImageDrawable(resources.getDrawable(R.drawable.notok));
//		}
		TextView nikName= (TextView) convertView.findViewById(R.id.comment_nickname);
		TextView likes= (TextView) convertView.findViewById(R.id.comment_like);
		TextView comments= (TextView) convertView.findViewById(R.id.comment_comment);
		TextView content = (TextView) convertView.findViewById(R.id.comment_content);
		content.setText(comment.getContent());
		nikName.setText("RACHID");
		likes.setText(comment.getLikesNumber());
		comments.setText(comment.getCommentsNumber());
		Log.i("GETVIEW_SUCSESS", comment.getContent());
		return convertView;
	}

}
