package control_runnable;

import twitter.LiteraryExtractor;

public class TwitterLiteraryExtractorRunnable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
        String srcPath = args[0];
		
		// output file --> ready for translation phase.
		String desPath = args[1]; 
		
		String  logFile = args[2]; // for errors logging
		
		LiteraryExtractor.GetQuotes(srcPath, desPath, logFile);
		
		
		
	}

}
