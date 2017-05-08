package com.example.android_ai_util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import com.example.android_ai_bean.ChatMessage;
import com.example.android_ai_bean.ChatMessage.Type;
import com.example.android_ai_bean.ChatResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class HttpUtil {
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "3a04beab50f2460f9a08395728b7edc5";

	/**
	 * 发送一个消息，得到一个消息
	 * 
	 * @param msg
	 * @return
	 */
	public static ChatMessage sendMessage(String msg) {
		ChatMessage chatMessage = new ChatMessage();

		String jsonRes = doGet(msg);
		Gson gson = new Gson();
		ChatResult result = null;
		try {
			result = gson.fromJson(jsonRes, ChatResult.class);
			chatMessage.setMsg(result.getText());
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			chatMessage.setMsg("服务器繁忙，请稍后再试");
			e.printStackTrace();
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(Type.INCOMING);
		return chatMessage;
	}

	public static String doGet(String msg) {
		String result = "";
		String url = setParams(msg);
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlNet
					.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");

			is = conn.getInputStream();
			int len = -1;
			byte[] buf = new byte[128];
			bos = new ByteArrayOutputStream();
			while ((len = is.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			bos.flush();
			result = new String(bos.toByteArray());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;
	}

	private static String setParams(String msg) {
		String url = "";
		try {
			url = URL + "?key=" + API_KEY + "&info="
					+ URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

}
