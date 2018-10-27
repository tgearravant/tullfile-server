package com.gearreald.tullfileserver.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

import com.gearreald.tullfileserver.utils.SystemUtils;

import net.tullco.tullutils.FileUtils;
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
	public void addPiece(int pieceNumber, byte[] data) throws IOException, NoSuchAlgorithmException{
		File piece = new File(getAbsolutePathToPiece(pieceNumber));
		File pieceSha = new File(getAbsolutePathToPiece(pieceNumber)+".sha1");
		MessageDigest md = MessageDigest.getInstance("SHA-1");

		DigestOutputStream digest = new DigestOutputStream(new FileOutputStream(piece),md);
		digest.write(data);
		digest.close();
		FileUtils.writeStringToFile(DatatypeConverter.printHexBinary(md.digest()), pieceSha);
	}
	public File getPiece(int pieceNumber) throws FileNotFoundException{
		File f = new File(getAbsolutePathToPiece(pieceNumber));
		if(!f.exists())
			throw new FileNotFoundException("The requested piece does not exist.");
		else
			return f;
	}
	public String getPieceHash(int pieceNumber) throws IOException{
		File f = new File(getAbsolutePathToPiece(pieceNumber)+".sha1");
		return FileUtils.getFileAsString(f);
	}
	public int totalPieces(){
		return this.getUnorderedPieces().size();
	}
	private List<File> getUnorderedPieces(){
		File[] files = this.fileLocation.listFiles();
		List<File> pieceFiles = new ArrayList<File>();
		for(File f: files){
			if(f.getName().endsWith(".part"))
				pieceFiles.add(f);
		}
		return pieceFiles;
	}
	public List<File> getPieces(){
		ArrayList<File> orderedFiles = new ArrayList<File>();
		ArrayList<String> filePaths = new ArrayList<String>();
		List<File> files = getUnorderedPieces();
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
	private File getHashFile(){
		String path = StringUtils.assureEndsWith(this.fileLocation.getAbsolutePath(),"/")+"file_hash.sha1";
		File hashFile = new File(path);
		return hashFile;
	}
	/**
	 * Sets the hash of the full file to the given string.
	 * @param hash The full file hash.
	 * @return True if there were no problems. False if it didn't happen.
	 */
	public boolean setFileHash(String hash){
		try{
			File hashFile = getHashFile();
			FileUtils.writeStringToFile(hash, hashFile);
			return true;
		}catch(IOException e){
			return false;
		}
	}
	/**
	 * Gets the hash of the full file. Either uses the cached value or, if that wasn't populated, traverses through the entire
	 * file to generate it.
	 * @return
	 */
	public String getFileHash() {
		try{
			File hashFile = getHashFile();
			return FileUtils.getFileAsString(hashFile);
		}catch(IOException e){}
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			DigestOutputStream dis = new DigestOutputStream(new FileOutputStream(SystemUtils.getNullFile()),md);
			for(File f: this.getPieces()){
				dis.write(FileUtils.getFileAsBytes(f));
			}
			String hash = DatatypeConverter.printHexBinary(md.digest());
			dis.close();
			this.setFileHash(hash);
			return hash;
		} catch (NoSuchAlgorithmException | IOException e) {
			return null;
		}
	}
	public JSONObject toJSON(){
		JSONObject main = new JSONObject();
		main.put("name",this.getName());
		main.put("pieces", this.totalPieces());
		main.put("size", this.fileSize());
		return main;
	}
	public void delete(){
		File[] subfiles = this.fileLocation.listFiles();
		for(File subfile: subfiles){
			subfile.delete();
		}
		this.fileLocation.delete();
	}
	public long fileSize(){
		long totalSize=0;
		for(File f: this.getUnorderedPieces()){
			totalSize+=f.length();
		}
		return totalSize;
	}
	public String getAbsolutePath(){
		return this.fileLocation.getAbsolutePath();
	}
	public String toString(){
		return this.toJSON().toString();
	}
}
