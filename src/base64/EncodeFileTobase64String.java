package base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;



public class EncodeFileTobase64String {


	
	public static String encodeToBase64(){
		
		 File originalFile = new File("F:/GuideWire_Documents/pdfdoc.pdf");
	        String encodedBase64 = null;
	        try {
	            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
	            byte[] bytes = new byte[(int)originalFile.length()];
	            fileInputStreamReader.read(bytes);
	            encodedBase64 = new String(Base64.encodeBase64(bytes));
	            
	            System.out.println(encodedBase64);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }	
	        
	        return encodedBase64;
	}
	
	
	public static void main(String args[]){
		String encodedData=encodeToBase64();
		System.out.println("encode::"+encodedData);
		
	}
	
		  
	}
	
	
	
