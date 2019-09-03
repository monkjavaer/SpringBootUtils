package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

public enum FileType {
	/**
	 * thumbnail
	 */
	THUMBNAIL("thumbnail"),
 
	VIDEO("video"),
	
	SNAPSHOT("snapshot"),
	
	PICTURE("picture"),
	
	OBI("obi"),
	
	UNKNOWN("unknown");
 
	public String prefix;
 
	
	FileType(String prefix) {
		this.prefix = prefix;
	}
	
	
	
	public static FileType getFileType(String relativeFilePath) {
		String pathStr;
		if(relativeFilePath == null) {
            return UNKNOWN;
        }
		
		pathStr = relativeFilePath.trim();
		
		if(pathStr.indexOf(0) == '/' || pathStr.indexOf(0) == '\\') {
			pathStr = pathStr.substring(1, pathStr.length());
		}
		
		int firstSlash = pathStr.indexOf('/');
		if(firstSlash == -1) {
			firstSlash = pathStr.indexOf('\\');
		}
		
		if(firstSlash == -1) {
            return UNKNOWN;
        }
		
		String typeWord = pathStr.substring(0, firstSlash);
		if(THUMBNAIL.prefix.equals(typeWord)) {
			return THUMBNAIL;
		} else if(VIDEO.prefix.equals(typeWord)) {
			return VIDEO;
		} else if(SNAPSHOT.prefix.equals(typeWord)) {
			return SNAPSHOT;
		} else if(PICTURE.prefix.equals(typeWord)) {
			return PICTURE;
		}  else if(OBI.prefix.equals(typeWord)) {
			return OBI;
		} else {
			return UNKNOWN;
		}
	}	
	
 
}