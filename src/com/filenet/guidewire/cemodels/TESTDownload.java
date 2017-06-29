package com.filenet.guidewire.cemodels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.Id;
import com.filenet.guidewire.manager.CEConnectionManager;

public class TESTDownload {



	public static void downloadDocumentFromCE(String DocumentGUID ) throws IOException, GeneralSecurityException{

		CEConnectionManager CE=new CEConnectionManager();
		ObjectStore objectStore = CE.getObjectStore_Dev();
		//String 	DocumentGUID="{C14C9117-0F3C-49BD-95EC-1452BCD328F0}";
		Document document = Factory.Document.fetchInstance(objectStore, DocumentGUID, null);
		// get the GUID before creating directory
		String folderId = document.get_Id().toString();
		System.out.println(document.get_Name());
		String ext="";
		String docName=document.get_Name();

		String mimeType = document.get_MimeType();

		File file = new File("E:\\GuideWire_Documents\\Download\\"); 
		System.out.println(file.mkdirs());
		file = new File("E:\\GuideWire_Documents\\Download\\" + docName + "." + ext);
		System.out.println(file.getAbsolutePath());
		file.createNewFile();
		ContentElementList docContentList = document.get_ContentElements();
		java.util.Iterator iter = docContentList.iterator();
		ContentTransfer ct = (ContentTransfer) iter.next();
		InputStream fileStream = ct.accessContentStream();
		FileOutputStream fos = new FileOutputStream(file);
		byte byteArray[] = new byte[4096];
		int read = 0;
		while ((read = fileStream.read(byteArray)) > 0) {
			fos.write(byteArray, 0, read);
		}


	}


	public static void main(String nag[]) throws GeneralSecurityException, IOException
	{


		String DocumentGUID="{C14C9117-0F3C-49BD-95EC-1452BCD328F0}";
		downloadDocumentFromCE(DocumentGUID);
	}
}
