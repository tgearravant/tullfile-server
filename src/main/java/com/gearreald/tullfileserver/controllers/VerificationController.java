package com.gearreald.tullfileserver.controllers;

import java.io.FileNotFoundException;

import org.json.JSONObject;

import com.gearreald.tullfileserver.files.TullFile;
import com.gearreald.tullfileserver.files.TullFileSystem;
import com.gearreald.tullfileserver.files.TullFolder;

import net.tullco.tullutils.StringUtils;
import spark.Request;
import spark.Response;
import spark.Route;

public class VerificationController {
	
	public static Route getPieceHash = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			String localPath = StringUtils.assureStartsWith(request.headers("localPath"),"/");
			String fileName = request.headers("fileName");
			int pieceNumber = Integer.parseInt(request.headers("pieceNumber"));
			
			TullFolder fileFolder = TullFileSystem.getTFS().getTullFolderAtPath(localPath, true);
			TullFile file = fileFolder.getFile(fileName, true);
			
			String pieceHash = file.getPieceHash(pieceNumber);
			responseJSON.put("fileName", fileName);
			responseJSON.put("localPath", localPath);
			responseJSON.put("pieceNumber", pieceNumber);
			responseJSON.put("piece_hash", pieceHash);
		}catch(FileNotFoundException e){
			responseJSON.put("error", "No Tullfile");
			responseJSON.put("message", e.getMessage());
		}catch(Exception e){
			responseJSON.put("error","serverError");
			responseJSON.put("message",e.getMessage());
		}
		return responseJSON.toString();
	};
	
	public static Route setFullFileHash = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		JSONObject requestJSON = new JSONObject(request.body());
		try{
			String fileName = requestJSON.getString("fileName");
			String localPath = StringUtils.assureStartsWith(requestJSON.getString("localPath"),"/");
			String hash = requestJSON.getString("file_hash");
			TullFolder folder = TullFileSystem.getTFS().getTullFolderAtPath(localPath);
			TullFile file = folder.getFile(fileName,true);
			file.setFileHash(hash);
			responseJSON.put("status", "success");
			responseJSON.put("fileName", fileName);
			responseJSON.put("localPath", localPath);
		}catch(FileNotFoundException e){
			responseJSON.put("error", "No Tullfile");
			responseJSON.put("message", e.getMessage());
		}catch(Exception e){
			responseJSON.put("error","serverError");
			responseJSON.put("message",e.getMessage());
		}
		return responseJSON.toString();
	};
	
	public static Route getFullFileHash = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			String localPath = StringUtils.assureStartsWith(request.headers("localPath"),"/");
			String fileName = request.headers("fileName");
			TullFolder folder = TullFileSystem.getTFS().getTullFolderAtPath(localPath);
			TullFile file = folder.getFile(fileName);
			String hash = file.getFileHash();
			if(hash == null){
				throw new FileNotFoundException();
			}
			responseJSON.put("status", "success");
			responseJSON.put("fileName", fileName);
			responseJSON.put("localPath", localPath);
			responseJSON.put("file_hash", hash);
		}catch(FileNotFoundException e){
			responseJSON.put("error", "No Tullfile");
			responseJSON.put("message", e.getMessage());
		}catch(Exception e){
			responseJSON.put("error","serverError");
			responseJSON.put("message",e.getMessage());
		}
		return responseJSON.toString();
	};
}
