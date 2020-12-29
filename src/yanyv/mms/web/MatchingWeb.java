package yanyv.mms.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import yanyv.mms.Window.MatchWindow;
import yanyv.mms.view.Round;
import yanyv.mms.vo.Matching;

public class MatchingWeb {
	public static JSONObject addMatching(Round round, Matching match, int matchingid) throws Exception {

		String urlString = "http://" + IPConfig.IP + "/addmatching";

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("matchingid=" + matchingid);
		out.write("&rid=" + round.getRound());
		out.write("&mid=" + MatchWindow.match.getMid());
		out.write("&uid1=" + match.getP1().getAccount().getUid());
		if(match.getP2() != null) out.write("&uid2=" + match.getP2().getAccount().getUid());
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
	
	public static JSONObject updateWinner(Round round, Matching match) throws Exception {

		String urlString = "http://" + IPConfig.IP + "/updatematchingwinner";

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		//OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "gbk");
		out.write("matchingid=" + round.getM().indexOf(match));
		out.write("&rid=" + round.getRound());
		out.write("&mid=" + MatchWindow.match.getMid());
		out.write("&winner=" + match.getWinner().getAccount().getUid());
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
