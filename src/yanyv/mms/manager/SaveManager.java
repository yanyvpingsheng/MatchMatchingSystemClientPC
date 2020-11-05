package yanyv.mms.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.JSONObject;

public class SaveManager {
	public static void save(JSONManager jm) {
		String path = "./data/" + jm.getName() + ".ymatchdata";
		// 新建一个文件对象，如果不存在则创建一个该文件
		FileWriter fileWriter;
		try {
			String str = jm.toString();

			FileOutputStream fileOutputStream = new FileOutputStream(path, true);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			BufferedWriter out = new BufferedWriter(outputStreamWriter);

			fileWriter = new FileWriter(path);
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();

			out.write(str);
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<File> getFileList() {

		ArrayList<File> fileList = new ArrayList<File>();
		String path = "./data/";

		File home = new File(path);

		File[] files = home.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();

				if (fileName.endsWith(".ymatchdata")) {
					fileList.add(files[i]);
				}
			}
		}

		return fileList;
	}

	public static String getInfo(String name) {
		String path = "./data/" + name + ".ymatchdata";

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader in = new BufferedReader(inputStreamReader);
			String str;
			if ((str = in.readLine()) != null) {
				in.close();
				inputStreamReader.close();
				fileInputStream.close();
				return str;
			}
			in.close();
			inputStreamReader.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void del(String name) {
		String path = "./data/" + name + ".ymatchdata";

		File file = new File(path);
		file.delete();

	}

	public static ConfigManager getConfig() {
		String path = "./data/config.json";
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
				createConfig();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader in = new BufferedReader(inputStreamReader);
			String str;
			if ((str = in.readLine()) != null) {
				in.close();
				inputStreamReader.close();
				fileInputStream.close();
				return new ConfigManager(new JSONObject(str));
			}
			in.close();
			inputStreamReader.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ConfigManager();
	}

	public static void createConfig() {
		ConfigManager config = new ConfigManager();
		
		saveConfig(config);
		
	}

	public static void saveConfig(ConfigManager config) {
		String path = "./data/config.json";
		File file = new File(path);
		FileWriter fileWriter;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file.getPath(), true);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			BufferedWriter out = new BufferedWriter(outputStreamWriter);

			fileWriter = new FileWriter(file.getPath());
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();

			out.write(config.toString());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
