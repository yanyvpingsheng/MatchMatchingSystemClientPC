package yanyv.mms.manager;

import org.json.JSONObject;

public class ConfigManager {
	private JSONObject config;
	public ConfigManager() {
		config = new JSONObject();
		config.put("savePass", false);
		config.put("autoLogin", false);
		config.put("user", "");
		config.put("pass", "");
	}
	
	public ConfigManager(JSONObject config) {
		this.config = config;
	}
	
	public void setUser(String user) {
		config.put("user", user);
	}
	
	public void setPass(String pass) {
		config.put("pass", pass);
	}
	
	public void setSavePass(boolean save) {
		config.put("savePass", save);
	}
	
	public void setAutoLogin(boolean auto) {
		config.put("autoLogin", auto);
	}
	
	public String getUser() {
		return config.getString("user");
	}
	
	public String getPass() {
		return config.getString("pass");
	}
	
	public boolean getSavePass() {
		return config.getBoolean("savePass");
	}
	
	public boolean getAutoLogin() {
		return config.getBoolean("autoLogin");
	}
	
	public String toString() {
		return config.toString();
	}
	
}
