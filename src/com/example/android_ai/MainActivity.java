package com.example.android_ai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android_ai_bean.ChatMessage;
import com.example.android_ai_bean.ChatMessage.Type;
import com.example.android_ai_util.HttpUtil;

public class MainActivity extends Activity {

	private ListView mMsgs;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;

	private ImageView mToImg;
	private ImageView mFromImg;

	private EditText mInputMsg;
	private Button mSendMsg;

	// 刷新listview界面
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 等待接收子线程数据的返回
			ChatMessage fromChatMessage = (ChatMessage) msg.obj;
			// 把Message加入listview数据集中
			mDatas.add(fromChatMessage);
			// 数据更新后自动scroll到底部
			mMsgs.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
			// 更新adapter 表示数据源发生变化
			mAdapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();

		initDatas();

		// 初始化事件
		initListener();
	}

	// private View.OnClickListener listener;

	private void initListener() {

		// listenerAdapter = new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent i = new Intent();
		// i.setAction(Intent.ACTION_PICK);
		// i.setType("image/*");
		// MainActivity.this.startActivityForResult(i, 100);
		//
		// }
		// };

		// this.mToImg.setOnClickListener(listenerAdapter);
		// this.mFromImg.setOnClickListener(listenerAdapter);

		mSendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击发送按钮
				final String toMsg = mInputMsg.getText().toString();
				if (TextUtils.isEmpty(toMsg)) {
					// Toast.makeText(MainActivity.this, "消息不能为空",
					// Toast.LENGTH_SHORT).show();

					return;
				}

				// 点击发送按钮后把消息封装成ChatMessage
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();

				// 输入框文本清空
				mInputMsg.setText("");

				// 网络操作用子线程 把消息传给Handler
				new Thread() {
					public void run() {
						ChatMessage fromMessage = HttpUtil.sendMessage(toMsg);
						// 得到一个Message
						Message m = Message.obtain();
						// 给obj赋值
						m.obj = fromMessage;
						mHandler.sendMessage(m);
					};
				}.start();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK && requestCode == 100) {
			System.out.println("onActivityResult");
			Uri imageUri = data.getData();
			mToImg.setImageURI(imageUri);
			mFromImg.setImageURI(imageUri);
		}
	}

	private void initDatas() {

		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("这里是新人碳的说，(´・ω・)ﾉ★*ﾟ*ﾖﾛｼｸﾃﾞｽ*ﾟ*☆",
				Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mDatas);

		mMsgs.setAdapter(mAdapter);

	}

	private void initView() {
		mToImg = (ImageView) this.findViewById(R.id.id_to_img);
		mFromImg = (ImageView) this.findViewById(R.id.id_from_img);

		mMsgs = (ListView) this.findViewById(R.id.id_listview_msgs);
		mInputMsg = (EditText) this.findViewById(R.id.id_input_msgs);
		mSendMsg = (Button) this.findViewById(R.id.id_send_msg);
	}

}
