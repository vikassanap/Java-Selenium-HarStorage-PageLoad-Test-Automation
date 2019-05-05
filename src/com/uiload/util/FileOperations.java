package com.uiload.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;

import java.io.IOException;


import org.apache.commons.io.FileDeleteStrategy;

import com.uiload.data.TestUrls;

public class FileOperations {
	private BufferedWriter out;
	TestUrls testUrls;
	String pathToScan;
	String target_file;
	String FileName;
	File[] listOfFiles1;


	public FileOperations() {
		testUrls = new TestUrls();
		pathToScan = testUrls.temp_har_storage;

	}

	public String getFileName(String fileName) throws IOException {
		File folderToScan = new File(pathToScan);
		listOfFiles1 = folderToScan.listFiles();
		for (int i = 0; i < listOfFiles1.length; i++) {
			if (listOfFiles1[i].isFile()) {
				target_file = listOfFiles1[i].getName();
				if (target_file.startsWith(fileName.substring(0, 11))
						&& target_file.endsWith(".har")) {
					FileName = pathToScan + target_file;

					return FileName;
				}
			}
		}

		return FileName;
	}

	public void deleteAllFiles() throws IOException {
		File folderToScan = new File(pathToScan);
		listOfFiles1 = folderToScan.listFiles();
		System.out.println("Deleting the files");
		for (int i = 0; i < listOfFiles1.length; i++) {
			FileDeleteStrategy.FORCE.delete(listOfFiles1[i]);
		}

	}

	public void copyHarFiles(String fileName) throws IOException {
		File srcFile = new File(getFileName(fileName));
		File destDir = new File(testUrls.har_storage);
		FileUtils.copyFileToDirectory(srcFile, destDir);

	}

	public void writeToLog(File file,String data) throws IOException {
		FileWriter fstream = new FileWriter(file,true);
		  out = new BufferedWriter(fstream);
		  out.write(data);

		  out.close();
	}

}
