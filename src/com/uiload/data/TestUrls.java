package com.uiload.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class TestUrls {

	public String temp_har_storage = null;
	public String har_storage = null;
	public String screenshot_storage = null;
	public String from_email = null;
	public String from_email_password = null;

	public String har_storage_url = null;
	public List<String> urls = new ArrayList<String>();
	public List<String> to_list = new ArrayList<String>();
	public String project_name = null;
	public String log_dir = null;
	public String[] view = { "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view", "First view", "Reload view", "First view",
			"Reload view" };

	public TestUrls() {

		Properties prop = new Properties();
		try {
			String workingDir = System.getProperty("user.dir");
			System.out.println(workingDir);
			prop.load(new FileInputStream("/home/vikas/UI_Runnable/Input_Properties/config.properties"));
			System.out.println(workingDir
					+ "/Input_Properties/config.properties");
			project_name = prop.getProperty("ProjectName").trim();
			log_dir = prop.getProperty("Logs").trim();
			har_storage = prop.getProperty("HistoryFilesDirectory").trim();
			temp_har_storage = prop.getProperty("TempHarDirectory").trim();
			screenshot_storage = prop.getProperty("Screenshots").trim();
			har_storage_url = prop.getProperty("HarStorageUrl").trim();
			from_email = prop.getProperty("EmailFromEmail").trim();
			from_email_password = prop.getProperty("EmailFromPassword").trim();

			String key = "link";
			String newkey = key + "0";

			for (int i = 0; !(prop.getProperty(newkey).equals("link-end")); i++) {

				urls.add(prop.getProperty(newkey).trim());
				newkey = key + Integer.toString(i + 1);
			}

			key = "To";
			newkey = key + "0";

			for (int i = 0; !(prop.getProperty(newkey).equals("mail-end")); i++) {
				to_list.add(prop.getProperty(newkey));
				newkey = key + Integer.toString(i + 1);

			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
