package adab;



import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import support.IOFiles;


public class AdabParser {

	
	
	
	
	
	
	public static void adabPoems(String saveFolder, int start, int finish) throws IOException {

		
		
		
			
		for(int j = start; j < finish; j++){	
			try {
			System.out.println(j);
		  Document doc = Jsoup.connect("https://adab.com/post/view_post/"+j).get();

		  String body = doc.body().html();
		  
		 //System.out.println(body);
		  
		  
		  String poem = body.split("id=\"old_post_content_view\" value=\"")[1].split("\">")[0].trim();

		 //System.out.println(poem);
		  
		  IOFiles.saveTextFileAppend(poem,saveFolder+"/"+j+".txt");
		
		  }
			
		  
		  
				
			
			 catch (Exception e) {;}
			}
	}
		}



