package com.filenet.guidewire.cemodels;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.net.ftp.FTPFile;

import base64.CheckFileInFTP;
import base64.MoveFile;
import sun.misc.BASE64Decoder;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.property.Properties;
import com.filenet.api.util.Id;
import com.filenet.guidewire.manager.CEConnectionManager;
import com.filenet.guidewire.manager.Messages;

public class CEDocumentUpload {

	// TODO Auto-generated constructor stub

	public  String uploadDocumentToCE(String Base64EncodedString,String DocumentName) throws GeneralSecurityException, IOException {

		//String localFolderPath = "E:\\GuideWire_Documents\\" + DocumentName;

		//File file = new File(localFolderPath);

		String docGUID = null;

		String docClass = Messages.getString("CE_OPS_CLASS_NAME");

		String CEfolderPath = Messages.getString("CE_OPS_CE_FOLDER");

		InputStream attStream=decodeBase64StringToInsputStream(Base64EncodedString);
		

		try {

			String docMimeType = findFileType(DocumentName);
			System.out.println("docMimeType::" + docMimeType);

			docGUID = addDocumentWithStream(CEfolderPath, attStream, docMimeType, DocumentName, docClass);

		} catch (FileNotFoundException e) {

			System.out.println("Exception:" + e.getMessage());

			e.printStackTrace();
		}


		return docGUID;

	}// end of method

	public  static InputStream convertByteArrToInputStream(byte[] Documentcontent) throws IOException{



		InputStream is = null;

		try {
			is = new ByteArrayInputStream(Documentcontent);       
		} finally {
			try{
				if(is != null) is.close();
			} catch (Exception ex){

			}
		}

		return is;
	}
	public String addDocumentWithStream(String CEfolderPath, InputStream inputStream, String mimeType, String docName,
			String docClass) {

		String docGUID = null;
		try {
			CEConnectionManager CE = new CEConnectionManager();
			ObjectStore objectStore = CE.getObjectStore_Dev();
			Folder folder = Factory.Folder.fetchInstance(objectStore, CEfolderPath, null);

			Document doc = Factory.Document.createInstance(objectStore, null);

			ContentElementList contEleList = Factory.ContentElement.createList();
			ContentTransfer ct = Factory.ContentTransfer.createInstance();

			ct.setCaptureSource(inputStream);

			// ct.set_ContentType(mimeType);
			ct.set_RetrievalName(docName);

			contEleList.add(ct);

			doc.set_ContentElements(contEleList);
			doc.getProperties().putValue("DocumentTitle", docName);

			System.out.println("docClass::" + docClass);

			doc.set_MimeType(mimeType);

			doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);

			doc.save(RefreshMode.REFRESH);
			doc.changeClass(docClass);
			doc.save(RefreshMode.REFRESH);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Date today = Calendar.getInstance().getTime();

			String reportDate = df.format(today);

			System.out.println("System Date::" + reportDate);

			Date convertStringToDate = convertStringToDate(reportDate);
			doc.getProperties().putValue("CreationDate", convertStringToDate);

			doc.save(RefreshMode.REFRESH);
			ReferentialContainmentRelationship rcr = folder.file(doc, AutoUniqueName.AUTO_UNIQUE, docName,
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			rcr.save(RefreshMode.REFRESH);

			com.filenet.api.property.Property[] docProperties = doc.getProperties().toArray();

			docGUID = doc.get_Id().toString();

			System.out.println("Document GUID::" + doc.get_Id());

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("Exception::" + e.getMessage());
		}
		return docGUID;

	}

	private static Date convertStringToDate(String dateProperty) throws Exception {
		// SimpleDateFormat for formatting the date
		System.out.println("SystemDate:" + dateProperty);
		// SimpleDateFormat isDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		SimpleDateFormat isDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		// isDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String datePropValue;
		Date dateFormat = null;
		try {
			if (!dateProperty.equalsIgnoreCase("<null>")) {
				dateFormat = isDateFormat.parse(dateProperty);
				// Formatting date into text
				datePropValue = isDateFormat.format(dateFormat);
				// Parse a string into date format
				dateFormat = isDateFormat.parse(datePropValue);
				System.out.println("dateFormat::" + dateFormat);

			}
		} catch (ParseException parseException) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			parseException.printStackTrace(pw);
			throw parseException;
		}
		return dateFormat;
	}// End of convertStringToDate()method

	public static String findFileType(String fileName) throws FileNotFoundException {
		String mymeType = "";

		try {

			// System.out.println("checkline::"+checkline);
			if (fileName.endsWith(".pdf")) {

				mymeType = Messages.getString("CE.MIME.PDF");
			} else if (fileName.endsWith(".xlsx")) {

				mymeType = Messages.getString("CE.MIME.XLSX");

			}

			else if (fileName.endsWith(".docx")) {

				mymeType = Messages.getString("CE.MIME.PDF");

			} else if (fileName.endsWith(".txt")) {

				mymeType = Messages.getString("CE.MIME.TEXTDOC");

			} else if (fileName.endsWith(".jpg")) {

				mymeType = Messages.getString("CE.MIME.IMAGE");

			} else {

				System.out.println("File doesnot matched for any of type");
				mymeType = null;
			}

		} catch (Exception e) {

			System.out.println("Exception:" + e.getMessage());
			// TODO: handle exception
		}
		return mymeType;

	}
 static InputStream decodeBase64StringToInsputStream(String base64String) throws IOException{
		
		 
		 BASE64Decoder decoder = new BASE64Decoder();
		 byte[] decodedBytes = decoder.decodeBuffer(base64String);	 
		 InputStream is = new ByteArrayInputStream(decodedBytes);
	    System.out.println("IS::"+is.toString());
		return is;
		
	}

	public static void main(String nag[]) throws GeneralSecurityException, IOException {

		/*
		 * //String CEfolderPath = "/Guidewire/";
		 * 
		 * 
		 * 
		 * 
		 * Id docGUID=uploadDocumentToCE(localFolderPath);
		 * 
		 * System.out.println("DocGUID="+docGUID);
		 */

		/*
		 * String localFolderPath="E:\\GuideWire_Documents\\"; File folder = new
		 * File(localFolderPath); File[] listOfFiles = folder.listFiles();
		 * System.out.println("listOfFiles::"+listOfFiles.length);
		 * 
		 * for (int i = 0; i < listOfFiles.length; i++) { if
		 * (listOfFiles[i].isFile()) {
		 * 
		 * CEDocumentUpload ce=new CEDocumentUpload(); String
		 * docGUID=ce.uploadDocumentToCE();
		 * System.out.println("DocGUID::"+docGUID); }
		 * 
		 * }
		 */


		//byte[] pdfBytes = new File("//file1.pdf").byte;
				
				
		byte[] arraypdf = Files.readAllBytes(new File("F:\\GuideWire_Documents\\pdfdoc.pdf").toPath());
		byte[] arraytxt = Files.readAllBytes(new File("F:\\GuideWire_Documents\\txtdoc.txt").toPath());
		String fileName="pdfdoc.pdf";
		System.out.println("arraypdf:"+arraypdf);

		System.out.println("arraytxt:"+arraytxt.toString());

		//CEDocumentUpload c=new CEDocumentUpload();
		//String docGUID = c.uploadDocumentToCE(array,fileName);

		//System.out.println("DocGUID="+docGUID);

	}

}
