package com.gearreald.tullfileserver;

import static spark.Spark.*;

import com.gearreald.tullfileserver.controllers.AuthController;
import com.gearreald.tullfileserver.controllers.ListingController;
import com.gearreald.tullfileserver.controllers.UploadController;

public final class Routing {
	
	public final static String LIST_FILES="list/";
	public final static String UPLOAD_FILES="upload/";
	
	public static void setRouting(){
		getRouting();
		postRouting();
		headRouting();
		
	}
	private static void getRouting(){
		
	}
	private static void postRouting(){
		post(LIST_FILES, ListingController.listFiles);
		post(UPLOAD_FILES, UploadController.uploadFiles);
	}
	private static void headRouting(){
		head("*",AuthController.checkKey);
	}
}
