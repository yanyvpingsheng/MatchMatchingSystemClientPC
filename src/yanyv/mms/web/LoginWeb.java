package yanyv.mms.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class LoginWeb {

	public static JSONObject login(String user, String pass) throws Exception {
		// TODO Auto-generated method stub
		String loginUrl = "http://" + IPConfig.IP + "/loginback";
		
		String useEmail = "unused";
		String email = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		
		Pattern patEmail = Pattern.compile(email);
		Matcher matEmail = patEmail.matcher(user);
		
		if(matEmail.find()) useEmail = "used";
		
		URL url = new URL(loginUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		out.write("user=" + user + "&pass=" + pass + "&useEmail=" + useEmail);
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
		System.out.println(sTotalString);
		JSONObject result = new JSONObject(sTotalString);
		
		return result;
	}

}
