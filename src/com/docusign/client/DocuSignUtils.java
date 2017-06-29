/**
 * 
 */
package com.docusign.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

/**
 * @author amitk.kmr
 *
 */
public class DocuSignUtils {
	/**
	 * @param authenticationHeader
	 * @return
	 * @throws IOException
	 */
	public static String getBaseUrl(String authenticationHeader, String url)
			throws IOException {


		String baseURL;
		String accountId;
		String body;
		String response;
		int status;
		HttpURLConnection conn = null;		// connection object used for each request

		//============================================================================
		// STEP 1 - Make the Login API call to retrieve your baseUrl and accountId
		//============================================================================

		body = "";	// no request body for the login call

		// create connection object, set request method, add request headers
		conn = InitializeRequest(url, "GET", body, authenticationHeader);

		// send the request
		//logger.info("Step 1:  Sending Login request...\n");
		conn.setConnectTimeout(0);
		conn.setReadTimeout(0);

		status = conn.getResponseCode();
		if( status != 200 )	// 200 = OK
		{
			errorParse(conn, status);
			return null;
		}

		// obtain baseUrl and accountId values from response body 
		response = getResponseBody(conn);
		baseURL = parseXMLBody(response, "baseUrl");
		accountId = parseXMLBody(response, "accountId");
		//logger.info("-- Login response --\n\n" + prettyFormat(response, 2) + "\n");
		return baseURL;
	}

	/**
	 * @param username
	 * @param password
	 * @param integratorKey
	 * @return
	 */
	public static String getAuthenticationHeader(String username,
			String password, String integratorKey) {
		// construct the DocuSign authentication header
		String authenticationHeader = 
				"<DocuSignCredentials>" + 
						"<Username>" + username + "</Username>" +
						"<Password>" + password + "</Password>" + 
						"<IntegratorKey>" + integratorKey + "</IntegratorKey>" + 
						"</DocuSignCredentials>";
		return authenticationHeader;
	}

	//***********************************************************************************************
	//***********************************************************************************************
	// --- HELPER FUNCTIONS ---
	//***********************************************************************************************
	//***********************************************************************************************
	public static HttpURLConnection InitializeRequest(String url, String method, String body, String httpAuthHeader) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection)new URL(url).openConnection();

			conn.setRequestMethod(method);
			conn.setRequestProperty("X-DocuSign-Authentication", httpAuthHeader);
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=AAA");
			conn.setRequestProperty("Accept", "application/json");
			if (method.equalsIgnoreCase("POST"))
			{
				conn.setRequestProperty("Content-Length", Integer.toString(body.length()));
				conn.setDoOutput(true);
				// write body of the POST request 
				DataOutputStream dos = new DataOutputStream( conn.getOutputStream() );
				dos.writeBytes(body); dos.flush(); dos.close();
			}
			return conn;

		} catch (Exception e) {
			throw new RuntimeException(e); // simple exception handling, please review it
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public static String parseXMLBody(String body, String searchToken) {
		String xPathExpression;
		try {
			// we use xPath to parse the XML formatted response body
			xPathExpression = String.format("//*[1]/*[local-name()='%s']", searchToken);
			XPath xPath = XPathFactory.newInstance().newXPath();
			return (xPath.evaluate(xPathExpression, new InputSource(new StringReader(body))));
		} catch (Exception e) {
			throw new RuntimeException(e); // simple exception handling, please review it
		}
	}	

	///////////////////////////////////////////////////////////////////////////////////////////////
	public static String getResponseBody(HttpURLConnection conn) {
		BufferedReader br = null;
		StringBuilder body = null;
		String line = "";
		try {
			// we use xPath to get the baseUrl and accountId from the XML response body
			br = new BufferedReader(new InputStreamReader( conn.getInputStream()));
			body = new StringBuilder();
			while ( (line = br.readLine()) != null)
				body.append(line);
			return body.toString();
		} catch (Exception e) {
			throw new RuntimeException(e); // simple exception handling, please review it
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public static void errorParse(HttpURLConnection conn, int status) { 
		BufferedReader br;
		String line;
		StringBuilder responseError;
		try {
			System.out.print("API call failed, status returned was: " + status);
			InputStreamReader isr = new InputStreamReader( conn.getErrorStream() );
			br = new BufferedReader(isr);
			responseError = new StringBuilder();
			line = null;
			while ( (line = br.readLine()) != null)
				responseError.append(line);
			//logger.info("\nError description:  \n" + prettyFormat(responseError.toString(), 2));
			return;
		}
		catch (Exception e) {
			throw new RuntimeException(e); // simple exception handling, please review it
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public static String prettyFormat(String input, int indent) { 
		try {
			Source xmlInput = new StreamSource(new StringReader(input));
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", indent);
			Transformer transformer = transformerFactory.newTransformer(); 
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(xmlInput, xmlOutput);
			return xmlOutput.getWriter().toString();
		} catch (Exception e) {
			throw new RuntimeException(e); // simple exception handling, please review it
		}
	}

	public static List<String> getEnvelopes(String envelope){

		return Arrays.asList( envelope.replaceAll("^.*?<envelopeId>", "").split("</envelopeId>.*?(<envelopeId>|$)"));

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public static void writePDFBytesToFile(HttpURLConnection conn, String docName) {
		OutputStream fop = null;
		File file;

		try {
			file = new File(docName);
			fop = new FileOutputStream(file);
			byte[] buffer = new byte[1024 /* we write the pdf in chunks of 1024 */];
			int numRead;

			while((numRead = conn.getInputStream().read(buffer)) > 0) {
				fop.write(buffer, 0, numRead);
			}
			fop.flush();
			fop.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // simple exception handling, please review it
		}
	}

	
}
