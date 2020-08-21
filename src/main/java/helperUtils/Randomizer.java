package helperUtils;

import java.util.Random;

public class Randomizer {

	public static String RandomCustomAPI() {
		// create instance of Random class
        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(10000);
        int rand_int2 = rand.nextInt(10000);
        // Chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                  + "0123456789"
                  + "abcdefghijklmnopqrstuvxyz";
        int n =20;
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
             // generate a random number between
             // 0 to AlphaNumericString variable length
             int index = (int)(AlphaNumericString.length()* Math.random());

             // add Character one by one in end of sb
             sb.append(AlphaNumericString.charAt(index));
        }

        String token = sb.toString();
        String generateString = rand_int1 + token + rand_int2;
        return generateString;
	}
	
}
