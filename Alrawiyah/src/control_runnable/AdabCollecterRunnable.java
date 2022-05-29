package control_runnable;

import java.io.IOException;

import adab.AdabParser;

public class AdabCollecterRunnable {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		String save_folder  = args[0]; // where the poems will be saved.
        
        int start_id = Integer.parseInt(args[1]); // start from poem id : 1
        
        int finish_id = Integer.parseInt(args[1]); // finish with poem id : 10000
		
        AdabParser.adabPoems(save_folder, start_id, finish_id);

        
        
	}

}
