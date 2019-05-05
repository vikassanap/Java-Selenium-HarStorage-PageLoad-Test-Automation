package com.uiload.main;

import com.uiload.util.*;
import com.uiload.data.*;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class UI {

	public static void main(String[] args) throws IOException {

		FileOperations foper = new FileOperations();
		TestUrls test_data = new TestUrls();
		TestOutput test_output = new TestOutput();
		StringUtil str_util = new StringUtil();


		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Timestamp myTimeStamp = timestamp;

		String file_name = myTimeStamp.toString().substring(0, 19);
		File file = new File(test_data.log_dir+test_data.project_name+"-"+file_name+".log");

		if (file.createNewFile()) {
			System.out.println("Log File is created!");
		} else {
			System.out.println("Log File already exists.");
		}

		for (int i = 0; i < test_data.urls.size(); i++) {
			FirefoxProfile profile = new ProfilesIni().getProfile("default");
			profile.setPreference("app.update.enabled", false);
			String domain = "extensions.firebug.";
			profile.setPreference(domain + "currentVersion", "2.0");
			profile.setPreference(domain + "allPagesActivation", "on");
			profile.setPreference(domain + "defaultPanelName", "net");
			profile.setPreference(domain + "net.enableSites", true);
			profile.setPreference(domain + "netexport.alwaysEnableAutoExport",
					true);
			profile.setPreference(domain + "netexport.showPreview", false);
			profile.setPreference(domain + "netexport.defaultLogDir",
					test_data.temp_har_storage);

			WebDriver driver = new FirefoxDriver(profile);
			driver.manage().deleteAllCookies();
			try {
					if(test_data.view[i]=="First view"){
						Thread.sleep(5000);
						driver.get("http://" + test_data.urls.get(i));
						System.out.println(test_data.view[i]+"----"+test_data.urls.get(i));
						foper.writeToLog(file, test_data.view[i]+"----"+test_data.urls.get(i));
						Thread.sleep(50000);
						driver.quit();
					}
				else
				{
					Thread.sleep(5000);
					driver.get("http://" + test_data.urls.get(i));
					Thread.sleep(5000);
					try {
						foper.deleteAllFiles();
						System.out.println("Files deleted!!1");
					} catch (IOException e) {
						e.printStackTrace();
					}
					driver.navigate().refresh();
					System.out.println(test_data.view[i]+"----"+test_data.urls.get(i));
					foper.writeToLog(file, test_data.view[i]+"----"+test_data.urls.get(i));
					Thread.sleep(50000);
					driver.quit();
				}

				String FilePath = foper.getFileName(test_data.urls.get(i));
				System.out.println("File name returned:"+FilePath);

				WebDriver driver1 = new FirefoxDriver();
				driver1.get(test_data.har_storage_url);
				System.out.println("harstorageUrl"+test_data.har_storage_url);
				driver1.findElement(By.id("file")).sendKeys(FilePath.trim());
				driver1.findElement(By.name("upload")).click();
				Thread.sleep(5000);

				File scrFile = ((TakesScreenshot) driver1)
						.getScreenshotAs(OutputType.FILE);

				FileUtils.copyFile(scrFile, new File(
						test_data.screenshot_storage + test_data.urls.get(i)
								+ " screenshot.png"));

				String full_load_time = driver1.findElement(
						By.id("full-load-time")).getText();
				full_load_time = str_util.convertTime(full_load_time);
				test_output.full_load_time.add(full_load_time);

				String time_to_first_byte = driver1.findElement(
						By.id("time-to-first-byte")).getText();
				time_to_first_byte = str_util.convertTime(time_to_first_byte);
				test_output.time_to_load_first_byte.add(time_to_first_byte);

				String total_dns_time = driver1.findElement(
						By.id("total-dns-time")).getText();
				total_dns_time = str_util.convertTime(total_dns_time);
				test_output.total_dns_time.add(total_dns_time);

				String total_server_time = driver1.findElement(
						By.id("total-server-time")).getText();
				total_server_time = str_util.convertTime(total_server_time);
				test_output.total_server_time.add(total_server_time);

				String total_size = driver1.findElement(By.id("total-size"))
						.getText();
				total_size = str_util.convertSize(total_size);
				test_output.total_page_size.add(total_size);

				String text_size = driver1.findElement(By.id("text-size"))
						.getText();
				text_size = str_util.convertSize(text_size);
				test_output.text_size.add(text_size);

				String media_size = driver1.findElement(By.id("media-size"))
						.getText();
				media_size = str_util.convertSize(media_size);
				test_output.media_size.add(media_size);

				String cache_size = driver1.findElement(By.id("cache-size"))
						.getText();
				cache_size = str_util.convertSize(cache_size);
				test_output.cache_size.add(cache_size);

				String more_info = driver1.getCurrentUrl();
				test_output.more_info.add(more_info);

				String on_load_time = driver1.findElement(By.id("onload-event")).getText();
				on_load_time = str_util.convertTime(on_load_time);
				test_output.on_load_time.add(on_load_time);


				driver1.quit();

				foper.copyHarFiles(test_data.urls.get(i));


				try {
					foper.deleteAllFiles();
				} catch (IOException e) {

					e.printStackTrace();
				}

			} catch (InterruptedException err) {
				System.out.println(err);
			}


		}

		for(int i=0;i<test_data.to_list.size();i++){
		Send_Mail sm = new Send_Mail();
		sm.sendMail(test_output,test_data.to_list.get(i));
		foper.writeToLog(file, "Mail sent to team");
		}
	}
}
