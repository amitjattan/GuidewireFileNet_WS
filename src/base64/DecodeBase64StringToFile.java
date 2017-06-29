package base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.misc.BASE64Decoder;

import org.apache.commons.codec.binary.Base64;

public class DecodeBase64StringToFile {

	
	public static void decodeBase64ToFile(String base64String,String fileName) throws IOException{
		
		fileName="F:/GuideWire_Documents/Download/"+fileName;
		
		
		byte[] base64Bytes = base64String.getBytes();
			  
		byte[] decodedBytes = (byte[]) Base64.decodeBase64(base64Bytes);
			File file = new File(fileName);
	        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
	        writer.write(decodedBytes);
	        writer.flush();
	        writer.close();	
	}
	
	
	public static void main(String[] args) throws IOException {
	
		
		EncodeFileTobase64String e=new EncodeFileTobase64String();
		
		String base64String=e.encodeToBase64();
		String fileName="";
		System.out.println(base64String);	
		decodeBase64ToFile(base64String,fileName);

	}

}
