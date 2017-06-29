package base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPConnectionClosedException;


public class CheckFileInFTP {
	
	public static void main(String[] args) throws IOException{
		String fileName="AddDocumentToCE.txt";
		checkFileInFTP(fileName);
		
	}
        public static int checkFileInFTP(String filename) throws IOException {
        	
        	
        FTPClient ftpclient = new FTPClient();
        boolean result;
        int flag = 0;
        try {

                // Connect to the localhost

                ftpclient.connect("sftp.eu2.cloud.doxee.com");

                // login to ftp server

                result = ftpclient.login("hcl_priya", "KC4DjCaM");

                if (result == true) {
                	
                        System.out.println("User logged in successfully !");
                } else {
                        System.out.println("Login failed !");
                       
                }
                
                ftpclient.enterLocalActiveMode();
                
                ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
              
                FTPFile[] files = ftpclient.listFiles();
                       
                System.out.println("Checking file existence on the server ... ");
                for (FTPFile file : files) {
                        String fileName = file.getName();
                        if (fileName.equals(filename)) {
                                flag = 1;
                         
                            
                               
                                break;
                        } else {
                                flag = 0;
                        }
                }

                if (flag == 1) {
                        System.out.println("File exists on FTP server. ");
                } 
                else {
                System.out.println("Sorry! your file is not presented on Ftp server.");
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
