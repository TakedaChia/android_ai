package com.example.android_ai_test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.android_ai_util.HttpUtil;

public class TestUtil extends AndroidTestCase {

	public void testSendInfo() {
		String res = HttpUtil.doGet("���ҽ���Ц��");
		Log.e("TAG", res);
		res = HttpUtil.doGet("���ҽ��������");
		Log.e("TAG", res);
		res = HttpUtil.doGet("���");
		Log.e("TAG", res);
		res = HttpUtil.doGet("����˭");
		Log.e("TAG", res);

	}
}
