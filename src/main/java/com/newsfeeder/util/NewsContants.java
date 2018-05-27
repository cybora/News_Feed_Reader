package com.newsfeeder.util;

public final class NewsContants {
	
	
	public static final int RCS = 1;
	public static final int AD_AMSTERDAM = 2;
	public static final int VOLKSKRANT_WETENSCHAP = 3;
	public static final int TROUW = 4;
	
	public static final int SCHEDULED_PERIOD = 300000;  //  = 5 minutes by default
	public static final int DEFAULT_TIME = 60;
	public static final int SLEEP_TIME = 30;
	
	public static final String REQUEST_PARAMETER_FORMAT_ERROR = "The both source and minutes parameters are required to be a integer value";

	
	// This user agent is for if the server wants real humans to visit
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
	public static final String IMAGE_PATH = "src/main/resources";
	public static final long SCHEDULED_REMOVAL_PERIOD = 600000;  //  10 minutes by default
	public static final int DELETE_HOURS_BEFORE = 6;

}
