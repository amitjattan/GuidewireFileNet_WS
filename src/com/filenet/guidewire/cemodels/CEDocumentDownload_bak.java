package com.filenet.guidewire.cemodels;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.codec.binary.Base64;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;
import com.filenet.guidewire.manager.CEConnectionManager;
import com.filenet.guidewire.manager.Messages;
import com.sun.xml.internal.bind.v2.model.core.ID;

public class CEDocumentDownload_bak {



	public  String downloadDocumentFromCE(String DocumentGUID ) throws  GeneralSecurityException, IOException{


		String base64EncodedString=null;
		File originalFile=null;
		CEConnectionManager CE=new CEConnectionManager();

		ObjectStore objectStore;
		Document document = null;
		
		if(DocumentGUID.length()!=38){
			
			base64EncodedString="Invalid GUID";
			return base64EncodedString;		
		}
		
		
		try {
			objectStore = CE.getObjectStore_Dev();
			
			
			
			document = Factory.Document.fetchInstance(objectStore, DocumentGUID, null);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			base64EncodedString="Invalid GUID";
			 throw new RuntimeException(e1);
			
		
			// TODO Auto-generated catch block
			
		}
	
	
		
	
		String docName=document.get_Name();
		System.out.println("Document Name:"+docName);	
		String mimeType = document.get_MimeType();
		System.out.println("mimeType::"+mimeType);
		
		String	fileExtension=findFileType(mimeType);
		originalFile = new File("C:\\temp\\" + docName);
		System.out.println("AbsolutePath::"+originalFile.getAbsolutePath());
		originalFile.createNewFile();
		ContentElementList docContentList = document.get_ContentElements();
		java.util.Iterator iter = docContentList.iterator();
		ContentTransfer ct = (ContentTransfer) iter.next();
		InputStream fileStream = ct.accessContentStream();
		FileOutputStream fos = new FileOutputStream(originalFile);
		byte byteArray[] = new byte[4096];
		int read = 0;
		while ((read = fileStream.read(byteArray)) > 0) {
			fos.write(byteArray, 0, read);
			
		}
		
		fos.close();

		try {
			FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
			byte[] bytes = new byte[(int)originalFile.length()];
			fileInputStreamReader.read(bytes);
			base64EncodedString = new String(Base64.encodeBase64(bytes));

			System.out.println(base64EncodedString);
			
			fileInputStreamReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		originalFile.delete();

		return base64EncodedString;
	}

	public static String findFileType(String  myType) throws FileNotFoundException{
		String fileTypeExtension="";



		try{


			if(myType.equalsIgnoreCase(Messages.getString("CE.MIME.PDF"))){

				fileTypeExtension=".pdf";
			}
			else if(myType.equalsIgnoreCase(Messages.getString("CE.MIME.XLSX"))){

				fileTypeExtension=".xlsx";
			}		
			else if(myType.equalsIgnoreCase(Messages.getString("CE.MIME.WORDDOC"))){

				fileTypeExtension=".docx";
			}	

			else if(myType.equalsIgnoreCase(Messages.getString("CE.MIME.TEXTDOC"))){

				fileTypeExtension=".txt";
			}

			else if(myType.equalsIgnoreCase(Messages.getString("CE.MIME.IMAGE"))){

				fileTypeExtension=".jpg";
			}	

			else{

				System.out.println("File doesnot matched for any of type");
				fileTypeExtension=null;
			}

		}catch (Exception e) {

			System.out.println("Exception:"+e.getMessage());
			// TODO: handle exception
		}
		return fileTypeExtension;




	}

	public static void main(String nag[]) throws GeneralSecurityException, IOException
	{


		String DocumentGUID="{D5DE56C7-5C6F-45C1-1B10-BEB51F63F6D8}";
		
		System.out.println(DocumentGUID.length());
		
		
		
		CEDocumentDownload_bak ce=new CEDocumentDownload_bak();
		String base64Encode=ce.downloadDocumentFromCE(DocumentGUID);
		System.out.println("base64Encode::"+base64Encode);
	}

}
