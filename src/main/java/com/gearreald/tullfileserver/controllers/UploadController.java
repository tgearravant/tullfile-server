package com.gearreald.tullfileserver.controllers;

import org.json.JSONObject;

import com.gearreald.tullfileserver.files.TullFile;
import com.gearreald.tullfileserver.files.TullFileSystem;
import com.gearreald.tullfileserver.files.TullFolder;

import spark.Request;
import spark.Response;
import spark.Route;

public class UploadController {
	public static Route uploadFiles = (Request request, Response response) -> {
		try{
			String localPath = request.headers("localPath");
			if(!localPath.startsWith("/")){
				localPath = "/"+localPath;
			}
			String fileName = request.headers("fileName");
			int pieceNumber = Integer.parseInt(request.headers("pieceNumber"));
			
			TullFolder fileFolder = TullFileSystem.getTFS().getTullFolderAtPath(localPath, true);
			TullFile file = fileFolder.getFile(fileName, true);
			byte[] data = request.bodyAsBytes();
			file.addPiece(pieceNumber, data);
			JSONObject json = new JSONObject();
			json.put("status", "success");
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("status", "failure");
			return json.toString();
		}
	};
}
