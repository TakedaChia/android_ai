package com.example.android_ai_test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.android_ai_util.HttpUtil;

public class TestUtil extends AndroidTestCase {

	public void testSendInfo() {
		String res = HttpUtil.doGet("给我讲个笑话");
		Log.e("TAG", res);
		res = HttpUtil.doGet("给我讲个鬼故事");
		Log.e("TAG", res);
		res = HttpUtil.doGet("你好");
		Log.e("TAG", res);
		res = HttpUtil.doGet("你是谁");
		Log.e("TAG", res);

	}
}
