package com.example.android_ai;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_ai_bean.ChatMessage;
import com.example.android_ai_bean.ChatMessage.Type;

public class ChatMessageAdapter extends BaseAdapter {
	public Context context;
	private LayoutInflater mInflater;
	private List<ChatMessage> mDatas;

	public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
		this.mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMessage chatMessage = mDatas.get(position);
		if (chatMessage.getType() == Type.INCOMING) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMessage chatMessage = mDatas.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			// 通过itemType设置不同的布局
			if (getItemViewType(position) == 0) {
				convertView = mInflater.inflate(R.layout.item_from_msg, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView
						.findViewById(R.id.id_from_msg_date);
				viewHolder.mMsg = (TextView) convertView
						.findViewById(R.id.id_from_msg_info);

				viewHolder.mFromImg = (ImageView) convertView
						.findViewById(R.id.id_from_img);
				viewHolder.mFromImg.setOnClickListener(listener);

			} else {
				convertView = mInflater.inflate(R.layout.item_to_msg, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView
						.findViewById(R.id.id_to_msg_date);
				viewHolder.mMsg = (TextView) convertView
						.findViewById(R.id.id_to_msg_info);

				viewHolder.mToImg = (ImageView) convertView
						.findViewById(R.id.id_to_img);

				viewHolder.mToImg.setOnClickListener(listener);
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 设置数据
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.mDate.setText(df.format(chatMessage.getDate()));
		viewHolder.mMsg.setText(chatMessage.getMsg());

		return convertView;
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent();
			i.setAction(Intent.ACTION_PICK);
			i.setType("image/*");
			((MainActivity) context).startActivityForResult(i, 100);
		}
	};

	private final class ViewHolder {
		TextView mDate;
		TextView mMsg;
		ImageView mToImg;
		ImageView mFromImg;

		// @Override
		// protected void onActivityResult(int requestCode, int resultCode,
		// Intent data) {
		// // TODO Auto-generated method stub
		// System.out.println("onActivityResult");
		// Uri imageUri = data.getData();// 传回一个URI路径O
		// mToImg.setImageURI(imageUri);
		// mFromImg.setImageURI(imageUri);
		//
		// }
	}
}
