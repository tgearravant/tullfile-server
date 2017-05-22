package com.gearreald.tullfileserver.controllers;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import com.gearreald.tullfileserver.files.TullFileSystem;
import com.gearreald.tullfileserver.files.TullFolder;

import spark.Request;
import spark.Response;
import spark.Route;

public class ListingController {
	public static Route listFiles = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			response.type("application/json");
			JSONObject requestJSON = new JSONObject(request.body());
			String directory = requestJSON.getString("directory");
			TullFileSystem tfs = TullFileSystem.getTFS();
			TullFolder folder = tfs.getTullFolderAtPath(directory,false);
			responseJSON.put("response", folder.toJSON());
		}catch(FileNotFoundException e){
			e.printStackTrace();
			responseJSON.put("error", "The file was not found.");
			responseJSON.put("message", e.getMessage());
		}catch(JSONException e){
			e.printStackTrace();
			responseJSON.put("error", "The request was invalid.");
			responseJSON.put("message", e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			responseJSON.put("error", "Server Error.");
			responseJSON.put("message", e.getMessage());
		}
		return responseJSON.toString();
	};
}
