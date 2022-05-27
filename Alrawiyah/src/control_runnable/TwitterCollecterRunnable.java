package control_runnable;

import twitter.TwitterFeeder;

public class TwitterCollecterRunnable {

	public static void main(String[] args) {

		String consumer_key = args[0];
		String consumer_secret = args[1];	
		String access_token = args[2];
		String access_token_secret = args[3];
		int limit = Integer.parseInt(args[4]);
	    String save_folder =args[5];

//	    System.out.println(consumer_key);
//	    System.out.println(consumer_secret);
//	    System.out.println(access_token);
//	    System.out.println(access_token_secret);
		
		
		TwitterFeeder feeder = new TwitterFeeder(consumer_key, consumer_secret,
	               access_token, access_token_secret);

		feeder.getTweetsTextFull(save_folder, limit); 
		
		
		
	}

}
