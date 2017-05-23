package com.gearreald.tullfileserver.server;

import static spark.Spark.*;

import com.gearreald.tullfileserver.controllers.AuthController;
import com.gearreald.tullfileserver.controllers.DownloadController;
import com.gearreald.tullfileserver.controllers.ListingController;
import com.gearreald.tullfileserver.controllers.FileSystemController;
import com.gearreald.tullfileserver.controllers.UploadController;
import com.gearreald.tullfileserver.controllers.VerificationController;

public final class Routing {

	public final static String DELETE_FILE="delete_file/";
	public final static String DELETE_FOLDER="delete_folder/";
	public final static String DOWNLOAD_FILES="download/";
	public final static String LIST_FILES="list/";
	public final static String NEW_FOLDER="new/";
	public final static String UPLOAD_FILES="upload/";
	public final static String VERIFY_PIECE="verify/";
	
	public static void setRouting(){
		beforeRouting();
		getRouting();
		postRouting();
		headRouting();
		deleteRouting();		
	}
	private static void beforeRouting(){
    	before("*",Filters.ensureLoggedIn);
	}
	private static void getRouting(){
		get(DOWNLOAD_FILES, DownloadController.downloadFiles);
		get(VERIFY_PIECE, VerificationController.getHash);
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
		delete(DELETE_FILE,FileSystemController.deleteFile);
		delete(DELETE_FOLDER,FileSystemController.deleteFolder);
	}
}
