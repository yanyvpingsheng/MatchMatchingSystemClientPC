package yanyv.mms.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import yanyv.mms.code.MatchStart;
import yanyv.mms.vo.Match;

public class MatchWeb {
	public static JSONObject addMatchs(Match mat) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

		String addMatchUrl = "http://" + IPConfig.IP + "/addmatch";

		URL url = new URL(addMatchUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("name=" + mat.getName());
		out.write("&creater_uid=" + mat.getCreaterUid());
		out.write("&mode_id=" + mat.getModeId());
		out.write("&start_datetime=" + format.format(mat.getStartDate()));
		out.write("&deadline=" + format.format(mat.getDeadline()));
		out.flush();
		out.close();

		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = conn.getInputStream();
		// 三层包装
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}
		// System.out.println(sTotalString);
		JSONObject result = new JSONObject(sTotalString);
		
		return result;
	}
	
	public static JSONObject getMatchInfo(String mid) throws Exception {
		String addMatchUrl = "http://" + IPConfig.IP + "/querymatchbymid";

		URL url = new URL(addMatchUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("mid=" + mid);
		out.flush();
		out.close();

		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = conn.getInputStream();
		// 三层包装
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}
		// System.out.println(sTotalString);
		JSONObject result = new JSONObject(sTotalString);
		
		return result;
	}
	
	public static String getMatchMode(int mmid) throws Exception {
		String addMatchUrl = "http://" + IPConfig.IP + "/querymatchmodebyid";

		URL url = new URL(addMatchUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("id=" + mmid);
		out.flush();
		out.close();

		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = conn.getInputStream();
		// 三层包装
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}
		// System.out.println(sTotalString);
		JSONObject result = new JSONObject(sTotalString).getJSONObject("data");
		
		String mode = result.getString("name");
		
		return mode;
	}
	
	public static String getMatchState(int msid) throws Exception {
		String addMatchUrl = "http://" + IPConfig.IP + "/querymatchstatebymsid";

		URL url = new URL(addMatchUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("msid=" + msid);
		out.flush();
		out.close();

		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = conn.getInputStream();
		// 三层包装
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}
		// System.out.println(sTotalString);
		JSONObject result = new JSONObject(sTotalString);
		
		String mode = result.getString("data");
		
		return mode;
	}
	
	public static int getMatchSize(String mid) throws Exception {
		String addMatchUrl = "http://" + IPConfig.IP + "/querysizebymid";

		URL url = new URL(addMatchUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("mid=" + mid);
		out.flush();
		out.close();

		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = conn.getInputStream();
		// 三层包装
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}
		// System.out.println(sTotalString);
		JSONObject result = new JSONObject(sTotalString);
		
		int size = result.getInt("data");
		
		return size;
	}
	
	public static boolean startMatch(String mid) throws Exception {
		String addMatchUrl = "http://" + IPConfig.IP + "/startmatch";

		URL url = new URL(addMatchUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("mid=" + mid);
		out.flush();
		out.close();

		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = conn.getInputStream();
		// 三层包装
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "\r\n";
		}
		// System.out.println(sTotalString);
		JSONObject result = new JSONObject(sTotalString);
		
		if(result.getInt("code") == MatchStart.SUCCESS) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, result.getString("data"), "警告", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}
