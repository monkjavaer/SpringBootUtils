package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import com.car.base.common.exception.ConnectionException;
import com.car.base.common.exception.PermissionException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FtpClient {
	
	public int serverPort = 21;
	public String serverIp = "172.16.3.20";
	public String username = "oms";
	public String password = "oms";
	
	public FtpClient(String serverIp, int serverPort, String username, String password) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.username = username;
		this.password = password;
	}
	
	
	
	/**
	 * 
	 * @param localFilename - full path of file to upload, including path and filename
	 * @param remoteFilePathAndName - the filename on the ftp server, if 
	 * 		 the parent directory of the file does not exist, then create the directory recursively 
	 * @throws IOException 
	 */
	public void uploadFile(String localFilePathAndName, String remoteFilePathAndName) 
			throws ConnectionException, PermissionException, IOException {
		FTPClient ftpClient = new FTPClient();
		try{
			FileInputStream inputStream = new FileInputStream(localFilePathAndName);
			
			//Setup connection
			ftpClient.connect(serverIp, serverPort);
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
				throw e;
			}
			
			//Login
			if(ftpClient.login(username, password) == false) {
				PermissionException e = new PermissionException("Invalid username/password.");
				throw e;
			}
			
			//File Type
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			if(remoteFilePathAndName.indexOf("/") > -1) {
				String parentDir = remoteFilePathAndName.substring(0, remoteFilePathAndName.lastIndexOf("/"));
				if(!ftpClient.changeWorkingDirectory(parentDir)) {
					//The parentDir is not exist
					//boolean result = ftpClient.makeDirectory(parentDir);
					//The parentDir is not exist
					 makeDir(ftpClient, parentDir);
					//boolean result2 = ftpClient.makeDirectory("abc");
					//System.out.println(result);
				}  else {
					ftpClient.changeWorkingDirectory("/");
				}
			}
			
			//transmit file
			boolean success = ftpClient.storeFile(remoteFilePathAndName, inputStream);
			
			ftpClient.logout();
		} catch(IOException e) {
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}

	}
	
	/**
	 * 
	 * @param localFilename - full path of file to upload, including path and filename
	 * @param remoteFilePathAndName - the filename on the ftp server, if 
	 * 		 the parent directory of the file does not exist, then create the directory recursively 
	 * @throws IOException 
	 */
	public void uploadFile(InputStream inputStream, String remoteFilePathAndName) 
			throws ConnectionException, PermissionException, IOException {
		FTPClient ftpClient = new FTPClient();
		try{
			
			//Setup connection
			ftpClient.connect(serverIp, serverPort);
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
				throw e;
			}
			
			//Login
			if(ftpClient.login(username, password) == false) {
				PermissionException e = new PermissionException("Invalid username/password.");
				throw e;
			}
			
			//File Type
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			
			if(remoteFilePathAndName.indexOf("/") > -1) {
				String parentDir = remoteFilePathAndName.substring(0, remoteFilePathAndName.lastIndexOf("/"));
				if(!ftpClient.changeWorkingDirectory(parentDir)) {//e.g., upload/image/2015/12/29
					//The parentDir is not exist
					 makeDir(ftpClient, parentDir);
				}  else {
					 ftpClient.changeWorkingDirectory("/");
				}
			}

			
			//transmit file
			boolean success = ftpClient.storeFile(remoteFilePathAndName, inputStream);
			
			ftpClient.logout();
		} catch(IOException e) {
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}

	}
	
	public void deleteFile(String remoteFilePathAndName) 
			throws ConnectionException, PermissionException, IOException {
		FTPClient ftpClient = new FTPClient();
		try{
			
			//Setup connection
			ftpClient.connect(serverIp, serverPort);
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
				throw e;
			}
			
			//Login
			if(ftpClient.login(username, password) == false) {
				PermissionException e = new PermissionException("Invalid username/password.");
				throw e;
			}
			
			//File Type
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			//delete file
			ftpClient.deleteFile(remoteFilePathAndName);
			
			ftpClient.logout();
		} catch(IOException e) {
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}
	}
	/**
	 * 
	 * @param remotePath - 
	 * When make directory 2015/12/13, if the directory 2015/12 does not exist，the method 
	 * will first create 2015/12, and then create 2015/12/13
	 * @throws ConnectionException
	 * @throws PermissionException
	 * @throws IOException
	 */
	public void makeDirectory(String remotePath) 
		throws ConnectionException, PermissionException, IOException  {
		FTPClient ftpClient = new FTPClient();
		try{
			
			//Setup connection
			ftpClient.connect(serverIp, serverPort);
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
				throw e;
			}
			
			//Login
			if(ftpClient.login(username, password) == false) {
				PermissionException e = new PermissionException("Invalid username/password.");
				throw e;
			}
			
			//File Type
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			//delete file
 			
			if(!ftpClient.makeDirectory(remotePath)) {
				String pwd = ftpClient.printWorkingDirectory();
				ftpClient.changeWorkingDirectory("/");
				String[] strList = remotePath.split("/");
				for(String item: strList) {
					FTPFile[] files = ftpClient.listDirectories();
					boolean exist = false;
					for(FTPFile file: files) {
						System.out.println(  " - " + file.getName());
						if(file.getName().equals(item)) {
							exist = true;
							break;
						}
					}
					 
					if(!exist) {
						boolean result = ftpClient.makeDirectory(item);
					}
					ftpClient.changeWorkingDirectory(item);
				}
				ftpClient.changeWorkingDirectory(pwd);
			}
			
			
			ftpClient.logout();
		} catch(IOException e) {
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}
	}
	
	public void makeDir(FTPClient ftpClient, String remotePath) {
		try{
			if(!ftpClient.makeDirectory(remotePath)) {
				String pwd = ftpClient.printWorkingDirectory();
				ftpClient.changeWorkingDirectory("/");
				String[] strList = remotePath.split("/");
				for(String item: strList) {
					FTPFile[] files = ftpClient.listDirectories();
					boolean exist = false;
					for(FTPFile file: files) {
						System.out.println(  " - " + file.getName());
						if(file.getName().equals(item)) {
							exist = true;
							break;
						}
					}
					 
					if(!exist) {
						boolean result = ftpClient.makeDirectory(item);
					}
					ftpClient.changeWorkingDirectory(item);
				}
				ftpClient.changeWorkingDirectory(pwd);
			}		
		}catch(IOException ex) {
			
		}

	}
	
	public void removeDirectory(String remotePath) 
			throws ConnectionException, PermissionException, IOException {
		FTPClient ftpClient = new FTPClient();
		try{
			
			//Setup connection
			ftpClient.connect(serverIp, serverPort);
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
				throw e;
			}
			
			//Login
			if(ftpClient.login(username, password) == false) {
				PermissionException e = new PermissionException("Invalid username/password.");
				throw e;
			}
			
			//File Type
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			//delete file
			ftpClient.removeDirectory(remotePath);
			
			ftpClient.logout();
		} catch(IOException e) {
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}
	}
	
	public void downloadFile(String remoteFilePathAndName, String localFilePathAndName) 
			throws ConnectionException, PermissionException, IOException {
		FTPClient ftpClient = new FTPClient();
		try{
			OutputStream outputStream = new FileOutputStream(localFilePathAndName);
			
			//Setup connection
			ftpClient.connect(serverIp, serverPort);
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
				throw e;
			}
			
			//Login
			if(ftpClient.login(username, password) == false) {
				PermissionException e = new PermissionException("Invalid username/password.");
				throw e;
			}
			
			//File Type
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			//transmit file
			ftpClient.retrieveFile(remoteFilePathAndName, outputStream);
			
			ftpClient.logout();
		} catch(IOException e) {
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}
	}

    /**
     * Determines whether a file exists or not
     * @return true if exists, false otherwise
     * @throws IOException thrown if any I/O error occurred.
     */
	public boolean checkFileExists(String remoteFilePathAndName) 
			throws ConnectionException, PermissionException, IOException {
		FTPClient ftpClient = new FTPClient();
		
		//Setup connection
		ftpClient.connect(serverIp, serverPort);
		int reply = ftpClient.getReplyCode();
		if(!FTPReply.isPositiveCompletion(reply)) {
			ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
			throw e;
		}
		
		//Login
		if(ftpClient.login(username, password) == false) {
			PermissionException e = new PermissionException("Invalid username/password.");
			throw e;
		}
		
		//File Type
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		try{
			
	        InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePathAndName);
	        Integer returnCode = ftpClient.getReplyCode();
			return !(inputStream == null || returnCode == 550);

		} catch(IOException e) {
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}	        
    }

	/**
	 * 递归遍历出目录下面所有文件
	 * @param pathName 需要遍历的目录，必须以"/"开始和结束
	 * @throws IOException
	 */
	public List<String> list(String pathName) throws ConnectionException, PermissionException, IOException {
		FTPClient ftpClient = new FTPClient();
		List<String> fileNameList = new ArrayList<String>();
		try {
			//Setup connection
			ftpClient.connect(serverIp, serverPort);
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
                ConnectionException e = new ConnectionException("Cannot connect to " + serverIp + ":" + serverPort);
                throw e;
            }

			//Login
			if(ftpClient.login(username, password) == false) {
                PermissionException e = new PermissionException("Invalid username/password.");
                throw e;
            }

			//File Type
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


			if(pathName.startsWith("/")&&pathName.endsWith("/")){
                String directory = pathName;
                //更换目录到当前目录
                ftpClient.changeWorkingDirectory(directory);
                FTPFile[] files = ftpClient.listFiles();
                for(FTPFile file:files){
                    if(file.isFile()){
                        fileNameList.add(directory+file.getName());
                    }else if(file.isDirectory()){
						list(directory+file.getName()+"/");
                    }
                }
            }
			ftpClient.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (PermissionException e) {
			e.printStackTrace();
		}finally {
			try {
				ftpClient.disconnect();
			} catch(IOException e) {
				throw e;
			}
		}
		return fileNameList;
	}
	
	public static void main(String[] args) {
		//FtpClient ftpClient = new FtpClient("172.16.3.20", 21, "oms", "oms");
		FtpClient ftpClient = new FtpClient("172.16.3.156", 21, "godseye", "Car@123");
		//FtpClient ftpClient = new FtpClient("172.22.6.232", 21, "godseye", "Car@123");
		int count = 0;
		long timeStart = System.currentTimeMillis();


		List<String> list = null;
		try {

			String s = "/var/ftp/file/snapshot/2017/09/04/2C69C9C5-5653-4594-8D40-FD4D969F68DE/CAEA15B5-93AA-430C-BD3A-0A54CAC32EAB/target_re_0__0.0.png";
			System.out.println(s.substring(s.lastIndexOf("file/") + 4, s.length()));

			list = ftpClient.list("/mnt/hdfs/data/snapshot/2017/08/04/B9B62948-24D2-4957-8A8C-F79378A3D721/");
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (PermissionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i=0 ;i <list.size();i++){
			System.out.println(list.get(i));
		}

/*
		while(true){
			try{
				System.out.println("---Start---");
				
				ftpClient.uploadFile("/home/Car/myfiles/111.jpg", "/var/ftp/file/features/"+count+".jpg");
				String ss = "/home/Car/12345/1111122222.jpg";
				int a = ss.lastIndexOf("/");
				String sss = ss.substring(0,a+1);
				new File(sss).mkdirs();

				ftpClient.downloadFile("/var/ftp/file/snapshot/2017/08/10/REALTIME/HUMAN_1552031528-000000-000000.jpg", "/home/12345/1111122222.jpg");
				if(count==1000){
					long timeStop = System.currentTimeMillis();
					System.out.println(timeStop-timeStart);
					break;
				}
				//ftpClient.uploadFile("D:/ftpRoot/upload/image/2015/11/12/123.jpg", "upload/image/2015/11/12/123.jpg");
				//ftpClient.downloadFile("upload/image/2015/12/29/222.jpg", "d:/222.jpg");
				
				//ftpClient.makeDirectory("upload/image/2015/11/32"); 
				//ftpClient.removeDirectory("upload/images/2015/11/05");
				//ftpClient.deleteFile("upload/abc.jpg");
				System.out.println("---Done---");
				
				System.out.println();
			}catch(Exception e) {
				
			}
		}*/
		
		
	}
}
