package com.gearreald.tullfileserver.files;

import java.io.File;
import java.io.FileNotFoundException;

import com.gearreald.tullfileserver.utils.SystemUtils;

public class TullFileSystem {
	
	public static String TULLFILE_SUFFIX=SystemUtils.getProperty("tullfile_suffix");
	
	private String absolutePath;
	private TullFolder rootTullFolder;
	
	public TullFileSystem(String subFolder){
		this.absolutePath=SystemUtils.getUserDirectory()+File.separator+subFolder;
		File rootFolder = new File(this.absolutePath);
		if(!rootFolder.exists())
			rootFolder.mkdir();
		this.rootTullFolder=new TullFolder(rootFolder);
	}
	public TullFile[] getRootFiles(){
		return this.rootTullFolder.getFiles();
	}
	public TullFolder[] getRootFolders(){
		return this.rootTullFolder.getFolders();
	}
	public TullFolder getRootFolder(){
		for(TullFile f:this.rootTullFolder.getFiles())
			System.out.println(f.getName());
		return this.rootTullFolder;
	}
	public TullFolder getTullFolderAtPath(String s, boolean create) throws FileNotFoundException {
		if(s.equals("/"))
			return this.getRootFolder();
		String[] path = s.split("/");
		TullFolder currentFolder = this.rootTullFolder;
		for(int i=0;i<path.length;i++){
			currentFolder = currentFolder.getFolder(s, create);
		}
		return currentFolder;
	}
	public static TullFileSystem getTFS(){
		return new TullFileSystem(SystemUtils.getProperty("home_directory_subfolder_name"));
	}
}
