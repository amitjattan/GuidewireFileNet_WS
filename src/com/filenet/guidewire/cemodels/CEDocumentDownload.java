package com.filenet.guidewire.cemodels;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;

import com.filenet.api.collection.RepositoryRowSet;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.query.RepositoryRow;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.guidewire.manager.CEConnectionManager;
import com.filenet.guidewire.manager.Messages;

public class CEDocumentDownload {

	public String downloadDocumentFromCE(String DocumentGUID) throws GeneralSecurityException, IOException {

		String base64EncodedString = null;

		/*if (DocumentGUID.length() != 38) {

			base64EncodedString = "Invalid GUID";
			return base64EncodedString;
		}*/

		CEConnectionManager CE = new CEConnectionManager();

		ObjectStore objectStore = CE.getObjectStore_Dev();
		Document document = null;//Factory.Document.fetchInstance(objectStore, DocumentGUID, null);

		 String mySQLString = "SELECT ID FROM Document WHERE DocumentTitle LIKE '%"+DocumentGUID+"%'";
		    SearchSQL sqlObject = new SearchSQL();
		    sqlObject.setQueryString(mySQLString);

		   // System.out.println(mySQLString);
		    SearchScope searchScope = new SearchScope(objectStore);
		    RepositoryRowSet rowSet = searchScope.fetchRows(sqlObject, null, null, new Boolean(true));
		    Iterator<Object> ppg = rowSet.iterator();

		    if (ppg.hasNext()) {

		            RepositoryRow rr = (RepositoryRow) ppg.next();
		            System.err.println(rr.getProperties());
		            Properties properties = rr.getProperties();

		            String ID = properties.getIdValue("ID").toString();
		            System.out.println(ID);
		           // System.out.println(properties.getIdValue(prp));


		            document  =  Factory.Document.fetchInstance(objectStore,ID,null);
		            String docName = document.get_Name();
		    		System.out.println("Document Name:" + docName);
		    		String mimeType = document.get_MimeType();
		    		System.out.println("mimeType::" + mimeType);

		    		ContentTransfer contentTransfer = (ContentTransfer) document.get_ContentElements().get(0);
		    		InputStream inputStream = contentTransfer.accessContentStream();

		    		byte[] bytes = getBytes(inputStream);
		    		base64EncodedString = new String(Base64.encodeBase64(bytes));

		    		System.out.println(base64EncodedString);

		    }
		return base64EncodedString;
	}

	public static byte[] getBytes(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}

	public static String findFileType(String myType) throws FileNotFoundException {
		String fileTypeExtension = "";

		try {

			if (myType.equalsIgnoreCase(Messages.getString("CE.MIME.PDF"))) {

				fileTypeExtension = ".pdf";
			} else if (myType.equalsIgnoreCase(Messages.getString("CE.MIME.XLSX"))) {

				fileTypeExtension = ".xlsx";
			} else if (myType.equalsIgnoreCase(Messages.getString("CE.MIME.WORDDOC"))) {

				fileTypeExtension = ".docx";
			}

			else if (myType.equalsIgnoreCase(Messages.getString("CE.MIME.TEXTDOC"))) {

				fileTypeExtension = ".txt";
			}

			else if (myType.equalsIgnoreCase(Messages.getString("CE.MIME.IMAGE"))) {

				fileTypeExtension = ".jpg";	
			}

			else {

				System.out.println("File doesnot matched for any of type");
				fileTypeExtension = null;
			}

		} catch (Exception e) {

			System.out.println("Exception:" + e.getMessage());
			// TODO: handle exception
		}
		return fileTypeExtension;

	}

	public static void main(String nag[]) throws GeneralSecurityException, IOException {

		String DocumentGUID = "{D5DE56C7-5C6F-45C1-1B10-BEB51F63F6D8}";

		System.out.println(DocumentGUID.length());

		CEDocumentDownload ce = new CEDocumentDownload();
		String base64Encode = ce.downloadDocumentFromCE(DocumentGUID);
		System.out.println("base64Encode::" + base64Encode);
	}

}
