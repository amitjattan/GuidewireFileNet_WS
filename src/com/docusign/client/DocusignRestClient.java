package com.docusign.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;


public class DocusignRestClient
{	
	/**
	 * @param authenticationHeader
	 * @param baseURL
	 * @throws IOException
	 */
	public String generateEnvelopes( String documentBytes, String documentGUID, String userName, String userEmail)
			throws IOException {
		String url;
		String body;
		String response;
		int status;
		HttpURLConnection conn;
		String baseURL = "https://demo.docusign.net/restapi/v2/accounts/5091776";//DocuSignUtils.getBaseUrl(authenticationHeader, url);
		String authenticationHeader = "{ \"Username\": \"amit.kumar4@standard.com\", \"Password\": \"mysic@2018\", \"IntegratorKey\": \"3ede30c4-b703-4c5d-ac71-0c23919808f7\" }";
		url = baseURL + "/envelopes";
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		writer.println("--AAA");
		writer.println("Content-Type: application/json");
		writer.println("Content-Disposition: form-data");
		writer.println("");
		writer.println("{");
		writer.println("\"status\":\"sent\",");
		writer.println("\"emailBlurb\":\"Please sign the document\",");
		writer.println("\"emailSubject\": \"Document to be signed\",");
		writer.println("\"documents\": [{");
		writer.println("\"matchBoxes\": ["
				+ "{"
				+ "\"height\":\"8\","
				+ "\"pageNumber\":\"1\","
				+ "\"width\":\"10\","
				+ "\"xPosition\":\"25\","
				+ "\"yPosition\":\"25\""
				+ "}],");
		writer.println("\"name\": \""
				+ userName
				+ "\",");
		writer.println("\"documentId\":\"1\",");
		writer.println("\"order\":\"1\"");
		writer.println("}]"+",");
		writer.println("\"recipients\": {");
		writer.println("\"signers\" : [{");
		writer.println("\"email\": \""
				+ userEmail
				+ "\",");
		writer.println("\"name\": \""
				+ userName
				+ "\",");
		writer.println("\"recipientId\":\"1\"");
		writer.println("}]");
		writer.println("}");
		writer.println("}");
		writer.println("");
		writer.println("--AAA");
		writer.println("Content-Type: application/pdf");
		writer.println("Content-Disposition: file; filename=\""
				+ documentGUID
				+ "\"; documentid=\"1\"");
		writer.println("");

		body = stringWriter.toString();
		String reqBody2 = "\r\n" + "--AAA--\r\n\r\n";
		conn = DocuSignUtils.InitializeRequest(url, "POST", body, authenticationHeader);
		conn = (HttpURLConnection)new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("X-DocuSign-Authentication", authenticationHeader);
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=AAA");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Length", Integer.toString(body.length()));
		conn.setDoOutput(true);
		// write body of the POST request 
		DataOutputStream dos = new DataOutputStream( conn.getOutputStream() );
		dos.writeBytes(body);
		if(documentBytes!=null){
			dos.write(Base64.decodeBase64(documentBytes.getBytes()));
		}
		dos.writeBytes(reqBody2.toString()); 
		dos.flush(); dos.close();
		status = conn.getResponseCode(); // triggers the request
		if( status != 201 )	// 200 = OK
		{
			DocuSignUtils.errorParse(conn, status);
			return null;
		}
		response = DocuSignUtils.getResponseBody(conn);
		return response;
	}
}
