package com.clumsy.googletest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogFile {
	private static final String DEFAULT_LOG_FILE = "c:\\temp\\log.txt";
	
	private static FileOutputStream logFileStream = null;
	
	public static void log(String message) {
		try {
		    if (logFileStream==null) {
			    final File logFile = new File(DEFAULT_LOG_FILE);
			    logFileStream = new FileOutputStream(logFile);
		    }
		    logFileStream.write(message.getBytes(), 0, message.getBytes().length);
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
	}
	
	public static void close() {
		if (logFileStream!=null) {
			try {
				logFileStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logFileStream=null;
		}
			
	}
}
