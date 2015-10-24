package com.hacklm.assist;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Neo on 2015/7/3.
 */
public class NetUtils {

	private static String urlHead = "http://tuputao.com:1337/msg/";

	public static String initWithOneMsg(Double x, Double y) {
		HttpURLConnection conn = null;
		String data = "count?" + "x=" + x.toString() + "&y=" + y.toString();
		String url = urlHead + data;
		try {
			URL mURL = new URL(url);
			conn = (HttpURLConnection) mURL.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(10000);

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i("netnet", "访问失败" + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	public static String addTucao(Double x, Double y, String content) {
		HttpURLConnection conn = null;

		String data = "add?x=" + x + "&y=" + y + "&content=" + content;
		String url = urlHead + data;
		try {
			URL mURL = new URL(url);
			Log.e("begin", url);
			conn = (HttpURLConnection) mURL.openConnection();
			Log.e("end", "abc");

			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(10000);

			int responseCode = conn.getResponseCode();
			Log.e("netnet", responseCode + "");
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i("netnet", "访问失败" + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
	
	public static String getDetail(Double x, Double y) {
		HttpURLConnection conn = null;

		String data = "listExactly?x=" + x + "&y=" + y;
		String url = urlHead + data;
		try {
			URL mURL = new URL(url);
			Log.e("begin", url);
			conn = (HttpURLConnection) mURL.openConnection();
			Log.e("end", "abc");

			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(10000);

			int responseCode = conn.getResponseCode();
			Log.e("netnet", responseCode + "");
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i("netnet", "访问失败" + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * 根据流返回一个字符串信息 *
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static String getStringFromInputStream(InputStream is)
			throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// 模板代码 必须熟练
		byte[] buffer = new byte[1024];
		int len = -1;
		// 一定要写len=is.read(buffer)
		// 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		is.close();
		String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
		os.close();
		return state;
	}
}
