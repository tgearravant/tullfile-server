package com.gearreald.tullfileserver.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import net.tullco.tullutils.StringUtils;

public class TullFile {
	
	private final static int PIECE_NUMBER_PADDING=8;
	
	private File fileLocation;
	
	public TullFile(File f){
		this.fileLocation=f;
	}
	public String getName(){
		String name = this.fileLocation.getName();
		return name.substring(0, name.length()-TullFileSystem.TULLFILE_SUFFIX.length());
	}
	public void addPiece(int pieceNumber, byte[] data) throws IOException{
		File piece = new File(getAbsolutePathToPiece(pieceNumber));
		System.out.println(piece.getAbsolutePath());
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
	public int totalPieces(){
		File[] files = getUnorderedPieces();
		int totalPieces = 0;
		for(File f: files){
			if(f.getName().endsWith(".part"))
				totalPieces++;
		}
		return totalPieces;
	}
	private File[] getUnorderedPieces(){
		return this.fileLocation.listFiles();
	}
	public List<File> getPieces(){
		ArrayList<File> orderedFiles = new ArrayList<File>();
		ArrayList<String> filePaths = new ArrayList<String>();
		File[] files = getUnorderedPieces();
		for(File f: files)
			filePaths.add(f.getAbsolutePath());
		filePaths.sort(null);
		for(String s: filePaths){
			orderedFiles.add(new File(s));
		}
		return orderedFiles;
	}
	private String getPieceName(int pieceNumber){
		return this.getName()+"_"+StringUtils.leftPad(Integer.toString(pieceNumber), '0', PIECE_NUMBER_PADDING)+".part";
	}
	private String getAbsolutePathToPiece(int pieceNumber){
		return this.fileLocation.getAbsolutePath()+"/"+this.getPieceName(pieceNumber);
	}
	public JSONObject toJSON(){
		JSONObject main = new JSONObject();
		main.put("name",this.getName());
		main.put("pieces", this.totalPieces());
		return main;
	}
	public String getAbsolutePath(){
		return this.fileLocation.getAbsolutePath();
	}
	public String toString(){
		return this.toJSON().toString();
	}
}
