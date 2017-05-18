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
		byte[] data = request.bodyAsBytes();
		String localPath = request.headers("localPath");
		int pieceNumber = Integer.parseInt(request.headers("pieceNumber"));
		String[] pathArray = localPath.split("/");
		String folderPath="";
		for(int i=0;i<pathArray.length-1;i++){
			folderPath+=pathArray[i];
		}
		TullFolder fileFolder = TullFileSystem.getTFS().getTullFolderAtPath(folderPath, true);
		TullFile file = fileFolder.getFile(pathArray[pathArray.length-1], true);
		file.addPiece(pieceNumber, data);
		JSONObject json = new JSONObject();
		json.put("status", "success");
		return json.toString();
	};
}
