package com.gearreald.tullfileserver.controllers;

import org.json.JSONObject;

import com.gearreald.tullfileserver.files.TullFile;
import com.gearreald.tullfileserver.files.TullFileSystem;
import com.gearreald.tullfileserver.files.TullFolder;

import spark.Request;
import spark.Response;
import spark.Route;

public class VerificationController {
	public static Route getHash = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			String localPath = request.headers("localPath");
			if(!localPath.startsWith("/")){
				localPath = "/"+localPath;
			}
			String fileName = request.headers("fileName");
			int pieceNumber = Integer.parseInt(request.headers("pieceNumber"));
			

			TullFolder fileFolder = TullFileSystem.getTFS().getTullFolderAtPath(localPath, true);
			TullFile file = fileFolder.getFile(fileName, true);
			
			String pieceHash = file.getPieceHash(pieceNumber);
			responseJSON.put("fileName", fileName);
			responseJSON.put("localPath", localPath);
			responseJSON.put("pieceNumber", pieceNumber);
			responseJSON.put("piece_hash", pieceHash);
		}catch(Exception e){
			responseJSON.put("error","serverError");
			responseJSON.put("message",e.getMessage());
		}
		return responseJSON.toString();
	};
}
