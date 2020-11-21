package yanyv.mms.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import yanyv.mms.code.Query;
import yanyv.mms.vo.Account;
import yanyv.mms.vo.Match;
import yanyv.mms.vo.MatchMode;

public class QueryWeb {

	private static Date stringToDate(String date) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = format.parse(date);
		return result;
	}

	public static Account queryUserByUid(int uid) throws Exception {
		// TODO Auto-generated method stub
		String queryUserByIdUrl = "http://" + IPConfig.IP + "/queryuserbyuid";

		URL url = new URL(queryUserByIdUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		out.write("uid=" + uid);
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

		Account acc = new Account();
		acc.setUid(result.getInt("uid"));
		acc.setName(result.getString("name"));
		acc.setEmail(result.getString("email"));
		acc.setDate(stringToDate(result.getString("date")));

		return acc;
	}

	public static List<MatchMode> queryAllMatchMode() throws Exception {
		// TODO Auto-generated method stub
		String loginUrl = "http://" + IPConfig.IP + "/queryallmatchmode";

		URL url = new URL(loginUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
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
		JSONArray result = new JSONArray(sTotalString);

		List<MatchMode> modes = new ArrayList<>();

		for (int i = 0; i < result.length(); i++) {
			JSONObject res = result.getJSONObject(i);
			MatchMode mode = new MatchMode();
			mode.setId(res.getInt("id"));
			mode.setName(res.getString("name"));
			mode.setUri(res.getString("uri"));

			modes.add(mode);
		}

		return modes;
	}

	public static List<Match> queryMatchsByUid(int uid) throws Exception {
		String loginUrl = "http://" + IPConfig.IP + "/querymatchsbyuid";

		URL url = new URL(loginUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		out.write("uid=" + uid);
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

		List<Match> matchs = new ArrayList<>();

		if (result.getInt("code") == Query.QUERY_SUCCESS) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			JSONArray matchArr = result.getJSONArray("data");

			for (int i = 0; i < matchArr.length(); i++) {
				JSONObject matchObj = matchArr.getJSONObject(i);
				Match match = new Match();
				match.setMid(matchObj.getString("mid"));
				match.setName(matchObj.getString("name"));
				match.setCreaterUid(matchObj.getInt("creater_uid"));
				match.setModeId(matchObj.getInt("mode_id"));
				match.setStartDate(dateformat.parse(matchObj.getString("start_datetime")));
				if (matchObj.getInt("state") == 3)
					match.setEndDate(dateformat.parse(matchObj.getString("end_datetime")));
				match.setDeadline(dateformat.parse(matchObj.getString("deadline_datetime")));
				match.setCreateDate(dateformat.parse(matchObj.getString("create_datetime")));
				match.setState(matchObj.getInt("state"));
				
				matchs.add(match);
			}
		} else {
			JOptionPane.showMessageDialog(null, result.getString("data"), "错误",JOptionPane.WARNING_MESSAGE);
		}

		return matchs;
	}

	public static List<Account> queryAllByMid(String mid) throws Exception {
		String loginUrl = "http://" + IPConfig.IP + "/queryallbymid";

		URL url = new URL(loginUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
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

		List<Account> accs = new ArrayList<>();

		if (result.getInt("code") == Query.QUERY_SUCCESS) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			JSONArray accArr = result.getJSONArray("data");

			for (int i = 0; i < accArr.length(); i++) {
				JSONObject accObj = accArr.getJSONObject(i);
				Account account = new Account();
				account.setUid(accObj.getInt("uid"));
				account.setName(accObj.getString("name"));
				account.setDate(dateformat.parse(accObj.getString("signupDate")));
				account.setState(accObj.getInt("state"));
				
				accs.add(account);
			}
		} else {
			JOptionPane.showMessageDialog(null, result.getString("data"), "错误",JOptionPane.WARNING_MESSAGE);
		}
		
		return accs;
	}
}
