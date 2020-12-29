package yanyv.mms.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import yanyv.mms.Window.MatchWindow;
import yanyv.mms.view.Round;
import yanyv.mms.vo.Match;

public class RoundWeb {
	public static JSONObject addRound(Round round) throws Exception {

		String urlString = "http://" + IPConfig.IP + "/addround";

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("title=" + round.getTitle());
		out.write("&rid=" + round.getRound());
		out.write("&mid=" + MatchWindow.match.getMid());
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
	
	public static JSONObject updataRound(Round round) throws Exception {

		String urlString = "http://" + IPConfig.IP + "/updataround";

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("title=" + round.getTitle());
		out.write("&rid=" + round.getRound());
		out.write("&mid=" + MatchWindow.match.getMid());
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
