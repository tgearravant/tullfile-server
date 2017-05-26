package com.gearreald.tullfileserver.controllers;

import org.json.JSONObject;

import com.gearreald.tullfileserver.files.TullFile;
import com.gearreald.tullfileserver.files.TullFileSystem;
import com.gearreald.tullfileserver.files.TullFolder;

import net.tullco.tullutils.FileUtils;
import net.tullco.tullutils.StringUtils;
import spark.Request;
import spark.Response;
import spark.Route;

public class DownloadController {
	public static Route downloadFiles = (Request request, Response response) -> {
		try{
			String localPath = StringUtils.assureStartsWith(request.headers("localPath"),"/");
			String fileName = request.headers("fileName");
			int pieceNumber = Integer.parseInt(request.headers("pieceNumber"));
			
			//System.out.println("Sending piece "+pieceNumber+" of file "+fileName+" at "+localPath);
			TullFolder fileFolder = TullFileSystem.getTFS().getTullFolderAtPath(localPath, true);
			TullFile file = fileFolder.getFile(fileName, true);
			byte[] data = FileUtils.getFileAsBytes(file.getPiece(pieceNumber));
			file.addPiece(pieceNumber, data);
			return data;
		}catch(Exception e){
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("error", "Server Error.");
			json.put("message", e.getMessage());
			return json.toString();
		}
	};
}
