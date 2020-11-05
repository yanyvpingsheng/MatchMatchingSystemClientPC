package yanyv.mms.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONManager {
	
	JSONObject json;
	JSONArray rounds;
	Date date = new Date();
	int round = -1;
	
	public JSONManager() {
		json = new JSONObject();
		rounds = new JSONArray();
		json.put("rounds", rounds);
	}
	
	public JSONManager(String info) {
		json = new JSONObject(info);
		
	}
	
	public void setName(String name) {
		json.put("name", name);
	}
	
	public String getName() {
		return json.getString("name");
	}
	
	public void setFuhuo(boolean fuhuo) {
		json.put("fuhuo", fuhuo);
	}
	
	public boolean getFuhuo() {
		return json.getBoolean("fuhuo");
	}
	
	public void setDate() {
		json.put("date", date);
	}
	
	public String getDate() {
		Date date = new Date(json.getString("date"));
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
		return dateformat.format(date);
	}
	
	public void setOnline(boolean online) {
		json.put("online", online);
	}
	
	public boolean getOnline() {
		return json.getBoolean("online");
	}
	
	public void setFinish(boolean finish) {
		json.put("finish", finish);
	}
	
	public boolean getFinish() {
		return json.getBoolean("finish");
	}
	
	public void addRound(JSONObject r) {
		rounds.put(r);
	}
	
	public JSONArray getRounds() {
		
		return json.getJSONArray("rounds");
	}
	
	public String toString() {
		return json.toString();
	}
	
}
