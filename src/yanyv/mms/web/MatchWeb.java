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

import org.json.JSONObject;

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
}
