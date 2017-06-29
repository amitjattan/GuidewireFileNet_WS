package base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

public class MoveFile {

	public static void main(String[] args) throws IOException {
		String fileName = "AddDocumentToCE.txt";
		moveFile(fileName);

	}

	public static int moveFile(String filename) throws IOException {

		FTPClient ftpclient = new FTPClient();
		boolean result;
		int flag = 0;
		try {

			// Connect to the localhost

			ftpclient.connect("10.137.181.168");

			// login to ftp server

			result = ftpclient.login("fnadmin", "Hello123");

			if (result == true) {

				System.out.println("User logged in successfully !");
			} else {
				System.out.println("Login failed !");

			}

			ftpclient.enterLocalActiveMode();

			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("Moving file to Archieve folder... ");

			String existingFilepath =filename;
			String newFilepath = "Archive/"+filename;
		boolean fileMoved = ftpclient.rename(existingFilepath, newFilepath);
		
		if(fileMoved){
			
			System.out.println("File::"+filename +" moved to archive ftp folder");
		}else{
			System.out.println("File::"+filename +" doesn't moved to archive ftp folder");

			
		}

		} catch (FTPConnectionClosedException e) {
			System.err.println(e);
		} finally {
			try {
				ftpclient.disconnect();
			} catch (FTPConnectionClosedException e) {
				System.err.println(e);
			}
		}

		return flag;

	}

}
