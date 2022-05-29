package control;

import java.io.IOException;

import adab.AdabParser;

public class AdabCollecter {

	public static void main(String[] args) throws IOException {

	
			
	        String save_folder  = ""; // where the poems will be saved.
	        
	        int start_id = 1; // start from poem id : 1
	        
	        int finish_id = 10000; // finish with poem id : 10000
			
	        AdabParser.adabPoems(save_folder, start_id, finish_id);

		
		}
		
		
		
	}


