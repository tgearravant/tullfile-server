package com.gearreald.tullfileserver;

import static spark.Spark.*;

import com.gearreald.tullfileserver.controllers.AuthController;
import com.gearreald.tullfileserver.controllers.DownloadController;
import com.gearreald.tullfileserver.controllers.ListingController;
import com.gearreald.tullfileserver.controllers.FileSystemController;
import com.gearreald.tullfileserver.controllers.UploadController;

public final class Routing {
	
	public final static String LIST_FILES="list/";
	public final static String UPLOAD_FILES="upload/";
	public final static String DOWNLOAD_FILES="download/";
	public final static String NEW_FOLDER="new/";
	public final static String DELETE_FOLDER="delete_folder/";
	public final static String DELETE_FILE="delete_file/";
	
	public static void setRouting(){
		getRouting();
		postRouting();
		headRouting();
		deleteRouting();		
	}
	private static void getRouting(){
		get(DOWNLOAD_FILES, DownloadController.downloadFiles);
	}
	private static void postRouting(){
		post(LIST_FILES, ListingController.listFiles);
		post(NEW_FOLDER, FileSystemController.newFolder);
		post(UPLOAD_FILES, UploadController.uploadFiles);
	}
	private static void headRouting(){
		head("*",AuthController.checkKey);
	}
	private static void deleteRouting(){
		delete(DELETE_FOLDER,FileSystemController.deleteFolder);
		delete(DELETE_FILE,FileSystemController.deleteFile);
	}
}
