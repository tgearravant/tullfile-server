package com.gearreald.tullfileserver.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gearreald.tullfileserver.utils.SystemUtils;

public class TullFolder {
	private File folderLocation;
	public TullFolder(File directory){
		this.folderLocation=directory;
	}
	public TullFile[] getFiles(){
		File[] allEntries = this.folderLocation.listFiles();
		ArrayList<TullFile> files = new ArrayList<TullFile>();
		for(File e : allEntries){
			if(e.isDirectory() && e.getName().endsWith(".tullfile")){
				files.add(new TullFile(e));
			}
		}
		return files.toArray(new TullFile[0]);
	}
	public TullFolder[] getFolders(){
		File[] allEntries = this.folderLocation.listFiles();
		ArrayList<TullFolder> files = new ArrayList<TullFolder>();
		String fileSuffix = SystemUtils.getProperty("tullfile_suffix");
		for(File e : allEntries){
			if(e.isDirectory() && !e.getName().endsWith(fileSuffix)){
				files.add(new TullFolder(e));
			}
		}
		return files.toArray(new TullFolder[0]);
	}
	public TullFolder getFolder(String name) throws FileNotFoundException {
		TullFolder[] folders = this.getFolders();
		for (TullFolder f: folders){
			if(f.getName().equals(name))
				return f;
		}
		throw new FileNotFoundException("The specified folder does not exist.");
	}
	public TullFile getFile(String name) throws FileNotFoundException {
		TullFile[] files = this.getFiles();
		for (TullFile f: files){
			if(f.getName().equals(name))
				return f;
		}
		throw new FileNotFoundException("The specified file does not exist.");
	}
	public String getName(){
		return this.folderLocation.getName();
	}
	public JSONObject toJSON(){
		JSONObject main = new JSONObject();
		JSONArray folders = new JSONArray();
		for(TullFolder f : this.getFolders()){
			folders.put(f.getName());
		}
		JSONArray files = new JSONArray();
		for(TullFile f: this.getFiles()){
			files.put(f.toJSON());
		}
		main.put("subfolders", folders);
		main.put("files", files);
		main.put("type", "folder");
		return main;
	}
	public String toString(){
		return this.toJSON().toString();
	}
}
