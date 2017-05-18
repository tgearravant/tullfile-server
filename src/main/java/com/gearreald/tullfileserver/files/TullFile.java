package com.gearreald.tullfileserver.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

public class TullFile {
	public File fileLocation;
	public TullFile(File f){
		this.fileLocation=f;
	}
	public String getName(){
		String name = this.fileLocation.getName();
		return name.substring(0, name.length()-TullFileSystem.TULLFILE_SUFFIX.length());
	}
	public void addPiece(int pieceNumber, byte[] data) throws IOException{
		File piece = new File(getAbsolutePathToPiece(pieceNumber));
		FileOutputStream output = new FileOutputStream(piece);
		output.write(data);
		output.close();
	}
	public File getPiece(int pieceNumber) throws FileNotFoundException{
		File f = new File(getAbsolutePathToPiece(pieceNumber));
		if(!f.exists())
			throw new FileNotFoundException("The requested piece does not exist.");
		else
			return f;
		
	}
	private String getPieceName(int pieceNumber){
		return this.getName()+"_"+pieceNumber+".part";
	}
	private String getAbsolutePathToPiece(int pieceNumber){
		return this.fileLocation.getAbsolutePath()+this.getPieceName(pieceNumber);
	}
	public JSONObject toJSON(){
		JSONObject main = new JSONObject();
		main.put("name",this.getName());
		return main;
	}
	public String toString(){
		return this.toJSON().toString();
	}
}
