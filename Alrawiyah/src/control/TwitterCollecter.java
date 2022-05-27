package control;

import twitter.TwitterFeeder;

public class TwitterCollecter {

	public static void main(String[] args) {

		String consumer_key = "<your_consumer_key>";
		String consumer_secret = "<your_consumer_secret";	
		String access_token = "<your_access_token>";
		String access_token_secret = "<your_access_token_secret>";
		int limit = 10000; // number of tweets per file in save_folder
	    String save_folder = "<save_folder>";

		
		
		TwitterFeeder feeder = new TwitterFeeder(consumer_key, consumer_secret,
	               access_token, access_token_secret);

		feeder.getTweetsTextFull(save_folder, limit); 
		
		
	}

}
