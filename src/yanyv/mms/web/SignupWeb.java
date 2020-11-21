package yanyv.mms.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import yanyv.mms.code.Query;
import yanyv.mms.code.Signup;
import yanyv.mms.vo.Account;

public class SignupWeb {
	public static boolean Signup(int uid, String mid) throws Exception {
		String queryUserByIdUrl = "http://" + IPConfig.IP + "/signupback";

		URL url = new URL(queryUserByIdUrl);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "8859_1");
		out.write("uid=" + uid);
		out.write("&mid=" + mid);
		out.write("&from=" + "ClientPC");
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

		if (result.getInt("code") == Signup.SUCCESS) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, result.getString("data"), "错误", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}
