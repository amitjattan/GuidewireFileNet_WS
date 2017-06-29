package com.filenet.webservice;


import java.io.IOException;
import java.security.GeneralSecurityException;

import com.docusign.client.DocusignRestClient;
import com.filenet.guidewire.cemodels.CEDocumentDownload;
import com.filenet.guidewire.cemodels.CEDocumentUpload;

import java.io.BufferedReader;
import java.util.ArrayList;

import java.util.List;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class AddRetrievDocument {

	
	
	public  String addDocumentToCE(String Base64EncodedString,String documentName) throws GeneralSecurityException, IOException {
		
		CEDocumentUpload uploadDoc=new CEDocumentUpload();
		
		String docGUID= uploadDoc.uploadDocumentToCE(Base64EncodedString,documentName);
		
		return docGUID;		
	}
	
	
	
	public String retrieveDocumentFromCE(String documentGUID) throws IOException, GeneralSecurityException{
		
		String base64EncodedString=null;
		CEDocumentDownload docDownload=new CEDocumentDownload();
		base64EncodedString = docDownload.downloadDocumentFromCE(documentGUID);
	    
		return base64EncodedString;
	   
		}
	
	
	public String signDocument(String documentGUID, String userName, String userEmail) throws IOException, GeneralSecurityException{

        CEDocumentDownload docDownload=new CEDocumentDownload();
        String base64EncodedString = docDownload.downloadDocumentFromCE(documentGUID);
        // additional variable declarations
        DocusignRestClient  client = new DocusignRestClient();
  /*    File file = new File("E:\\Users\\amitk.kmr\\Downloads\\Montana State Fund\\Doxee\\policyXML\\043454_0000000000797468.pdf");
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[] 
        fis.close();
        String base64EncodedString = Base64.getEncoder().encodeToString(bytesArray);
*/          String envelopes = client.generateEnvelopes(base64EncodedString, documentGUID, userName, userEmail);
        System.out.println(envelopes);
        return envelopes;
  }


	public String onDemandDocumentGeneration(String encodedBomXML, String encodedDataXML){

		String url="https://eu2.cloud.doxee.com/ondemand-ws/test/HCL/UJTI/MSFODP/envMSFODP";

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			System.out.println(encodedBomXML);
			urlParameters.add(new BasicNameValuePair("bomXml",encodedBomXML));
			System.out.println("");
			System.out.println(encodedDataXML);
			urlParameters.add(new BasicNameValuePair("dataXml",encodedDataXML));
			UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(urlParameters);
			System.out.println(encodedFormEntity.getContentType());
			post.setEntity(encodedFormEntity);
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			String resultStr = result.toString();
			System.out.println(resultStr);
			return resultStr;
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		} catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IllegalStateException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
	}
	
	public int add(int a,int b ) {
		

	    
		return a+b;
	   
		}
	
	
	public int testWS(int a,int b ) {
		

	    
		return a+b;
	   
		}
	
	
	
}
