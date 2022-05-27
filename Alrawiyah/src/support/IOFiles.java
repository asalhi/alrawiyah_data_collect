package support;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class IOFiles {

public static boolean downloadPage(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream  in = null;
		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(
				new FileOutputStream(localFileName));
			conn = url.openConnection();
			
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;
			
			while ((numRead = in.read(buffer)) != -1) {
			
				out.write(buffer, 0, numRead);
				numWritten += numRead;
			} 
			System.out.println(localFileName + "\t" + numWritten);
		} catch (Exception exception) {
			System.out.println("Page not Found");
			
			//exception.printStackTrace();
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
			}
		}
		
		return true;
	}
	
	
public static void saveTextFileAppend(String contents , String filename) {
		
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
		    out.println(contents);
		    out.close();
		} catch (IOException e) {
		    System.err.println("error");
		}
	}
	
	public static void saveTextFile(String contents, String string) throws IOException {
		  FileOutputStream  fos = new FileOutputStream( string);
		  OutputStreamWriter osw = new OutputStreamWriter( fos, "UTF-8" );
		  BufferedWriter bw = null;
	      bw = new BufferedWriter( osw );	
		  bw.write( contents );
	      bw.flush();
	      bw.close();
		
		 }
	 public static String readFileAsString(String filePath)
	    throws java.io.IOException{
	        StringBuffer fileData = new StringBuffer(50000);
	        Reader reader = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
	        char[] buf = new char[5000];
	        int numRead=0;
	        while((numRead=reader.read(buf)) != -1){
	            String readData = String.valueOf(buf, 0, numRead);
	            fileData.append(readData);
	            buf = new char[1024];
	        }
	        reader.close();
	        return fileData.toString();
	    } 
	 
	 public static boolean createDir (String Location) {
		 
		  File theDir = new File(Location);

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
		    System.out.println("Msg <Class IOFiles> creating directory: " + Location);
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        System.out.println(theDir.canWrite());
		        result = true;
		     } catch(SecurityException se){

		    	System.err.println("Error <Class IOFiles>  directory can't be created !");  
		     
		     }        
		     if(result) {    
		       System.out.println("Msg <Class IOFiles>  directory created");  
		     }
		     
		     return true;

		  }
		  else {
			    System.out.println("Msg <Class IOFiles> directory exists - no action taken !");
			    return false;

		  }
		  
		  
	 }
 	 
}
