package org.daiayum.examples.jcifs;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;

public class JCIFS {
	
	final static String USER_NAME="dyumkhaibam";
	final static String PASSWORD="Evoke1234";
	final static String DOMAIN="evoketechnologies.com";
	final static String DESTINATION_PATH="smb://10.12.4.245/SharedFolder/";
	final static String ORIGINAL_FILENAME="D:/test.txt";
	final static String NEW_FILE_NAME="e431.txt";

	public byte[] fileToBytes(String originalFileName) {
		File file = new File(ORIGINAL_FILENAME);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				System.out.println("read " + readNum + " bytes,");
			}
			fis.close();
			byte[] bytes = bos.toByteArray();
			return bytes;
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return null;
	}

	public void copyFile(byte[] originalBytes, String newFileName) {			
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(DOMAIN, USER_NAME, PASSWORD);
		SmbFile sFile;		   
	    
		try {
			sFile = new SmbFile(DESTINATION_PATH + newFileName, auth);			
			
			SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
			sfos.write(originalBytes);
			sfos.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JCIFS cifs = new JCIFS();
		cifs.copyFile(cifs.fileToBytes(ORIGINAL_FILENAME), NEW_FILE_NAME);
	}

}
