package com.gearreald.tullfileserver.controllers;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import com.gearreald.tullfileserver.files.TullFileSystem;

import spark.Request;
import spark.Response;
import spark.Route;

public class FileSystemController {
	public static Route newFolder = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			response.type("application/json");
			JSONObject requestJSON = new JSONObject(request.body());
			System.out.println(requestJSON.toString());
			String directory = requestJSON.getString("directory");
			String name = requestJSON.getString("name");
			TullFileSystem tfs = TullFileSystem.getTFS();
			tfs.getTullFolderAtPath(directory+"/"+name,true);
			responseJSON.put("message","success");
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
	public static Route deleteFolder = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			response.type("application/json");
			JSONObject requestJSON = new JSONObject(request.body());
			System.out.println(requestJSON.toString());
			String directory = requestJSON.getString("directory");
			String name = requestJSON.getString("name");
			TullFileSystem tfs = TullFileSystem.getTFS();
			tfs.getTullFolderAtPath(directory+"/"+name,true);
			responseJSON.put("message","success");
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
	public static Route deleteFile = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			response.type("application/json");
			JSONObject requestJSON = new JSONObject(request.body());
			System.out.println(requestJSON.toString());
			String directory = requestJSON.getString("directory");
			String name = requestJSON.getString("name");
			TullFileSystem tfs = TullFileSystem.getTFS();
			tfs.getTullFolderAtPath(directory+"/"+name,true);
			responseJSON.put("message","success");
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
