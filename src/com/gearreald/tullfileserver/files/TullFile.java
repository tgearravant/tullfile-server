package com.gearreald.tullfileserver.files;

import java.io.File;

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
	public JSONObject toJSON(){
		JSONObject main = new JSONObject();
		main.put("name",this.getName());
		return main;
	}
	public String toString(){
		return this.toJSON().toString();
	}
}
