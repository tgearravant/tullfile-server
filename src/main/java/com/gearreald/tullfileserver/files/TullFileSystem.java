package com.gearreald.tullfileserver.files;

import java.io.File;
import java.io.FileNotFoundException;

import com.gearreald.tullfileserver.utils.SystemUtils;

import net.tullco.tullutils.StringUtils;

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
		return this.rootTullFolder;
	}
	public TullFolder getTullFolderAtPath(String s, boolean create) throws FileNotFoundException {
		String localPath = StringUtils.assureStartsWith(s, "/");
		if(localPath.equals("/"))
			return this.getRootFolder();
		String[] path = localPath.split("/");
		TullFolder currentFolder = this.rootTullFolder;
		for(int i=1;i<path.length;i++){
			currentFolder = currentFolder.getFolder(path[i], create);
		}
		return currentFolder;
	}
	public static TullFileSystem getTFS(){
		return new TullFileSystem(SystemUtils.getProperty("home_directory_subfolder_name"));
	}
}
